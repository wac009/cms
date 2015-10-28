package com.cc.common.struts2.interceptor;

public interface DomainNameAware {
	public static final String	DOMAIN_NAME	= "_domain";

	public void setDomainName(String domainName);
}
