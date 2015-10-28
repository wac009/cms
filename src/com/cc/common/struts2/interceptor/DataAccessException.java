package com.cc.common.struts2.interceptor;

public class DataAccessException extends RuntimeException {
	private static final long	serialVersionUID	= 1L;

	public DataAccessException() {
		super();
	}
	public DataAccessException(String msg) {
		super(msg);
	}
}
