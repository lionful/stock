package com.netease.cloud.nsf.demo.stock.caller.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.cloud.nsf.demo.stock.caller.web.auth.AuthManager;
import com.netease.cloud.nsf.demo.stock.caller.web.entity.AuthToken;
import com.netease.cloud.nsf.demo.stock.caller.web.entity.HttpResponse;
import com.netease.cloud.nsf.demo.stock.caller.web.entity.Stock;
import com.netease.cloud.nsf.demo.stock.caller.web.manager.HttpLogManager;
import com.netease.cloud.nsf.demo.stock.caller.web.service.IStockService;
import com.netease.cloud.nsf.demo.stock.caller.web.service.ITokenService;


/**
 * @author Chen Jiahan | chenjiahan@corp.netease.com
 */
@Controller
public class PanelController {

    private static Logger log = LoggerFactory.getLogger(PanelController.class);

    @Autowired
    IStockService stockService;

    @Autowired
    HttpLogManager httpLogManager;
    
    @Autowired
    ITokenService tokenService;
    
    @GetMapping(value = {"" , "/index"})
    public String indexPage(){
        return "index";
    }

    @GetMapping(value = "/stocks", produces = "application/json")
    @ResponseBody
    public HttpResponse getStockList(@RequestParam(name = "delay", required = false, defaultValue = "0") int delay) {

        List<Stock> stocks;
        try {
            stocks = stockService.getStockList(delay);
        } catch (Exception e) {
            log.warn("get stock list failed ...");
            log.warn(e.getMessage());
            return handleExceptionResponse(e);
        }
        return new HttpResponse(stocks);
    }

    @GetMapping(value = "/advices/hot", produces = "application/json")
    @ResponseBody
    public HttpResponse getHotAdvice() {

        List<Stock> stocks;
        try {
            stocks = stockService.getHotStockAdvice();
        } catch (Exception e) {
            log.warn("get hot stock advice failed ...");
            return handleExceptionResponse(e);
        }
        return new HttpResponse(stocks);
    }

    @GetMapping("/stocks/{stockId}")
    @ResponseBody
    public Stock getStockById(@PathVariable String stockId) {

        Stock stock = null;
        try {
            stock = stockService.getStockById(stockId);
        } catch (Exception e) {
            log.warn("get stock[{}] info failed ...", stockId);
        }
        return stock;
    }

    @GetMapping("/logs")
    @ResponseBody
    public HttpResponse getHttpLog() {
    	return new HttpResponse(httpLogManager.logs());
    }
    
    @GetMapping("/logs/clear")
    @ResponseBody
    public HttpResponse clearLogs() {
    	httpLogManager.clear();
    	return new HttpResponse("clear logs success");
    }
    
    @GetMapping("/echo/advisor")
    @ResponseBody
    public HttpResponse echoAdvisor(HttpServletRequest request) {
    	String result = stockService.echoAdvisor();
    	httpLogManager.put(UUID.randomUUID().toString(), result);
    	return new HttpResponse(result);
    }
    
    @GetMapping("/echo/provider")
    @ResponseBody
    public HttpResponse echoProvider(HttpServletRequest request) {
    	String result = stockService.echoProvider();
    	httpLogManager.put(UUID.randomUUID().toString(), result);
    	return new HttpResponse(result);
    }
    
    @GetMapping("/health")
    @ResponseBody
    public String health() {
    	return "I am good!";
    }
    
    @RequestMapping("/deepInvoke")
    @ResponseBody
    public String deepInvoke(@RequestParam int times) {
    	return stockService.deepInvoke(times);
    }
    
    private HttpResponse handleExceptionResponse(Exception e) {
        NsfExceptionUtil.NsfExceptionWrapper nsfException = NsfExceptionUtil.parseException(e);
        log.error(nsfException.getThrowable().getMessage());
        if(nsfException.getType() == NsfExceptionUtil.NsfExceptionType.NORMAL_EXCEPTION){
            return new HttpResponse(nsfException.getThrowable().getMessage());
        }
        return new HttpResponse(nsfException.getType().getDesc());
    }

    //最大/最小差价
    @GetMapping("/spread")
    @ResponseBody
    public String getMaxSpreadStock() {
        String MaxSpreadStock=null;
        try {
            MaxSpreadStock=stockService.getMaxSpreadStock();
        }
        catch (Exception e){
            log.warn("get max stock spread failed ...");
        }
        return MaxSpreadStock;
    }

    //预测股票数据
    @GetMapping("/predictPrice/{stockId}")
    @ResponseBody
    public String getPredictPriceById(@PathVariable String stockId) {
        String PredictPrice=null;
        try {
            PredictPrice=stockService.getPredictPriceById(stockId);
        }
        catch (Exception e){
            log.warn("get predict stock price failed ...");
        }
        return PredictPrice;
    }
    
    @GetMapping("/tokenOn")
    @ResponseBody
    public HttpResponse turnTokenSwitchOn(@RequestParam(name = "ak") String ak,
    		@RequestParam(name = "sk") String sk) {
    	
    	if(StringUtils.isEmpty(ak) || StringUtils.isEmpty(sk)) {
    		log.info("ak or sk is empty");
    		return new HttpResponse("ak or sk is empty");
    	}
    	AuthToken token = tokenService.getToken(ak, sk);
    	if(token == null) {
    		return new HttpResponse("fail to get token");
    	}
    	AuthManager.setAuthToken(token);
    	return new HttpResponse("success");
    }
    
    @GetMapping("/tokenOff")
    @ResponseBody
    public HttpResponse turnTokenSwitchOff() {
    	AuthManager.clearToken();
    	return new HttpResponse("success");
    }
    
    @GetMapping("/secret/{keys:.+}")
    @ResponseBody
    public HttpResponse getValsBykeys(@PathVariable String[] keys) {
    	String result = Arrays.stream(keys).map(System::getProperty).collect(Collectors.toList()).toString();
    	httpLogManager.put(UUID.randomUUID().toString(), result);
    	return new HttpResponse("success");
    }
}