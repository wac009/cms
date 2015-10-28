
package com.cc.cms.service.assist.impl;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IVoteTopicDao;
import com.cc.cms.entity.assist.VoteItem;
import com.cc.cms.entity.assist.VoteRecord;
import com.cc.cms.entity.assist.VoteTopic;
import com.cc.cms.entity.assist.base.BaseVoteTopic;
import com.cc.cms.entity.main.User;
import com.cc.cms.exception.VoteException;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IVoteItemSvc;
import com.cc.cms.service.assist.IVoteRecordSvc;
import com.cc.cms.service.assist.IVoteTopicSvc;
import com.cc.common.orm.Updater;
import com.cc.common.util.ComUtils;

@Service
@Transactional
public class VoteTopicSvcImpl extends CmsSvcImpl<VoteTopic> implements IVoteTopicSvc {

	@Autowired
	public void setDao(IVoteTopicDao dao) {
		super.setDao(dao);
	}

	@Override
	public IVoteTopicDao getDao() {
		return (IVoteTopicDao) super.getDao();
	}

	@Autowired
	private IVoteRecordSvc	voteRecordSvc;
	@Autowired
	private IVoteItemSvc	voteItemSvc;

	@Override
	public VoteTopic getDefTopic(Integer siteId) {
		return getDao().getDefTopic(siteId);
	}

	@Override
	public VoteTopic saveTopic(VoteTopic bean, Set<VoteItem> items) {
		bean.init();
		getDao().save(bean);
		voteItemSvc.save(items, bean);
		return bean;
	}

	@Override
	public VoteTopic updateTopic(VoteTopic bean, Set<VoteItem> items) {
		Updater<VoteTopic> updater = new Updater<VoteTopic>(bean);
		updater.include(BaseVoteTopic.PROP_START_TIME);
		updater.include(BaseVoteTopic.PROP_END_TIME);
		getDao().updateByUpdater(updater);
		bean = updater.getBean();
		int totalCount = 0;
		for (VoteItem item : items) {
			totalCount += item.getVoteCount();
		}
		bean.setTotalCount(totalCount);
		bean.setItems(findById(bean.getId()).getItems());
		voteItemSvc.update(items, bean);
		return bean;
	}

	@Override
	public VoteTopic vote(Integer topicId, Integer[] voteItems, String ip, String cookie) throws VoteException {
		VoteTopic topic = findById(topicId);
		if (topic.getDisabled()) {
			throw new VoteException("这个投票已经被关闭！");
		}
		if (voteItems == null || voteItems.length <= 0) {
			return topic;
		}
		if (voteItems.length > topic.getMultiSelect()) {
			throw new VoteException("您投票的选项个数大于允许的个数！");
		}
		long now = System.currentTimeMillis();
		Date start = topic.getStartTime();
		if (start != null && now < start.getTime()) {
			throw new VoteException("投票还没有开始！开始时间是：" + ComUtils.dataFormatWhole(start));
		}
		Date end = topic.getEndTime();
		if (end != null && now > end.getTime()) {
			throw new VoteException("投票已经结束！结束时间是：" + ComUtils.dataFormatWhole(end));
		}
		long repeat = topic.getRepeateHour() * 60 * 60 * 1000;
		long vtime = 0;
		if (topic.getRestrictIp() || cookie == null) {
			vtime = voteRecordSvc.getTimeByIp(ip, topicId);
			if (vtime + repeat > now) {
				throw new VoteException("该主题不能在" + topic.getRepeateHour() + "小时内重复投票。您上次的投票时间是：" + ComUtils.dataFormatWhole(new Date(vtime)));
			}
		}
		if (topic.getRestrictCookie() && cookie != null) {
			vtime = voteRecordSvc.getTimeByCookie(cookie, topicId);
			if (vtime + repeat > now) {
				throw new VoteException("该主题不能在" + topic.getRepeateHour() + "小时内重复投票。您上次的投票时间是：" + ComUtils.dataFormatWhole(new Date(vtime)));
			}
		}
		topic.setTotalCount(topic.getTotalCount() + voteItems.length);
		for (VoteItem vi : topic.getItems()) {
			for (Integer itemId : voteItems) {
				if (vi.getId().equals(itemId)) {
					vi.setVoteCount(vi.getVoteCount() + 1);
				}
			}
		}
		VoteRecord record = voteRecordSvc.getVoteRecord(ip, cookie, topicId);
		if (record == null) {
			record = new VoteRecord();
			record.setTopic(topic);
		}
		record.setCookie(cookie);
		record.setIp(ip);
		record.setTime(ComUtils.now());
		saveOrUpdate(record);
		return topic;
	}

	@Override
	public VoteTopic vote(Integer topicId, Integer[] itemIds, User user, String ip, String cookie) {
		VoteTopic topic = findById(topicId);
		Set<VoteItem> items = topic.getItems();
		int totalCount = topic.getTotalCount();
		for (VoteItem item : items) {
			if (ArrayUtils.contains(itemIds, item.getId())) {
				item.setVoteCount(item.getVoteCount() + 1);
				totalCount++;
			}
		}
		topic.setTotalCount(totalCount);
		// 如果需要限制投票，则需保存投票记录。
		if ((topic.getRepeateHour() == null || topic.getRepeateHour() > 0) && (topic.getRestrictMember() || topic.getRestrictIp() || topic.getRestrictCookie())) {
			voteRecordSvc.save(topic, user, ip, cookie);
		}
		return topic;
	}
}