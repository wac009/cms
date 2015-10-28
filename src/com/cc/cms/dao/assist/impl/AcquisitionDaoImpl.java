/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IAcquisitionDao;
import com.cc.cms.entity.assist.Acquisition;
import com.cc.common.orm.hibernate3.Finder;

@Repository
public class AcquisitionDaoImpl extends CmsDaoImpl<Acquisition> implements IAcquisitionDao {

	@Override
	public List<Acquisition> getList(Integer siteId) {
		Finder f = Finder.create("from Acquisition bean");
		if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	@Override
	public Acquisition findById(Integer id) {
		Acquisition entity = get(id);
		return entity;
	}

	@Override
	public Acquisition save(Acquisition bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public Acquisition deleteById(Integer id) {
		Acquisition entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	public int countByChannelId(Integer channelId) {
		String hql = "select count(*) from Acquisition bean" + " where bean.channel.id=:channelId";
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).intValue();
	}

	@Override
	public Acquisition getStarted(Integer siteId) {
		Criteria crit = createCriteria(Restrictions.eq("status", Acquisition.START), Restrictions.eq("site.id", siteId)).setMaxResults(1);
		return (Acquisition) crit.uniqueResult();
	}

	@Override
	public Integer getMaxQueue(Integer siteId) {
		Query query = createQuery("select max(bean.queue) from Acquisition bean where bean.site.id=?", siteId);
		return ((Number) query.iterate().next()).intValue();
	}

	@Override
	public List<Acquisition> getLargerQueues(Integer siteId, Integer queueNum) {
		Finder f = Finder.create("from Acquisition bean where bean.queue>:queueNum and bean.site.id=:siteId").setParam("queueNum", queueNum).setParam("siteId", siteId);
		return find(f);
	}

	@Override
	public Acquisition popAcquFromQueue(Integer siteId) {
		Query query = getSession().createQuery("from Acquisition bean where bean.queue>0 and bean.site.id=:siteId order by bean.queue").setParameter("siteId", siteId).setMaxResults(1);
		return (Acquisition) query.uniqueResult();
	}
}