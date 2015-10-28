
package com.cc.cms.service.assist.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IVoteRecordDao;
import com.cc.cms.entity.assist.VoteRecord;
import com.cc.cms.entity.assist.VoteTopic;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IVoteRecordSvc;

@Service
@Transactional
public class VoteRecordSvcImpl extends CmsSvcImpl<VoteRecord> implements IVoteRecordSvc {

	@Autowired
	public void setVoteRecordDao(IVoteRecordDao dao) {
		super.setDao(dao);
	}
	@Override
	public IVoteRecordDao getDao() {
		return (IVoteRecordDao) super.getDao();
	}

	@Override
	public VoteRecord save(VoteTopic topic, User user, String ip, String cookie) {
		VoteRecord record = new VoteRecord();
		record.setTopic(topic);
		record.setIp(ip);
		record.setCookie(cookie);
		record.setTime(new Timestamp(System.currentTimeMillis()));
		getDao().save(record);
		return record;
	}

	@Override
	public int deleteByTopic(Integer topicId) {
		return getDao().deleteByTopic(topicId);
	}

	@Override
	public Date lastVoteTimeByUserId(Integer userId, Integer topicId) {
		VoteRecord record = getDao().findByUserId(userId, topicId);
		return record != null ? record.getTime() : null;
	}

	@Override
	public Date lastVoteTimeByIp(String ip, Integer topicId) {
		VoteRecord record = getDao().findByIp(ip, topicId);
		return record != null ? record.getTime() : null;
	}

	@Override
	public Date lastVoteTimeByCookie(String cookie, Integer topicId) {
		VoteRecord record = getDao().findByCookie(cookie, topicId);
		return record != null ? record.getTime() : null;
	}
	@Override
	public long getTimeByIp(String voteIp, Integer topicId) {
		Date d = getDao().getTimeByIp(voteIp, topicId);
		if (d != null) {
			return d.getTime();
		} else {
			return 0;
		}
	}
	@Override
	public long getTimeByCookie(String voteCookie, Integer topicId) {
		Date d = getDao().getTimeByCookie(voteCookie, topicId);
		if (d != null) {
			return d.getTime();
		} else {
			return 0;
		}
	}
	@Override
	public VoteRecord getVoteRecord(String voteIp, String voteCookie, Integer topicId) {
		return getDao().getVoteRecord(voteIp, voteCookie, topicId);
	}
}