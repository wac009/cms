package com.cc.cms.exception;

/**
 * 缺少注册的必要信息。
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
