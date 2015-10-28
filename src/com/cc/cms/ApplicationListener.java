package com.cc.cms;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cc.cms.service.main.IPermissionSvc;
import com.cc.cms.service.main.IWebsiteSvc;

public class ApplicationListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent event) {}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Logger log = LoggerFactory.getLogger(ApplicationListener.class);
		log.info(">>>>------开始缓存数据-------<<<<");
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		IWebsiteSvc websiteSvc = wac.getBean("websiteSvcImpl", IWebsiteSvc.class);
		IPermissionSvc permissionSvc = wac.getBean("permissionSvcImpl", IPermissionSvc.class);
		websiteSvc.loadAllToCache();
		log.info(">>>>------站点缓存完成-------<<<<");
		permissionSvc.loadAllToCache();
		log.info(">>>>------权限缓存完成-------<<<<");
	}
}