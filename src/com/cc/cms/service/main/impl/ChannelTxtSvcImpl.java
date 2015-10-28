
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IChannelTxtDao;
import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.ChannelTxt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IChannelTxtSvc;

/**
 * 栏目文本Manager实现
 * 
 * @author wangcheng
 */
@Service
@Transactional
public class ChannelTxtSvcImpl extends CmsSvcImpl<ChannelTxt> implements IChannelTxtSvc {

	@Autowired
	public void setDao(IChannelTxtDao dao) {
		super.setDao(dao);
	}

	@Override
	public IChannelTxtDao getDao() {
		return (IChannelTxtDao) super.getDao();
	}

	/** @see ChannelTxtMng#save(ChannelTxt, Channel) */
	@Override
	public ChannelTxt save(ChannelTxt txt, Channel channel) {
		if (txt.isAllBlank()) {
			return null;
		} else {
			txt.setChannel(channel);
			txt.init();
			getDao().save(txt);
			return txt;
		}
	}

	/** @see ChannelTxtMng#update(ChannelTxt, Channel) */
	@Override
	public ChannelTxt update(ChannelTxt txt, Channel channel) {
		ChannelTxt entity = getDao().get(channel.getId());
		if (entity == null) {
			entity = save(txt, channel);
			// channel.getChannelTxt().add(entity);
			return entity;
		} else {
			if (txt.isAllBlank()) {
				// channel.getChannelTxtSet().clear();
				return null;
			} else {
				updateDefault(entity);
				entity.blankToNull();
				return entity;
			}
		}
	}
}