package com.cc.cms.exception;

/**
 * ȱ��ע��ı�Ҫ��Ϣ��
 */
@SuppressWarnings("serial")
public class LackOfInfomationException extends UserRegisterException {
	public LackOfInfomationException() {
		super();
	}
	public LackOfInfomationException(String msg) {
		super(msg);
	}
}
