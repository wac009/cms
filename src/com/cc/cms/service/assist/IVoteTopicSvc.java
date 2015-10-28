
package com.cc.cms.service.assist;

import java.util.Set;

import com.cc.cms.entity.assist.VoteItem;
import com.cc.cms.entity.assist.VoteTopic;
import com.cc.cms.entity.main.User;
import com.cc.cms.exception.VoteException;
import com.cc.cms.service.ICmsSvc;

public interface IVoteTopicSvc extends ICmsSvc<VoteTopic> {

	public VoteTopic getDefTopic(Integer siteId);

	public VoteTopic saveTopic(VoteTopic bean, Set<VoteItem> items);

	/**
	 * 更新投票主题
	 * 
	 * @param bean
	 *            待更新主题
	 * @param items
	 *            待更新投票项
	 * @return
	 */
	public VoteTopic updateTopic(VoteTopic bean, Set<VoteItem> items);

	/**
	 * 投票
	 * 
	 * @param topicId
	 *            投票主题ID
	 * @param voteItems
	 *            投票选项ID
	 * @param member
	 *            投票会员
	 * @param ip
	 *            投票IP
	 * @param cookie
	 *            投票cookie
	 * @return result of VotoTopic
	 * @throws VoteException
	 */
	public VoteTopic vote(Integer topicId, Integer[] voteItems, String ip, String cookie) throws VoteException;

	public VoteTopic vote(Integer topicId, Integer[] itemIds, User user, String ip, String cookie);
}