package com.cc.cms.exception;

/**
 * ����û���ҵ��쳣��
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
