package com.ivan.web4back.security.oauth2;

import com.ivan.web4back.model.access.AccessEntity;
import com.ivan.web4back.model.access.Authority;
import com.ivan.web4back.security.oauth2.user.OAuth2UserInfo;
import com.ivan.web4back.security.oauth2.user.OAuth2UserInfoFactory;
import com.ivan.web4back.service.access.AccessService;
import com.ivan.web4back.service.account.AccountService;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AccessService accessService;
    private final AccountService accountService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

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
