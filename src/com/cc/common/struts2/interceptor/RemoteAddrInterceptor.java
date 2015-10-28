package com.cc.common.struts2.interceptor;

import javax.servlet.http.*;

import org.apache.struts2.*;

import com.opensymphony.xwork2.*;
import com.opensymphony.xwork2.interceptor.*;

public class RemoteAddrInterceptor extends AbstractInterceptor {
	/**
	 *获取远程IP
	 */
	private static final long	serialVersionUID	= -2185920708747626659L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		Object action = invocation.getAction();
		HttpServletRequest request = (HttpServletRequest) ac.get(StrutsStatics.HTTP_REQUEST);
		String userRemoteAddr = request.getRemoteAddr();
		if (action instanceof RemoteAddrAware) {
			((RemoteAddrAware) action).setRemoteAddr(userRemoteAddr);
		}
		return invocation.invoke();
	}
}
