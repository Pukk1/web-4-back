package com.ivan.web4back.security.oauth2;//package com.ivan.web4back.security.oauth2;
//
//import com.ivan.web4back.model.access.AccessEntity;
//import com.ivan.web4back.model.access.Authority;
//import com.ivan.web4back.security.oauth2.user.OAuth2UserInfo;
//import com.ivan.web4back.security.oauth2.user.OAuth2UserInfoFactory;
//import com.ivan.web4back.service.access.AccessService;
//import com.ivan.web4back.service.account.AccountService;
//import com.ivan.web4back.utils.exception.UserNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//    private final AccessService accessService;
//    private final AccountService accountService;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        try {
//            return processOAuth2User(userRequest, oAuth2User);
//        } catch (Exception ex) {
//            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
//        }
//    }
//
//    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
//        var attributes = oAuth2User.getAttributes();
//        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), attributes);
//
//        try {
//            AccessEntity access = accessService.findByUsername(userInfo.getUsername());
//            return new UserOAuth2Principal(attributes, access.getAuthorities(), access.getAccount().getName());
//        } catch (UserNotFoundException e) {
//            var newAccount = accountService.createAccount(userInfo.getName());
//            var access = accessService.createOauthAccess(userInfo.getUsername(), userInfo.getProvider(), newAccount, List.of(Authority.USER_ROLE));
//            return new UserOAuth2Principal(attributes, access.getAuthorities(), access.getAccount().getName());
//        }
//    }
//}


import com.ivan.web4back.model.access.AccessEntity;
import com.ivan.web4back.model.access.AuthProvider;
import com.ivan.web4back.model.access.Authority;
import com.ivan.web4back.security.oauth2.user.OAuth2UserInfo;
import com.ivan.web4back.security.oauth2.user.OAuth2UserInfoFactory;
import com.ivan.web4back.service.access.AccessService;
import com.ivan.web4back.service.account.AccountService;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final RestOperations restOperations;
    private final AccessService accessService;
    private final AccountService accountService;
    private final Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter();
    private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";
    private static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute";
    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";
    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE =
            new ParameterizedTypeReference<Map<String, Object>>() {
            };

    public CustomOAuth2UserService(AccessService accessService, AccountService accountService) {
        this.accessService = accessService;
        this.accountService = accountService;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User;
        if (!userRequest.getClientRegistration().getRegistrationId().equals(AuthProvider.VK.toString())) {
            oAuth2User = super.loadUser(userRequest);
        } else {
            Assert.notNull(userRequest, "userRequest cannot be null");

            if (!StringUtils.hasText(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())) {
                OAuth2Error oauth2Error = new OAuth2Error(
                        MISSING_USER_INFO_URI_ERROR_CODE,
                        "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: " +
                                userRequest.getClientRegistration().getRegistrationId(),
                        null
                );
                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
            }

            String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                    .getUserInfoEndpoint().getUserNameAttributeName();

            if (!StringUtils.hasText(userNameAttributeName)) {
                OAuth2Error oauth2Error = new OAuth2Error(
                        MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE,
                        "Missing required \"user name\" attribute name in UserInfoEndpoint for Client Registration: " +
                                userRequest.getClientRegistration().getRegistrationId(),
                        null
                );
                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
            }

            RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);
            ResponseEntity<Map<String, Object>> response;

            try {
                response = this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
            } catch (OAuth2AuthorizationException ex) {
                OAuth2Error oauth2Error = ex.getError();
                StringBuilder errorDetails = new StringBuilder();
                errorDetails.append("Error details: [");
                errorDetails.append("UserInfo Uri: ").append(
                        userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri());
                errorDetails.append(", Error Code: ").append(oauth2Error.getErrorCode());
                if (oauth2Error.getDescription() != null) {
                    errorDetails.append(", Error Description: ").append(oauth2Error.getDescription());
                }
                errorDetails.append("]");
                oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                        "An error occurred while attempting to retrieve the UserInfo Resource: " + errorDetails.toString(), null);
                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
            } catch (RestClientException ex) {
                OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                        "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.getMessage(), null);
                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
            }

            //извлекаем атрибуты из обертки "response"
            ArrayList valueList = (ArrayList) response.getBody().get("response");
            Map<String, Object> userAttributes = (Map<String, Object>) valueList.get(0);
            Set<GrantedAuthority> authorities = new LinkedHashSet<>();
            authorities.add(new OAuth2UserAuthority(userAttributes));
            OAuth2AccessToken token = userRequest.getAccessToken();
            for (String authority : token.getScopes()) {
                authorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
            }
            oAuth2User = new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
        }

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), attributes);

        try {
            AccessEntity access = accessService.findByUsername(userInfo.getUsername());
            return new UserOAuth2Principal(attributes, access.getAuthorities(), access.getAccount().getName());
        } catch (UserNotFoundException e) {
            var newAccount = accountService.createAccount(userInfo.getName());
            var access = accessService.createOauthAccess(userInfo.getUsername(), userInfo.getProvider(), newAccount, List.of(Authority.USER_ROLE));
            return new UserOAuth2Principal(attributes, access.getAuthorities(), access.getAccount().getName());
        }
    }
}