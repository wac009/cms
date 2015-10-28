
package com.cc.cms.action.admin;

import static com.cc.cms.Constants.*;
import static com.cc.core.service.IAuthenticationSvc.*;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.common.security.*;
import com.cc.common.web.*;
import com.cc.core.entity.*;
import com.cc.core.service.*;
import com.octo.captcha.service.image.*;

/** @author wangcheng */
@SuppressWarnings({ "serial", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.loginAct")
public class LoginAct extends CmsAct {

	public static final Logger	log						= LoggerFactory.getLogger(LoginAct.class);
	public static final String	COOKIE_ERROR_REMAINING	= "_error_remaining";
	@Autowired
	private ImageCaptchaService	imageCaptchaService;
	@Autowired
	private IAuthenticationSvc	authSvc;
	@Autowired
	private IUnifiedUserSvc		unifiedUserSvc;
	public String				username;
	public String				password;
	private String				checkCode;

	@Override
	public String index() throws Exception {
		Site site = websiteSvc.getWebsite(domainName);
		if (null == site) {
			return WEBSITE_NOT_FOUND;
		}
		String authId = (String) contextPvd.getSessionAttr(AUTH_KEY);
		if (authId != null) {
			// 存在认证ID
			Authentication auth = authSvc.retrieve(authId);
			// 存在认证信息，且未过期
			if (auth != null) {
				return success();
			}
		}
		return "loginpage";
	}

	public String login() throws Exception {
		Integer errorRemaining = unifiedUserSvc.errorRemaining(username);
		// 校验验证码
		if (checkCodeEnabled || (errorRemaining != null && errorRemaining < 0)) {
			boolean isHuman = imageCaptchaService.validateResponseForID(contextPvd.getSessionId(false), checkCode);
			if (!isHuman) {
				addActionError("验证码错误！");
				setCheckCodeEnabled(true);
				return LOGIN;
			}
		}
		try {
			String ip = getIp();
			Authentication auth = authSvc.login(username, password, ip);
			User user = userSvc.findById(auth.getUid());
			if (user.getDisabled()) {
				// 如果已经禁用，则退出登录。
				logout();
				throw new DisabledException("user disabled");
			}
			removeCookieErrorRemaining();
			// 清除以前登录信息
			contextPvd.logout();
			// 保存当前登录信息
			contextPvd.setSessionAttr(AUTH_KEY, auth.getId());
			contextPvd.setSessionAttr(User.USER_KEY, user.getId());
			contextPvd.setSessionAttr(User.ADMIN_KEY, user.getId());
			contextPvd.setSessionAttr(User.ADMIN_INFO, user);
			// 将快捷菜单放入session
			contextPvd.setSessionAttr(User.QUICKMENUS, permissionSvc.getQuickMenus(user.getId()));
			// 更新登录信息
			userSvc.updateLoginInfo(auth.getUid(), ip);
			// 将权限集放入缓存
			userSvc.loadRightsToCache(user.getId());
			// 显示第一个顶部菜单
			contextPvd.setSessionAttr(TOPMENU_INDEX_URL, new ArrayList<Permission>(permissionSvc.getRoot().getChild()).get(0).getUrl());
			// 登录成功，返回登录后主界面
			return success();
		} catch (UsernameNotFoundException e) {
			addActionError("此用户不存在！");
			setCheckCodeEnabled(true);
		} catch (BadCredentialsException e) {
			addActionError("您输入的用户名或密码错误！");
			setCheckCodeEnabled(true);
		} catch (DisabledException e) {
			addActionError("账户已禁用，请联系网站管理员！");
			setCheckCodeEnabled(true);
		}
		return LOGIN;
	}

	public String logout() {
		String authId = (String) contextPvd.getSessionAttr(AUTH_KEY);
		if (authId != null) {
			authSvc.deleteById(authId);
		}
		// 清除登录信息
		contextPvd.logout();
		return success();
	}

	public String success() {
		setRedirectUrl("/manage/main_index.jspa");
		return SUCCESS;
	}

	private void removeCookieErrorRemaining() {
		CookieUtils.cancleCookie(contextPvd.getRequest(), contextPvd.getResponse(), COOKIE_ERROR_REMAINING, null);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
}
