
package com.cc.cms.service.assist;

import java.util.Date;

import com.cc.cms.entity.assist.VoteRecord;
import com.cc.cms.entity.assist.VoteTopic;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.ICmsSvc;

public interface IVoteRecordSvc extends ICmsSvc<VoteRecord> {

	public VoteRecord save(VoteTopic topic, User user, String ip, String cookie);

	public int deleteByTopic(Integer topicId);

	public Date lastVoteTimeByUserId(Integer userId, Integer topicId);

	public Date lastVoteTimeByIp(String ip, Integer topicId);

	public Date lastVoteTimeByCookie(String cookie, Integer topicId);

	public long getTimeByIp(String voteIp, Integer topicId);

	public long getTimeByCookie(String voteCookie, Integer topicId);

	public VoteRecord getVoteRecord(String voteIp, String voteCookie, Integer topicId);
}