package com.cc.cms.exception;

/**
 * 数据没有找到异常。
 */
public class DataNotFountException extends RuntimeException {
	private static final long	serialVersionUID	= 1L;

	public DataNotFountException() {
		super();
	}
	public DataNotFountException(String msg) {
		super(msg);
	}
}
