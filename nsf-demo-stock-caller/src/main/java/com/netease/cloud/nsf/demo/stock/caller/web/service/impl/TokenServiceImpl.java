package com.netease.cloud.nsf.demo.stock.caller.web.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netease.cloud.nsf.demo.stock.caller.web.entity.AuthToken;
import com.netease.cloud.nsf.demo.stock.caller.web.service.ITokenService;

@Service
public class TokenServiceImpl implements ITokenService {

	private static Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Value("${auth_center_url}")
	String authCenterUrl;

	@Override
	public AuthToken getToken(String ak, String sk) {

		String requestUrl = authCenterUrl + "/auth?Action=GenToken&Version=2018-07-26&AccessKey=" + ak
				+ "&AccessSecret=" + sk;
		ResponseEntity<String> response;
		try {
			response = restTemplate.getForEntity(requestUrl, String.class);
			if (response.getStatusCode().is2xxSuccessful()) {
				JsonParser springParser = JsonParserFactory.getJsonParser();
				Map<String, Object> resMap = springParser.parseMap(response.getBody());
				return new AuthToken(resMap);
			}
		} catch (Exception ex) {
			log.error(ex.toString());
		}

		return null;
	}

}
