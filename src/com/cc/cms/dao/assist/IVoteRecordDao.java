/** @author wangcheng */

package com.cc.cms.dao.assist;

import java.util.Date;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.VoteRecord;

public interface IVoteRecordDao extends ICmsDao<VoteRecord> {

	public int deleteByTopic(Integer topicId);

	public VoteRecord findByUserId(Integer userId, Integer topicId);

	public VoteRecord findByIp(String ip, Integer topicId);

	public VoteRecord findByCookie(String cookie, Integer topicId);

	public Date getTimeByIp(String voteIp, Integer topicId);

	public Date getTimeByCookie(String voteCookie, Integer topicId);

	public VoteRecord getVoteRecord(String voteIp, String voteCookie, Integer topicId);
}