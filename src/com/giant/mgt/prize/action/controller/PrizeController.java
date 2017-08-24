package com.giant.mgt.prize.action.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.giant.mgt.prize.action.service.PrizeService;
import com.giant.mgt.prize.action.service.impl.PrizeServiceImpl;
import com.jfinal.core.Controller;

public class PrizeController extends Controller {
	protected static final Log log = LogFactory.getLog(PrizeController.class);
	private PrizeService prizeService = new PrizeServiceImpl();
	public void access(){
		String actId = getPara("actId", "");
		if("".equals(actId)){
			renderError(404);
		}
		boolean flag = prizeService.setTemplate(actId);
		if(!flag){
			renderError(404);
		}else{
			Map<String, String> map = prizeService.getActInfo(actId);
			setAttr("title", map.get("title"));
			setAttr("rule", map.get("rule"));
			setAttr("result", map.get("result"));
			setAttr("actId",actId);
			renderFreeMarker("/WEB-INF/common/common.html");
		}
	}
	
	public void checkWinning(){
		String actId = getPara("actId", "");
		String number = getPara("number", "");
		String result = prizeService.checkWinning(actId,number);
//		setAttr("title", "活动中奖信息查询");
//		setAttr("result", "恭喜您，获得30M流量");
//		renderFreeMarker("/WEB-INF/common/common.html");
		//查询中奖
		renderJson("{\"message\":\""+result+"\"}");
	}
}
