
package com.cc.cms.service.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.ITopicDao;
import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.Topic;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IChannelSvc;
import com.cc.cms.service.main.ITopicSvc;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;

/** @author wangcheng */
@Service
@Transactional
public class TopicSvcImpl extends CmsSvcImpl<Topic> implements ITopicSvc {

	@Autowired
	public void setTopicDao(ITopicDao dao) {
		super.setDao(dao);
	}

	@Override
	protected ITopicDao getDao() {
		return (ITopicDao) super.getDao();
	}

	@Autowired
	private IChannelSvc	channelSvc;

	@Override
	@Transactional(readOnly = true)
	public List<Topic> getListForTag(Integer channelId, boolean recommend, Integer count) {
		return getDao().getList(channelId, recommend, count, true);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPageForTag(Integer channelId, boolean recommend, int pageNo, int pageSize) {
		return getDao().getPage(channelId, recommend, pageNo, pageSize, true);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = getDao().getPage(null, false, pageNo, pageSize, false);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Topic> getListByChannel(Integer channelId) {
		List<Topic> list = getDao().getGlobalTopicList();
		Channel c = channelSvc.findById(channelId);
		list.addAll(getDao().getListByChannelIds(c.getNodeIds()));
		return list;
	}

	@Override
	public Topic save(Topic bean) {
		bean.init();
		bean.setPriority(getDao().getMaxPriority() + 1);
		if (bean.getChannel() != null && bean.getChannel().getId() == null) {
			bean.setChannel(null);
		}
		getDao().save(bean);
		refresh(bean);
		return bean;
	}

	@Override
	public Topic update(Topic bean) {
		if (bean.getChannel() != null && bean.getChannel().getId() == null) {
			bean.setChannel(null);
		}
		getDao().updateDefault(bean);
		return bean;
	}

	public String checkForChannelDelete(Integer channelId) {
		if (getDao().countByChannelId(channelId) > 0) {
			return "Topic.error.cannotDeleteChannel";
		} else {
			return null;
		}
	}

	/** 排序 */
	@Override
	public boolean isUp(Topic bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(Topic bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Topic up(Integer id) {
		Topic bean = findById(id);
		Integer oPriority = bean.getPriority();
		Topic beanPre = getDao().getPrev(bean);
		//
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public Topic down(Integer id) {
		Topic bean = findById(id);
		Integer oPriority = bean.getPriority();
		Topic beanNext = getDao().getNext(bean);
		//
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public Topic getPrev(Topic bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public Topic getNext(Topic bean) {
		return getDao().getNext(bean);
	}
}
