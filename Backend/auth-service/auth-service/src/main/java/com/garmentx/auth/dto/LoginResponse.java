package com.garmentx.auth.dto;

public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(String accessToken, String tokenType, String role) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}