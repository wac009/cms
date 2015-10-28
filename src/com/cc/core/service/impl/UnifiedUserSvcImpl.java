package com.cc.core.service.impl;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import com.cc.cms.service.*;
import com.cc.common.email.*;
import com.cc.common.page.*;
import com.cc.common.security.*;
import com.cc.common.security.encoder.*;
import com.cc.core.dao.*;
import com.cc.core.entity.CoreConfig.ConfigLogin;
import com.cc.core.entity.*;
import com.cc.core.service.*;

@Service
@Transactional
public class UnifiedUserSvcImpl extends CmsSvcImpl<UnifiedUser> implements IUnifiedUserSvc {
	@Autowired
	private ICoreConfigSvc coreConfigSvc;
	@Autowired
	private IPwdEncoder pwdEncoder;

	@Autowired
	public void setDao(IUnifiedUserDao dao) {
		super.setDao(dao);
	}

	@Override
	public IUnifiedUserDao getDao() {
		return (IUnifiedUserDao) super.getDao();
	}

	@Override
	public UnifiedUser passwordForgotten(Integer userId, EmailSender email, MessageTemplate tpl) {
		UnifiedUser user = findById(userId);
		String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
		user.setResetKey(uuid);
		String resetPwd = RandomStringUtils.randomNumeric(10);
		user.setResetPwd(resetPwd);
		senderEmail(user.getId(), user.getUsername(), user.getEmail(), user.getResetKey(), user.getResetPwd(), email, tpl);
		return user;
	}

