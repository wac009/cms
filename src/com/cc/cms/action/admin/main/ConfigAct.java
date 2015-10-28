
package com.cc.cms.action.admin.main;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;
import com.cc.core.entity.CoreConfig.ConfigEmailSender;
import com.cc.core.entity.CoreConfig.ConfigLogin;
import com.cc.core.entity.CoreConfig.ConfigMessageTemplate;
import com.cc.core.service.*;

/** @author wangcheng */
@SuppressWarnings("rawtypes")
@Scope("prototype")
@Controller("web.action.admin.configAct")
public class ConfigAct extends CmsAct {

	private static final long		serialVersionUID	= -468477539313849635L;
	private static final Logger		log					= LoggerFactory.getLogger(ConfigAct.class);
	@Autowired
	private IConfigSvc				configSvc;
	@Autowired
	private ICoreConfigSvc			coreConfigSvc;
	private Config					config;
	private MarkConfig				markConfig;
	private MemberConfig			memberConfig;
	private ConfigLogin				loginConfig;
	private ConfigEmailSender		emailSender;
	private ConfigMessageTemplate	messageTemplatePassword;
	private ConfigMessageTemplate	messageTemplateRegister;

	@Override
	public String list() {
		config = configSvc.get();
		return LIST;
	}

	public String systemEdit() {
		config = configSvc.get();
		return "systemEdit";
	}

	public String systemUpdate() {
		configSvc.updateDefault(config);
		log.info("更新成功！");
		addActionMessage("已更新！");
		logOperating("更新系统配置", "更新系统配置成功");
		return "systemEdit";
	}

	public String loginEdit() {
		loginConfig = coreConfigSvc.getConfigLogin();
		emailSender = coreConfigSvc.getEmailSender();
		messageTemplatePassword = coreConfigSvc.getForgotPasswordMessageTemplate();
		messageTemplateRegister = coreConfigSvc.getRegisterMessageTemplate();
		return "loginEdit";
	}

	public String loginUpdate() {
		coreConfigSvc.updateOrSave(loginConfig.getAttr());
		coreConfigSvc.updateOrSave(emailSender.getAttr());
		coreConfigSvc.updateOrSave(messageTemplatePassword.getAttr());
		coreConfigSvc.updateOrSave(messageTemplateRegister.getAttr());
		addActionMessage("已更新！");
		return "loginEdit";
	}

	public String memberEdit() {
		memberConfig = configSvc.get().getMemberConfig();
		return "memberEdit";
	}

	public String memberUpdate() {
		configSvc.updateMemberConfig(memberConfig);
		addActionMessage("已更新！");
		return "memberEdit";
	}

	public String markEdit() {
		markConfig = configSvc.get().getMarkConfig();
		return "markEdit";
	}

	public String markUpdate() {
		configSvc.updateMarkConfig(markConfig);
		log.info("更新成功！");
		addActionMessage("已更新！");
		logOperating("更新水印配置", "更新水印配置成功");
		return "markEdit";
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public MarkConfig getMarkConfig() {
		return markConfig;
	}

	public void setMarkConfig(MarkConfig markConfig) {
		this.markConfig = markConfig;
	}

	public ConfigEmailSender getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(ConfigEmailSender emailSender) {
		this.emailSender = emailSender;
	}

	public MemberConfig getMemberConfig() {
		return memberConfig;
	}

	public void setMemberConfig(MemberConfig memberConfig) {
		this.memberConfig = memberConfig;
	}

	public ConfigLogin getLoginConfig() {
		return loginConfig;
	}

	public void setLoginConfig(ConfigLogin loginConfig) {
		this.loginConfig = loginConfig;
	}

	public ConfigMessageTemplate getMessageTemplatePassword() {
		return messageTemplatePassword;
	}

	public void setMessageTemplatePassword(ConfigMessageTemplate messageTemplatePassword) {
		this.messageTemplatePassword = messageTemplatePassword;
	}

	public ConfigMessageTemplate getMessageTemplateRegister() {
		return messageTemplateRegister;
	}

	public void setMessageTemplateRegister(ConfigMessageTemplate messageTemplateRegister) {
		this.messageTemplateRegister = messageTemplateRegister;
	}
}
