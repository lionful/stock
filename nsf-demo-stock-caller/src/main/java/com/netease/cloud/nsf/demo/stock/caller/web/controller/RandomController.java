package com.netease.cloud.nsf.demo.stock.caller.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netease.cloud.nsf.demo.stock.caller.web.service.IRandomService;

@RestController
public class RandomController {

	@Autowired
	IRandomService randomService;
	
	@GetMapping("/number")
	public int getNumber() {
		
		return randomService.getRandomNumber();
	}
	
	@GetMapping("/string")
	public String getString() {
		
		return randomService.getRanomString();
	}
}
