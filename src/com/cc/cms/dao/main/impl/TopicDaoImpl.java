
package com.cc.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.ITopicDao;
import com.cc.cms.entity.main.Topic;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;

/** @author wangcheng */
@Repository
@SuppressWarnings("unchecked")
public class TopicDaoImpl extends CmsDaoImpl<Topic> implements ITopicDao {

	@Override
	public List<Topic> getList(Integer channelId, boolean recommend, Integer count, boolean cacheable) {
		Finder f = Finder.create("from Topic bean where 1=1");
		if (channelId != null) {
			f.append(" and bean.channel.id=:channelId");
			f.setParam("channelId", channelId);
		}
		if (recommend) {
			f.append(" and bean.recommend=true");
		}
		f.append(" order by bean.priority asc,bean.id desc");
		if (count != null) {
			f.setMaxResults(count);
		}
		f.setCacheable(cacheable);
		return find(f);
	}

	@Override
	public Pagination getPage(Integer channelId, boolean recommend, int pageNo, int pageSize, boolean cacheable) {
		Finder f = Finder.create("from Topic bean where 1=1");
		if (channelId != null) {
			f.append(" and bean.channel.id=:channelId");
			f.setParam("channelId", channelId);
		}
		if (recommend) {
			f.append(" and bean.recommend=true");
		}
		f.append(" order by bean.priority asc,bean.id desc");
		return find(f, pageNo, pageSize);
	}

	@Override
	public List<Topic> getListByChannelIds(Integer[] channelIds) {
		String hql = "from Topic bean where bean.channel.id in (:ids) order by bean.id asc";
		return getSession().createQuery(hql).setParameterList("ids", channelIds).list();
	}

	@Override
	public List<Topic> getListByChannelId(Integer channelId) {
		String hql = "select bean from Topic bean inner join bean.channel as node,Channel parent" + " where node.lft between parent.lft and parent.rgt" + " and parent.id=?" + " order by bean.priority asc,bean.id desc";
		return find(hql, channelId);
	}

	@Override
	public List<Topic> getGlobalTopicList() {
		String hql = "from Topic bean where bean.channel.id is null" + " order by bean.priority asc,bean.id desc";
		return find(hql);
	}

	@Override
	public int deleteContentRef(Integer id) {
		Query query = getSession().getNamedQuery("Topic.deleteContentRef");
		return query.setParameter(0, id).executeUpdate();
	}

	@Override
	public int countByChannelId(Integer channelId) {
		String hql = "select count(*) from Topic bean" + " where bean.channel.id=:channelId";
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).hashCode();
	}

	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(c.priority) from Topic c ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}

	@Override
	public Topic getPrev(Topic bean) {
		String hql = "from Topic c where c.priority<? order by c.priority desc";
		List<Topic> list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Topic getNext(Topic bean) {
		String hql = "from Topic c where c.priority>? order by c.priority asc";
		List<Topic> list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
}
