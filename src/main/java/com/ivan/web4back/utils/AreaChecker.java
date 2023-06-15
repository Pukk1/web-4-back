package com.ivan.web4back.utils;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;

public class AreaChecker {
    public static boolean checkArea(AreaPointRequest pointRequest) {
        if (pointRequest.getR() > 0) {
            if (pointRequest.getX() < 0 && pointRequest.getY() < 0) {
                return false;
            } else if (pointRequest.getX() >= 0 && pointRequest.getY() <= 0) {
                return Math.pow(Math.abs(pointRequest.getX()), 2) + Math.pow(Math.abs(pointRequest.getY()), 2) <= Math.pow(pointRequest.getR(), 2);
            } else if (pointRequest.getX() >= 0 && pointRequest.getY() >= 0) {
                return pointRequest.getX() <= pointRequest.getR() / 2 && pointRequest.getY() <= pointRequest.getR();
            } else if (pointRequest.getX() <= 0 && pointRequest.getY() >= 0) {
                return pointRequest.getX() + pointRequest.getR() - pointRequest.getY() >= 0;
            } else {
                return false;
            }
        } else {
            if (pointRequest.getX() > 0 && pointRequest.getY() > 0) {
                return false;
            } else if (pointRequest.getX() <= 0 && pointRequest.getY() >= 0) {
                return Math.pow(Math.abs(pointRequest.getX()), 2) + Math.pow(Math.abs(pointRequest.getY()), 2) <= Math.pow(pointRequest.getR(), 2);
            } else if (pointRequest.getX() <= 0 && pointRequest.getY() <= 0) {
                return pointRequest.getX() >= pointRequest.getR() / 2 && pointRequest.getY() >= pointRequest.getR();
            } else if (pointRequest.getX() >= 0 && pointRequest.getY() <= 0) {
                return pointRequest.getX() + pointRequest.getR() - pointRequest.getY() <= 0;
            } else {
                return false;
            }
        }
    }
}