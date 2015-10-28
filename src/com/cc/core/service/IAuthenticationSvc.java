
package com.cc.core.service;

import javax.servlet.http.*;

import com.cc.cms.service.*;
import com.cc.common.security.*;
import com.cc.common.web.session.*;
import com.cc.core.entity.*;

/**
 * 认证信息管理接口
 * 
 * @author wangcheng
 */
public interface IAuthenticationSvc extends ICmsSvc<Authentication> {

	/** 认证信息session key */
	public static final String	AUTH_KEY	= "_auth_key";

	public Integer retrieveUserIdFromSession(ISessionProvider session, HttpServletRequest request);

	public void storeAuthIdToSession(ISessionProvider session, HttpServletRequest request, HttpServletResponse response, String authId);

	/**
	 * 通过认证ID，获得认证信息。本方法会检查认证是否过期。
	 * 
	 * @param authId
	 *            认证ID
	 * @return 返回Authentication对象。如果authId不存在或已经过期，则返回null。
	 */
	public Authentication retrieve(String authId);

	/**
	 * 登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param ip
	 *            登录IP
	 * @return 认证信息
	 * @throws UsernameNotFoundException
	 *             用户名没有找到
	 * @throws BadCredentialsException
	 *             错误的认证信息，比如密码错误
	 */
	public Authentication login(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException;

	public Authentication login(String username, String password, String ip, HttpServletRequest request, HttpServletResponse response, ISessionProvider session)
			throws UsernameNotFoundException, BadCredentialsException;

	/**
	 * 注册后登录
	 * 
	 * @param user
	 * @param ip
	 *            登录IP
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public Authentication activeLogin(UnifiedUser user, String ip, HttpServletRequest request, HttpServletResponse response, ISessionProvider session);
}