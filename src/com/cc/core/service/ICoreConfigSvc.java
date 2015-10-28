
package com.cc.core.service;

import java.util.*;

import com.cc.cms.service.*;
import com.cc.core.entity.*;
import com.cc.core.entity.CoreConfig.ConfigEmailSender;
import com.cc.core.entity.CoreConfig.ConfigLogin;
import com.cc.core.entity.CoreConfig.ConfigMessageTemplate;

public interface ICoreConfigSvc extends ICmsSvc<CoreConfig> {

	public Map<String, String> getMap();

	public ConfigLogin getConfigLogin();

	public ConfigEmailSender getEmailSender();

	public ConfigMessageTemplate getForgotPasswordMessageTemplate();

	public ConfigMessageTemplate getRegisterMessageTemplate();

	public String getValue(String id);

	public void updateOrSave(Map<String, String> map);

	public CoreConfig updateOrSave(String key, String value);

	public CoreConfig deleteById(String id);

	public CoreConfig[] deleteByIds(String[] ids);
}