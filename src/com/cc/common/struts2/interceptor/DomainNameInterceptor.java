package com.cc.common.struts2.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class DomainNameInterceptor extends MethodFilterInterceptor {
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest req = (HttpServletRequest) ac.get(StrutsStatics.HTTP_REQUEST);
		req.setAttribute(DomainNameAware.DOMAIN_NAME, req.getServerName());
		if (action instanceof DomainNameAware) {
			DomainNameAware aware = (DomainNameAware) action;
			aware.setDomainName(req.getServerName());
		}
		return invocation.invoke();
	}
}