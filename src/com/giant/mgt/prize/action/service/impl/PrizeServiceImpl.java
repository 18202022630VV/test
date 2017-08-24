package com.giant.mgt.prize.action.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.giant.mgt.prize.action.service.PrizeService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.FreeMarkerRender;

public class PrizeServiceImpl implements PrizeService {
	protected static final Log log = LogFactory.getLog(PrizeServiceImpl.class);

	public boolean setTemplate(String actId) {
		Record route = Db.findFirst("select b.TEMP_ROUTE from mrk_act a,mrk_act_temp b where a.ACT_ID = ? and a.TEMP_ID = b.ID and a.STATUS = '1' and a.ACT_STATUS = '1' and b.STATUS = '1'",actId);
		if(route!=null){
			Map<String,String> map = new HashMap<String, String>();
    		map.put("macro",route.getStr("TEMP_ROUTE"));
    		FreeMarkerRender.getConfiguration().setAutoImports(map);
    		return true;
		}
		return false;
	}

	public String checkWinning(String actId,String number) {
		Record record = Db.findFirst("select a.PROMPT from mrk_user_group a,mrk_user_detail b where a.ACT_ID = ? and a.ID = b.GROUP_ID and b.IDENTIFY = ? and a.STATUS = '1'",actId,number);
		if(record == null){
			return Db.findFirst("select ACT_PROMPT from mrk_act where ACT_ID = ?",actId).getStr("ACT_PROMPT");
		}else{
			return record.getStr("PROMPT");
		}
	}
	
	public Map<String, String> getActInfo(String actId) {
		Map<String, String> map = new HashMap<String, String>();
		Record record = Db.findFirst("select ACT_NAME,ACT_DESC from mrk_act where ACT_ID = ?",actId);
		if(record!=null){
			map.put("title", record.getStr("ACT_NAME"));
			map.put("rule", record.getStr("ACT_DESC"));
			map.put("result", "&nbsp;");
		}
		return map;
	}
}
