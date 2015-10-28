
package com.cc.cms.interceptor;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.apache.struts2.*;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.common.struts2.*;
import com.opensymphony.xwork2.*;
import com.opensymphony.xwork2.interceptor.*;

/**
 * cookie识别码拦截器 使用cookie标识浏览器，可用于投票、流量统计等功能。
 */
@SuppressWarnings("serial")
public class CookieIdentityInterceptor extends MethodFilterInterceptor {

	private IWebsiteSvc	websiteSvc;
	private IContextPvd	contextPvd;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		if (websiteSvc == null || contextPvd == null) {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			websiteSvc = wac.getBean("websiteSvcImpl", IWebsiteSvc.class);
			contextPvd = wac.getBean("contextPvd", IContextPvd.class);
		}
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest req = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		Site web = websiteSvc.getWebsite(req.getServerName());
		if (web == null) {
			return invocation.invoke();
		}
		Cookie cookie = contextPvd.getCookie(web.getCookieKey());
		if (cookie == null) {
			String s = RandomStringUtils.randomAlphanumeric(10);
			Cookie c = new Cookie(web.getCookieKey(), s);
			c.setDomain(web.getDomain());
			c.setMaxAge(Integer.MAX_VALUE);
			c.setPath("/");
			contextPvd.addCookie(c);
		}
		return invocation.invoke();
	}
}
