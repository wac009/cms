
package com.cc.cms.interceptor;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.servlet.handler.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.cms.web.*;
import com.cc.common.web.session.*;

/** CMS上下文信息拦截器 包括登录信息、权限信息、站点信息
 * 
 * @author wangcheng */
public class FrontContextInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ISessionProvider	session;
	@Autowired
	private IWebsiteSvc			cmsSiteMng;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		Site site = null;
		List<Site> list = cmsSiteMng.getAllWebsites();
		int size = list.size();
		if (size == 0) {
			throw new RuntimeException("no site record in database!");
		} else if (size == 1) {
			site = list.get(0);
		} else {
			String server = request.getServerName();
			String alias, redirect;
			for (Site s : list) {
				// 检查域名
				if (s.getDomain().equals(server)) {
					site = s;
					break;
				}
				// 检查域名别名
				alias = s.getDomainAlias();
				if (!StringUtils.isBlank(alias)) {
					for (String a : StringUtils.split(alias, ',')) {
						if (a.equals(server)) {
							site = s;
							break;
						}
					}
				}
				// 检查重定向
				redirect = s.getDomainRedirect();
				if (!StringUtils.isBlank(redirect)) {
					for (String r : StringUtils.split(redirect, ',')) {
						if (r.equals(server)) {
							try {
								response.sendRedirect(s.getUrl());
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
							return false;
						}
					}
				}
			}
		}
		CmsUtils.setSite(request, site);
		return true;
	}
}