package com.cc.cms.exception;

/**
 * ����Ա�����쳣
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
