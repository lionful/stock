package com.netease.cloud.nsf.demo.stock.caller.web.service;

import java.util.List;

import com.netease.cloud.nsf.demo.stock.caller.web.entity.Stock;


public interface IStockService {

	public List<Stock> getStockList(int delay) throws Exception;
	
	public Stock getStockById(String stockId) throws Exception;
	
	public List<Stock> getHotStockAdvice() throws Exception;
	
	public String echoAdvisor();
	
	public String echoProvider();
	
	public String deepInvoke(int times);

	public String getMaxSpreadStock();

	public String getPredictPriceById(String stockId);
}
