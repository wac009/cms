
package com.cc.core.service.impl;

import java.util.*;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.cc.cms.service.*;
import com.cc.core.dao.*;
import com.cc.core.entity.*;
import com.cc.core.entity.CoreConfig.ConfigEmailSender;
import com.cc.core.entity.CoreConfig.ConfigLogin;
import com.cc.core.entity.CoreConfig.ConfigMessageTemplate;
import com.cc.core.service.*;

@Service
@Transactional
public class CoreConfigSvcImpl extends CmsSvcImpl<CoreConfig> implements ICoreConfigSvc {

	@Autowired
	public void setDao(ICoreConfigDao dao) {
		super.setDao(dao);
	}
	@Override
	public ICoreConfigDao getDao() {
		return (ICoreConfigDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> getMap() {
		List<CoreConfig> list = getDao().getList();
		Map<String, String> map = new HashMap<String, String>(list.size());
		for (CoreConfig coreConfig : list) {
			map.put(coreConfig.getId(), coreConfig.getValue());
		}
		return map;
	}

	@Override
	@Transactional(readOnly = true)
	public String getValue(String id) {
		CoreConfig entity = getDao().findById(id);
		if (entity != null) {
			return entity.getValue();
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigLogin getConfigLogin() {
		return ConfigLogin.create(getMap());
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigEmailSender getEmailSender() {
		return ConfigEmailSender.create(getMap());
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigMessageTemplate getForgotPasswordMessageTemplate() {
		return ConfigMessageTemplate.createForgotPasswordMessageTemplate(getMap());
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigMessageTemplate getRegisterMessageTemplate() {
		return ConfigMessageTemplate.createRegisterMessageTemplate(getMap());
	}

	@Override
	public void updateOrSave(Map<String, String> map) {
		if (map != null && map.size() > 0) {
			for (Entry<String, String> entry : map.entrySet()) {
				updateOrSave(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public CoreConfig updateOrSave(String key, String value) {
		CoreConfig coreConfig = getDao().findById(key);
		if (coreConfig != null) {
			coreConfig.setValue(value);
		} else {
			coreConfig = new CoreConfig(key);
			coreConfig.setValue(value);
			getDao().save(coreConfig);
		}
		return coreConfig;
	}

	@Override
	public CoreConfig deleteById(String id) {
		CoreConfig bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public CoreConfig[] deleteByIds(String[] ids) {
		CoreConfig[] beans = new CoreConfig[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
}