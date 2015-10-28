
package com.cc.cms.action.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.User;
import com.cc.common.orm.Updater;
import com.cc.common.security.encoder.IPwdEncoder;
import com.cc.common.util.ComUtils;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.indexAct")
public class IndexAct extends CmsAct {

	public static final Logger	log	= LoggerFactory.getLogger(IndexAct.class);
	@Autowired
	private IPwdEncoder			pwdEncoder;
	private User				user;
	private String				username;
	private String				opassword;
	private String				password;
	private String				confirm_password;
	private String				birthday;

	public String infoedit() {
		user = userSvc.findById(getUserId());
		setBirthday(ComUtils.formatDate(user.getExt().getBirthday(), 2));
		addUploadRule();
		return "infoedit";
	}

	public String infoupdate() {
		handleTime();
		userSvc.updateByUpdater(Updater.create(user));
		userExtSvc.update(user.getExt(), user);
		// 更新session中的用户信息
		contextPvd.setSessionAttr(User.ADMIN_INFO, user);
		log.info("成功修改个人资料 :{}", user.getUsername());
		addActionMessage("成功修改个人资料");
		return "infoedit";
	}

	public String pwdedit() {
		return "pwdedit";
	}

	public String pwdupdate() {
		unifiedUserSvc.update(user.getId(), password, user.getEmail());
		userSvc.updateByUpdater(Updater.create(user));
		log.info("成功修改密码 :{}", user.getUsername());
		addActionMessage("成功修改密码");
		return "pwdedit";
	}

	/** 处理时间 */
	private void handleTime() {
		if (!ComUtils.nullOrBlank(birthday)) {
			user.getExt().setBirthday(ComUtils.parseString2Date(birthday));
		}
	}

	public boolean validatePwdupdate() {
		user = userSvc.findById((Integer) contextPvd.getSessionAttr(User.USER_KEY));
		if (hasErrors()) {
			log.error("发生action/field错误");
			return true;
		}
		if (!pwdEncoder.encodePassword(opassword).equals(unifiedUserSvc.findById(getUserId()).getPassword())) {
			log.error("输入的原始密码错误！");
			addActionError("对不起，您输入的原始密码错误！");
			return true;
		}
		if (!password.equals(confirm_password)) {
			log.error("两次输入的密码不一致！");
			addActionError("对不起，两次输入的密码不一致！");
			return true;
		}
		return false;
	}

	public String findpwd() {
		return "findpwd";
	}

	@Override
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOpassword() {
		return opassword;
	}

	public void setOpassword(String opassword) {
		this.opassword = opassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm_password() {
		return confirm_password;
	}

	public void setConfirm_password(String confirmPassword) {
		confirm_password = confirmPassword;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
