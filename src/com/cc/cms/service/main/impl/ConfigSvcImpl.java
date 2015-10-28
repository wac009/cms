
package com.cc.cms.service.main.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IConfigDao;
import com.cc.cms.entity.main.Config;
import com.cc.cms.entity.main.MarkConfig;
import com.cc.cms.entity.main.MemberConfig;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IConfigSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class ConfigSvcImpl extends CmsSvcImpl<Config> implements IConfigSvc {
	@Autowired
	public void setDao(IConfigDao dao) {
		super.setDao(dao);
	}

	@Override
	public IConfigDao getDao() {
		return (IConfigDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public Config get() {
		Config entity = findById(1);
		return entity;
	}

	@Override
	public void updateCountCopyTime(Date d) {
		findById(1).setCountCopyTime(d);
	}

	@Override
	public void updateCountClearTime(Date d) {
		findById(1).setCountClearTime(d);
	}

	public Config update(Config bean) {
		Updater<Config> updater = new Updater<Config>(bean);
		Config entity = (Config) updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}

	@Override
	public MarkConfig updateMarkConfig(MarkConfig mark) {
		get().setMarkConfig(mark);
		return mark;
	}

	@Override
	public void updateMemberConfig(MemberConfig memberConfig) {
		get().getAttr().putAll(memberConfig.getAttr());
	}

}