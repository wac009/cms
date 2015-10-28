
package com.cc.cms.service.assist.impl;

import static com.cc.cms.statistic.Statistic.THISMONTH;
import static com.cc.cms.statistic.Statistic.THISWEEK;
import static com.cc.cms.statistic.Statistic.THISYEAR;
import static com.cc.cms.statistic.Statistic.TODAY;
import static com.cc.cms.statistic.Statistic.YESTERDAY;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IMessageDao;
import com.cc.cms.entity.assist.Message;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IMessageSvc;
import com.cc.cms.service.assist.IReceiverMessageSvc;
import com.cc.cms.statistic.Statistic;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;
import com.cc.common.util.TimeRange;

/**
 * @author wangcheng
 */
@Service
@Transactional
public class MessageSvcImpl extends CmsSvcImpl<Message> implements IMessageSvc {

	@Autowired
	public void setDao(IMessageDao dao) {
		super.setDao(dao);
	}

	@Override
	public IMessageDao getDao() {
		return (IMessageDao) super.getDao();
	}

	@Autowired
	private IReceiverMessageSvc	receiverMessageSvc;

	@Override
	public Pagination getPage(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime, Boolean status,
			Integer box, Boolean cacheable, int pageNo, int pageSize) {
		return getDao().getPage(siteId, sendUserId, receiverUserId, title, sendBeginTime, sendEndTime, status, box, cacheable, pageNo, pageSize);
	}

	@Override
	public List<Message> getList(Integer siteId, Integer sendUserId, Integer receiverUserId, String title, Date sendBeginTime, Date sendEndTime,
			Boolean status, Integer box, Boolean cacheable) {
		return getDao().getList(siteId, sendUserId, receiverUserId, title, sendBeginTime, sendEndTime, status, box, cacheable);
	}

	@Override
	public Integer getCount(Integer siteId, Integer sendUserId, Integer receiverUserId, Boolean status, Integer box) {
		return getDao().getCount(siteId, sendUserId, receiverUserId, status, box);
	}

	@Override
	public Message findById(Integer id) {
		return getDao().findById(id);
	}

	@Override
	public Message save(Message bean) {
		return getDao().save(bean);
	}

	@Override
	public Message update(Message bean) {
		Updater<Message> updater = new Updater<Message>(bean);
		bean = (Message) getDao().updateByUpdater(updater);
		return bean;
	}

	@Override
	public Message deleteById(Integer id) {
		return getDao().deleteById(id);
	}

	@Override
	public Message[] deleteByIds(Integer[] ids) {
		return getDao().deleteByIds(ids);
	}

	@Override
	public Map<String, List<Statistic>> getWelcomeData(Integer siteId) {
		Map<String, List<Statistic>> map = new HashMap<String, List<Statistic>>();
		map.put("today", getListByTimeRange(siteId, getTimeRange(TODAY)));
		map.put("yesterday", getListByTimeRange(siteId, getTimeRange(YESTERDAY)));
		map.put("thisweek", getListByTimeRange(siteId, getTimeRange(THISWEEK)));
		map.put("thismonth", getListByTimeRange(siteId, getTimeRange(THISMONTH)));
		map.put("thisyear", getListByTimeRange(siteId, getTimeRange(THISYEAR)));
		map.put("total", getListByTimeRange(siteId, getTimeRange(-1)));
		return map;
	}

	private List<Statistic> getListByTimeRange(Integer siteId, TimeRange timeRange) {
		List<Statistic> list = new ArrayList<Statistic>();
		list.add(new Statistic(receiverMessageSvc.getCountByTimeRange(siteId, timeRange)));
		list.add(new Statistic(getCountByTimeRange(siteId, timeRange)));
		list.add(new Statistic(getDraftCountByTimeRange(siteId, timeRange)));
		return list;
	}

	private long getCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return getDao().getCountByTimeRange(siteId, timeRange);
	}
	
	private long getDraftCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return getDao().getDraftCountByTimeRange(siteId, timeRange);
	}
}
