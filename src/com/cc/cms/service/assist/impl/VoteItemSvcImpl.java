
package com.cc.cms.service.assist.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IVoteItemDao;
import com.cc.cms.entity.assist.VoteItem;
import com.cc.cms.entity.assist.VoteTopic;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IVoteItemSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class VoteItemSvcImpl extends CmsSvcImpl<VoteItem> implements IVoteItemSvc {

	@Autowired
	public void setVoteRecordDao(IVoteItemDao dao) {
		super.setDao(dao);
	}

	@Override
	public IVoteItemDao getDao() {
		return (IVoteItemDao) super.getDao();
	}

	@Override
	public Collection<VoteItem> save(Collection<VoteItem> items, VoteTopic topic) {
		for (VoteItem item : items) {
			item.setTopic(topic);
			item.init();
			getDao().save(item);
		}
		return items;
	}

	@Override
	public Collection<VoteItem> update(Collection<VoteItem> items, VoteTopic topic) {
		Set<VoteItem> set = topic.getItems();
		// 先删除
		Set<VoteItem> toDel = new HashSet<VoteItem>();
		for (VoteItem item : set) {
			if (!items.contains(item)) {
				toDel.add(item);
			}
		}
		set.removeAll(toDel);
		// 再更新和增加
		Updater<VoteItem> updater;
		Set<VoteItem> toAdd = new HashSet<VoteItem>();
		for (VoteItem item : items) {
			if (set.contains(item)) {
				// 更新
				updater = new Updater<VoteItem>(item);
				getDao().updateByUpdater(updater);
			} else {
				// 增加
				toAdd.add(item);
			}
		}
		save(toAdd, topic);
		set.addAll(toAdd);
		return set;
	}
}