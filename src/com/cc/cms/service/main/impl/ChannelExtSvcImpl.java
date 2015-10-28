package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IChannelExtDao;
import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.ChannelExt;
import com.cc.cms.entity.main.base.BaseChannelExt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IChannelExtSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class ChannelExtSvcImpl extends CmsSvcImpl<com.cc.cms.entity.main.ChannelExt> implements IChannelExtSvc {
	
	@Autowired
	public void setDao(IChannelExtDao dao) {
		super.setDao(dao);
	}

	@Override
	public IChannelExtDao getDao() {
		return (IChannelExtDao) super.getDao();
	}
	
	@Override
	public ChannelExt save(ChannelExt ext, Channel channel) {
		channel.setExt(ext);
		ext.setChannel(channel);
		ext.init();
		getDao() .save(ext);
		return ext;
	}

	public ChannelExt update(ChannelExt ext) {
		Updater<ChannelExt> updater = new Updater<ChannelExt>(ext);
		updater.include(BaseChannelExt.PROP_FINAL_STEP);
		updater.include(BaseChannelExt.PROP_AFTER_CHECK);
		ChannelExt entity = (ChannelExt) updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}
}