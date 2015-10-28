package com.cc.common.security;

/**
 * 账号被锁定异常
 * 
 * @author wangcheng
 * 
 */
@SuppressWarnings("serial")
public class LockedException extends AccountStatusException {
	public LockedException() {
	}

	public LockedException(String msg) {
		super(msg);
	}

	public LockedException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
