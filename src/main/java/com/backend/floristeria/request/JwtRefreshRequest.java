package com.backend.floristeria.request;

import java.io.Serializable;

public class JwtRefreshRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String refreshToken;
    
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}

