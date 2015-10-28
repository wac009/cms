
package com.cc.core.service.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.http.*;

import org.apache.commons.lang.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.cc.cms.service.*;
import com.cc.common.page.*;
import com.cc.common.security.*;
import com.cc.common.web.session.*;
import com.cc.core.dao.*;
import com.cc.core.entity.*;
import com.cc.core.service.*;

@Service
@Transactional
public class AuthenticationSvcImpl extends CmsSvcImpl<Authentication> implements IAuthenticationSvc {

	private Logger			log			= LoggerFactory.getLogger(AuthenticationSvcImpl.class);
	// 过期时间
	private int				timeout		= 30 * 60 * 1000;													// 30分钟
	// 间隔时间
	private int				interval	= 4 * 60 * 60 * 1000;												// 4小时
	// 刷新时间。
	private long			refreshTime	= getNextRefreshTime(System.currentTimeMillis(), this.interval);
	@Autowired
	private IUnifiedUserSvc	unifiedUserSvc;

	@Autowired
	public void setDao(IAuthenticationDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IAuthenticationDao getDao() {
		return (IAuthenticationDao) super.getDao();
	}

	/**
	 * 设置认证过期时间。默认30分钟。
	 * 
	 * @param timeout
	 *            单位分钟
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout * 60 * 1000;
	}

	/**
	 * 设置刷新数据库时间。默认4小时。
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
		this.refreshTime = getNextRefreshTime(System.currentTimeMillis(), this.interval);
	}

	/**
	 * 获得下一个刷新时间。
	 * 
	 * @param current
	 * @param interval
	 * @return 随机间隔时间
	 */
	private long getNextRefreshTime(long current, int interval) {
		return current + interval;
		// 为了防止多个应用同时刷新，间隔时间=interval+RandomUtils.nextInt(interval/4);
		// return current + interval + RandomUtils.nextInt(interval / 4);
	}

	@Override
	public Authentication login(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException {
		UnifiedUser user = unifiedUserSvc.login(username, password, ip);
		Authentication auth = new Authentication();
		auth.setUid(user.getId());
		auth.setUsername(user.getUsername());
		auth.setEmail(user.getEmail());
		auth.setLoginIp(ip);
		save(auth);
		return auth;
	}

	@Override
	public Authentication login(String username, String password, String ip, HttpServletRequest request, HttpServletResponse response, ISessionProvider session)
			throws UsernameNotFoundException, BadCredentialsException {
		UnifiedUser user = unifiedUserSvc.login(username, password, ip);
		Authentication auth = new Authentication();
		auth.setUid(user.getId());
		auth.setUsername(user.getUsername());
		auth.setEmail(user.getEmail());
		auth.setLoginIp(ip);
		save(auth);
		session.setAttribute(request, response, AUTH_KEY, auth.getId());
		return auth;
	}

	@Override
	public Authentication activeLogin(UnifiedUser user, String ip, HttpServletRequest request, HttpServletResponse response, ISessionProvider session) {
		unifiedUserSvc.activeLogin(user, ip);
		Authentication auth = new Authentication();
		auth.setUid(user.getId());
		auth.setUsername(user.getUsername());
		auth.setEmail(user.getEmail());
		auth.setLoginIp(ip);
		save(auth);
		session.setAttribute(request, response, AUTH_KEY, auth.getId());
		return auth;
	}

	@Override
	public Authentication retrieve(String authId) {
		long current = System.currentTimeMillis();
		// 是否刷新数据库
		if (refreshTime < current) {
			refreshTime = getNextRefreshTime(current, interval);
			int count = getDao().deleteExpire(new Date(current - timeout));
			log.info("refresh Authentication, delete count: {}", count);
		}
		Authentication auth = findById(authId);
		if (auth != null && auth.getUpdateTime().getTime() + timeout > current) {
			auth.setUpdateTime(new Timestamp(current));
			return auth;
		} else {
			return null;
		}
	}

	@Override
	public Integer retrieveUserIdFromSession(ISessionProvider session, HttpServletRequest request) {
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId == null) {
			return null;
		}
		Authentication auth = retrieve(authId);
		if (auth == null) {
			return null;
		}
		return auth.getUid();
	}

	@Override
	public void storeAuthIdToSession(ISessionProvider session, HttpServletRequest request, HttpServletResponse response, String authId) {
		session.setAttribute(request, response, AUTH_KEY, authId);
	}

	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = getDao().getPage(pageNo, pageSize);
		return page;
	}

	@Override
	public Authentication save(Authentication bean) {
		bean.setId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
		bean.init();
		getDao().save(bean);
		return bean;
	}
}