package com.cc.cms.exception;

/**
 * �û�ע���쳣��
 */
@SuppressWarnings("serial")
public class UserRegisterException extends RuntimeException {
	public UserRegisterException() {
		super();
	}
	public UserRegisterException(String msg) {
		super(msg);
	}
}
