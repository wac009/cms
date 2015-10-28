/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IVoteTopicDao;
import com.cc.cms.entity.assist.VoteTopic;
import com.cc.common.orm.hibernate3.Finder;

@Repository
public class VoteTopicDaoImpl extends CmsDaoImpl<VoteTopic> implements IVoteTopicDao {

	@Override
	public VoteTopic getDefTopic(Integer siteId) {
		Finder f = Finder.create("from VoteTopic bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" and bean.def=true");
		f.setMaxResults(1);
		return (VoteTopic) f.createQuery(getSession()).uniqueResult();
	}
}