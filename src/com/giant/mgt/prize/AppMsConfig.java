package com.giant.mgt.prize;
import com.giant.mgt.prize.action.controller.PrizeController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class AppMsConfig extends JFinalConfig{
	public void configConstant(Constants me) {
        //设定为开发者模式
        me.setDevMode(true);
        me.setMaxPostSize(200*1024*1024);
        me.setError404View("/WEB-INF/errorPages/404.html");
        me.setError500View("/WEB-INF/errorPages/500.html");
        me.setBaseViewPath("/WEB-INF/templates");
        me.setFreeMarkerViewExtension(".html");
	}
	public void configRoute(Routes me) {
		me.add("/",PrizeController.class);
	}
	public void configPlugin(Plugins me) {
		loadPropertyFile("database.properties");
//		C3p0Plugin cp =  new  C3p0Plugin(getProperty("url"),getProperty("username"), getProperty("password"));
//        cp.setDriverClass(getProperty("driverClass"));
//        cp.setAcquireIncrement(5);
//        me.add(cp);
//	    ActiveRecordPlugin arp=new ActiveRecordPlugin(cp);
//        me.add(arp);
//        //配置属性名(字段名)大小写不敏感容器工厂
//        arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		
		try {
			C3p0Plugin cp = new C3p0Plugin(getProperty("url"),getProperty("username"), getProperty("password"));
			cp.setDriverClass(getProperty("driverClass"));
			cp.setInitialPoolSize(3).setMaxIdleTime(180);
			me.add(cp);
			ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
			arp.setShowSql(false);
			arp.setDialect(new OracleDialect());
			arp.setContainerFactory(new CaseInsensitiveContainerFactory());
			me.add(arp);
		} catch (Exception e) {

		}
	}
	public void configInterceptor(Interceptors me) {
		
	}
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("base"));
	}
    public void afterJFinalStart(){
//    	Properties p = loadPropertyFile("freemarker.properties");
//        FreeMarkerRender.setProperties(p);
//    	Map<String,String> map = new HashMap<String, String>();
//    	map.put("macro","/WEB-INF/templates/prize.html");
//    	FreeMarkerRender.getConfiguration().setAutoImports(map);
    }
}