	private void senderEmail(final Integer uid, final String username, final String to, final String resetKey, final String resetPwd, final EmailSender email, final MessageTemplate tpl) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(email.getHost());
		sender.setUsername(email.getUsername());
		sender.setPassword(email.getPassword());
		sender.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
				MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, false, email.getEncoding());
				msg.setSubject(tpl.getForgotPasswordSubject());
				msg.setTo(to);
				msg.setFrom(email.getUsername(), email.getPersonal());
				String text = tpl.getForgotPasswordText();
				text = StringUtils.replace(text, "${uid}", String.valueOf(uid));
				text = StringUtils.replace(text, "${username}", username);
				text = StringUtils.replace(text, "${resetKey}", resetKey);
				text = StringUtils.replace(text, "${resetPwd}", resetPwd);
				msg.setText(text);
			}
		});
	}

	private void senderEmail(final String username, final String to, final String activationCode, final EmailSender email, final MessageTemplate tpl) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(email.getHost());
		sender.setUsername(email.getUsername());
		sender.setPassword(email.getPassword());
		sender.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws MessagingException, UnsupportedEncodingException {
				MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, false, email.getEncoding());
				msg.setSubject(tpl.getRegisterSubject());
				msg.setTo(to);
				msg.setFrom(email.getUsername(), email.getPersonal());
				String text = tpl.getRegisterText();
				text = StringUtils.replace(text, "${username}", username);
				text = StringUtils.replace(text, "${activationCode}", activationCode);
				msg.setText(text);
			}
		});
	}

	@Override
	public UnifiedUser resetPassword(Integer userId) {
		UnifiedUser user = findById(userId);
		user.setPassword(pwdEncoder.encodePassword(user.getResetPwd()));
		user.setResetKey(null);
		user.setResetPwd(null);
		return user;
	}

	@Override
	public Integer errorRemaining(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		UnifiedUser user = getByUsername(username);
		if (user == null) {
			return null;
		}
		long now = System.currentTimeMillis();
		ConfigLogin configLogin = coreConfigSvc.getConfigLogin();
		int maxErrorTimes = configLogin.getErrorTimes();
		int maxErrorInterval = configLogin.getErrorInterval() * 60 * 1000;
		Integer errorCount = user.getErrorCount();
		Date errorTime = user.getErrorTime();
		if (errorCount <= 0 || errorTime == null || errorTime.getTime() + maxErrorInterval < now) {
			return maxErrorTimes;
		}
		return maxErrorTimes - errorCount;
	}

	@Override
	public UnifiedUser login(String username, String password, String ip) throws UsernameNotFoundException, BadCredentialsException {
		UnifiedUser user = getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("username not found: " + username);
		}
		if (!pwdEncoder.isPasswordValid(user.getPassword(), password)) {
			updateLoginError(user.getId(), ip);
			throw new BadCredentialsException("password invalid");
		}
		if (!user.getActivation()) {
			throw new BadCredentialsException("account not activated");
		}
		updateLoginSuccess(user.getId(), ip);
		return user;
	}

	public void updateLoginSuccess(Integer userId, String ip) {
		UnifiedUser user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());
		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginTime(user.getCurrentLoginTime());
		user.setLastLoginIp(user.getCurrentLoginIp());
		user.setCurrentLoginTime(now);
		user.setCurrentLoginIp(ip);
		user.setErrorCount(0);
		user.setErrorTime(null);
		user.setErrorIp(null);
	}

	public void updateLoginError(Integer userId, String ip) {
		UnifiedUser user = findById(userId);
		Date now = new Timestamp(System.currentTimeMillis());
		ConfigLogin configLogin = coreConfigSvc.getConfigLogin();
		int errorInterval = configLogin.getErrorInterval();
		Date errorTime = user.getErrorTime();
		user.setErrorIp(ip);
		if (errorTime == null || errorTime.getTime() + errorInterval * 60 * 1000 < now.getTime()) {
			user.setErrorTime(now);
			user.setErrorCount(1);
		} else {
			user.setErrorCount(user.getErrorCount() + 1);
		}
	}

	@Override
	public boolean usernameExist(String username) {
		return getByUsername(username) != null;
	}

	@Override
	public boolean emailExist(String email) {
		return getDao().countByEmail(email) > 0;
	}

	@Override
	public UnifiedUser getByUsername(String username) {
		return getDao().getByUsername(username);
	}

	@Override
	public List<UnifiedUser> getByEmail(String email) {
		return getDao().getByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = getPage(pageNo, pageSize);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public UnifiedUser findById(Integer id) {
		UnifiedUser entity = getDao().get(id);
		return entity;
	}

	@Override
	public UnifiedUser save(String username, String email, String password, String ip) {
		return save(username, email, password, ip, true, null, null);
	}

	@Override
	public UnifiedUser save(String username, String email, String password, String ip, Boolean activation, EmailSender sender, MessageTemplate msgTpl) {
		Date now = new Timestamp(System.currentTimeMillis());
		UnifiedUser user = new UnifiedUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(pwdEncoder.encodePassword(password));
		user.setRegisterIp(ip);
		user.setRegisterTime(now);
		user.setCurrentLoginTime(now);
		user.setCurrentLoginIp(ip);
		user.setLastLoginIp(ip);
		user.setLastLoginTime(now);
		user.setActivation(activation);
		user.init();
		getDao().save(user);
		if (!activation) {
			String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
			user.setActivationCode(uuid);
			senderEmail(username, email, uuid, sender, msgTpl);
		}
		return user;
	}

	/**
	 * @see IUnifiedUserSvc#update(Integer, String, String)
	 */
	@Override
	public UnifiedUser update(Integer id, String password, String email) {
		UnifiedUser user = findById(id);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		if (!StringUtils.isBlank(password)) {
			user.setPassword(pwdEncoder.encodePassword(password));
		}
		return user;
	}

	@Override
	public boolean isPasswordValid(Integer id, String password) {
		UnifiedUser user = findById(id);
		return pwdEncoder.isPasswordValid(user.getPassword(), password);
	}

	@Override
	public UnifiedUser deleteById(Integer id) {
		UnifiedUser bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public UnifiedUser[] deleteByIds(Integer[] ids) {
		UnifiedUser[] beans = new UnifiedUser[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
	public UnifiedUser active(String username, String activationCode) {
		UnifiedUser bean = getByUsername(username);
		bean.setActivation(true);
		bean.setActivationCode(null);
		return bean;
	}

	@Override
	public UnifiedUser activeLogin(UnifiedUser user, String ip) {
		updateLoginSuccess(user.getId(), ip);
		return user;
	}
}