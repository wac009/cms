package com.cc.common.struts2.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.interceptor.PrefixMethodInvocationUtil;

@SuppressWarnings("serial")
public class DataControlInterceptor extends MethodFilterInterceptor {
	private static final Logger	log			= LoggerFactory.getLogger(DataControlInterceptor.class);
	private final static String	PREFIX		= "control";
	private final static String	PREFIX_ALT	= "controlDo";

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if (action instanceof DataControlAware) {
			try {
				PrefixMethodInvocationUtil.invokePrefixMethod(invocation, new String[] { PREFIX, PREFIX_ALT });
			}
			catch (DataAccessException e) {
				log.warn("数据不允许访问！");
				throw e;
			}
			catch (Exception e) {
				throw e;
			}
		}
		return invocation.invoke();
	}
}