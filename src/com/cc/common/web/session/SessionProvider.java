
package com.cc.common.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

/**
 * HttpSession提供类
 * 
 * @author wangcheng
 */

@Service
@Transactional
public class SessionProvider implements ISessionProvider {

	@Override
	public Serializable getAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (Serializable) session.getAttribute(name);
		} else {
			return null;
		}
	}

	@Override
	public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value) {
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}

	@Override
	public String getSessionId(HttpServletRequest request, HttpServletResponse response) {
		return request.getSession().getId();
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
}
