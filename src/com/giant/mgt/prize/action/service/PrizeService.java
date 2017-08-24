package com.giant.mgt.prize.action.service;

import java.util.Map;

public interface PrizeService {
	boolean setTemplate(String actId);
	String checkWinning(String actId,String number);
	Map<String, String> getActInfo(String actId);
}
