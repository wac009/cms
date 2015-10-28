
package com.cc.common.struts2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.*;

public class ContextPvdImpl implements IContextPvd {

	@Override
	public ActionContext getActionContext() {
		return ActionContext.getContext();
	}

	@Override
	public String getAppRealPath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}

	@Override
	public String getAppRoot() {
		return getAppRealPath("/");
	}

	@Override
	public String getAppCxtPath() {
		return ServletActionContext.getRequest().getContextPath();
	}

	@Override
	public int getServerPort() {
		return ServletActionContext.getRequest().getServerPort();
	}

	@Override
	public void logout() {
		HttpSession session = ServletActionContext.getRequest().getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	@Override
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	@Override
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	@Override
	public Object getRequestAttr(String key) {
		return ServletActionContext.getRequest().getAttribute(key);
	}

	@Override
	public void setRequestAttr(String key, Object value) {
		ServletActionContext.getRequest().setAttribute(key, value);
	}

	@Override
	public Object getSessionAttr(String key) {
		HttpSession session = ServletActionContext.getRequest().getSession(false);
		if (session == null) {
			return null;
		} else {
			return session.getAttribute(key);
		}
	}

	@Override
	public void setSessionAttr(String key, Object value) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute(key, value);
	}

	@Override
	public void removeSessionAttribute(String key) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute(key);
	}

	@Override
	public Object getApplicationAttr(String key) {
		return ServletActionContext.getServletContext().getAttribute(key);
	}

	@Override
	public void setApplicationAttr(String key, Object value) {
		ServletActionContext.getServletContext().setAttribute(key, value);
	}

	@Override
	public String getSessionId(boolean isCreate) {
		HttpSession session = ServletActionContext.getRequest().getSession(isCreate);
		if (session == null) {
			return null;
		} else {
			return session.getId();
		}
	}

	@Override
	public String getRemoteIp() {
		return ServletActionContext.getRequest().getRemoteAddr();
	}

	@Override
	public int getRemotePort() {
		return ServletActionContext.getRequest().getRemotePort();
	}

	@Override
	public String getRequestURL() {
		return ServletActionContext.getRequest().getRequestURL().toString();
	}

	@Override
	public String getRequestBrowser() {
		String userAgent = getRequestUserAgent();
		String[] agents = userAgent.split(";");
		if (agents.length > 1) {
			return agents[1].trim();
		} else {
			return null;
		}
	}

	@Override
	public String getRequestOs() {
		String userAgent = getRequestUserAgent();
		String[] agents = userAgent.split(";");
		if (agents.length > 2) {
			return agents[2].trim();
		} else {
			return null;
		}
	}

	@Override
	public String getRequestUserAgent() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String userAgent = req.getHeader("user-agent");
		return userAgent;
	}

	@Override
	public void addCookie(Cookie cookie) {
		ServletActionContext.getResponse().addCookie(cookie);
	}

	@Override
	public Cookie getCookie(String name) {
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public boolean isMethodPost() {
		String method = ServletActionContext.getRequest().getMethod();
		if ("post".equalsIgnoreCase(method)) {
			return true;
		} else {
			return false;
		}
	}
}
