package com.cc.cms.exception;

@SuppressWarnings("serial")
public class NoLogonException extends RuntimeException {
	public NoLogonException() {
		super();
	}

	public NoLogonException(String msg) {
		super(msg);
	}
}
