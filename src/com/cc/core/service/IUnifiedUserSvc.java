
package com.cc.core.service;

import java.util.List;

import com.cc.cms.service.*;
import com.cc.common.security.*;
import com.cc.core.entity.*;
import com.cc.common.email.EmailSender;
import com.cc.common.email.MessageTemplate;
import com.cc.common.page.Pagination;

public interface IUnifiedUserSvc extends ICmsSvc<UnifiedUser> {

	/**
	 * 忘记密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param email
	 *            发送者邮件信息
	 * @param tpl
	 *            邮件模板。内容模板可用变量${uid}、${username}、${resetKey}、${resetPwd}。
	 * @return
	 */
	public UnifiedUser passwordForgotten(Integer userId, EmailSender email, MessageTemplate tpl);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @return
	 */
	public UnifiedUser resetPassword(Integer userId);

	public Integer errorRemaining(String username);

	public UnifiedUser login(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException;

	public boolean usernameExist(String username);

	public boolean emailExist(String email);

	public UnifiedUser getByUsername(String username);

	public List<UnifiedUser> getByEmail(String email);

	public Pagination getPage(int pageNo, int pageSize);

	public UnifiedUser findById(Integer id);

	public UnifiedUser save(String username, String email, String password, String ip);

	public UnifiedUser save(String username, String email, String password, String ip, Boolean activation, EmailSender sender,
			MessageTemplate msgTpl);

	/**
	 * 修改邮箱和密码
	 * 
	 * @param id
	 *            用户ID
	 * @param password
	 *            未加密密码。如果为null或空串则不修改。
	 * @param email
	 *            电子邮箱。如果为空串则设置为null。
	 * @return
	 */
	public UnifiedUser update(Integer id, String password, String email);

	/**
	 * 密码是否正确
	 * 
	 * @param id
	 *            用户ID
	 * @param password
	 *            未加密密码
	 * @return
	 */
	public boolean isPasswordValid(Integer id, String password);

	public UnifiedUser deleteById(Integer id);

	public UnifiedUser[] deleteByIds(Integer[] ids);

	public UnifiedUser active(String username, String activationCode);

	public UnifiedUser activeLogin(UnifiedUser user, String ip);
}