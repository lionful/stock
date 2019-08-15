package com.netease.cloud.nsf.demo.stock.caller.web.service;

import com.netease.cloud.nsf.demo.stock.caller.web.entity.AuthToken;

public interface ITokenService {

	public AuthToken getToken(String ak, String sk);
}
