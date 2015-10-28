/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IAcquisitionTempDao;
import com.cc.cms.entity.assist.AcquisitionTemp;
import com.cc.common.orm.hibernate3.Finder;

@Repository
public class AcquisitionTempDaoImpl extends CmsDaoImpl<AcquisitionTemp> implements IAcquisitionTempDao {

	@Override
	public List<AcquisitionTemp> getList(Integer siteId) {
		Finder f = Finder.create("from AcquisitionTemp bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	@Override
	public AcquisitionTemp findById(Integer id) {
		AcquisitionTemp entity = get(id);
		return entity;
	}

	@Override
	public AcquisitionTemp save(AcquisitionTemp bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public AcquisitionTemp deleteById(Integer id) {
		AcquisitionTemp entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	public Integer getPercent(Integer siteId) {
		Query query = getSession().createQuery("select max(percent) from AcquisitionTemp where site.id=:siteId").setParameter("siteId", siteId);
		return (Integer) (query.uniqueResult() == null ? 0 : query.uniqueResult());
	}

	@Override
	public void clear(Integer siteId, String channelUrl) {
		StringBuilder sb = new StringBuilder(100);
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("delete from AcquisitionTemp where site.id=:siteId");
		params.put("siteId", siteId);
		if (StringUtils.isNotBlank(channelUrl)) {
			sb.append(" and channelUrl!=:channelUrl");
			params.put("channelUrl", channelUrl);
		}
		Query query = getSession().createQuery(sb.toString());
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.executeUpdate();
	}
}