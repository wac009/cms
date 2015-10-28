/** @author wangcheng */

package com.cc.cms.dao.assist;

import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.VoteTopic;

public interface IVoteTopicDao extends ICmsDao<VoteTopic> {

	public VoteTopic getDefTopic(Integer siteId);

}