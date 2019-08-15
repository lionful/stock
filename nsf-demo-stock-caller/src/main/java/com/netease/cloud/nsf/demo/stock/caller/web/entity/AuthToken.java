package com.netease.cloud.nsf.demo.stock.caller.web.entity;

import java.util.Map;

public class AuthToken {

    public static final String TOKEN_KEY = "X-nsf-authentication";

    private String tokenId;
    private Long expirationTime;

    public AuthToken(Map<String, Object> resMap) {
        this.tokenId = (String) resMap.get("TokenId");
        this.expirationTime = (Long) resMap.get("ExpirationTime");
    }

    public String getTokenId() {
        return tokenId;
    }

    public boolean isExpired(){
        // 提前2小时过期
        return System.currentTimeMillis() + 2 * 3600 * 1000 >= this.expirationTime;
    }
}
