package com.cc.cms.exception;

/**
 * 管理员禁用异常
 */
@SuppressWarnings("serial")
public class AdminDisabledException extends RuntimeException {
	public AdminDisabledException() {
		super();
	}
	public AdminDisabledException(String msg) {
		super(msg);
	}
}
