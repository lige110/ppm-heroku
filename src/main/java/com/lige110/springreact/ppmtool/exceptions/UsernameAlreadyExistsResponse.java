package com.lige110.springreact.ppmtool.exceptions;

public class UsernameAlreadyExistsResponse {

    private String username;

    public UsernameAlreadyExistsResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
