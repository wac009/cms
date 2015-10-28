package com.cc.common.security.rememberme;

import com.cc.common.security.*;

@SuppressWarnings("serial")
public class RememberMeAuthenticationException extends AuthenticationException {
	public RememberMeAuthenticationException() {
	}

	public RememberMeAuthenticationException(String msg) {
		super(msg);
	}
}
