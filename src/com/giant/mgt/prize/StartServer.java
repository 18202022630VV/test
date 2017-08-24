package com.giant.mgt.prize;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class StartServer {
	protected static final Log log = LogFactory.getLog(StartServer.class);
	private static int port = 9000;
	private static String contextPath = "/";
	private static String webAppDirectory = "WebRoot";
	private static String defaultsDescriptor = "";
	
	public static void init(){
//		String path = StartServer.class.getClassLoader().getResource("log4j.properties").getPath();
//		DOMConfigurator.configure(path);
		InputStream in = StartServer.class.getClassLoader().getResourceAsStream("database.properties");
		Properties p = new Properties();
		try {
			p.load(in);
			port = Integer.parseInt((String)p.get("port"));
			log.info("端口为："+port);
		} catch (IOException e) {
			log.info("获取端口出错。");
		}
	}
	
	public static void main(String[] args){
		init();
		startUp();
	}
	
	public static void startUp(){
	    log.info("start...");
		Server server = new Server();
		QueuedThreadPool jettyThreadpool = new QueuedThreadPool();
		jettyThreadpool.setMinThreads(30);
		jettyThreadpool.setMaxThreads(400);
		server.setThreadPool(jettyThreadpool);
		Connector connector = new SelectChannelConnector();
		connector.setPort(port);   
		server.setConnectors(new Connector[] { connector });   
		WebAppContext webAppContext = new WebAppContext(); 
		if (contextPath != null){
            webAppContext.setContextPath(contextPath);
		}
        if(defaultsDescriptor!=null && !defaultsDescriptor.equals("")){
        	webAppContext.setDefaultsDescriptor(defaultsDescriptor); 
        }
        
        webAppContext.setResourceBase(webAppDirectory);
        webAppContext.getInitParams().put("org.mortbay.jetty.servlet.Default.dirAllowed", "false");
        webAppContext.getInitParams().put("org.mortbay.jetty.servlet.Default.useFileMappedBuffer", "false");
		server.setHandler(webAppContext);
		server.setStopAtShutdown(true);   
		server.setSendServerVersion(true); 
		
		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		log.info("jetty start success...");
	}
}
