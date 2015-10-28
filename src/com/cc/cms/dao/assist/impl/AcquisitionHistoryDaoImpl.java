/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IAcquisitionHistoryDao;
import com.cc.cms.entity.assist.AcquisitionHistory;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;

@Repository
public class AcquisitionHistoryDaoImpl extends CmsDaoImpl<AcquisitionHistory> implements IAcquisitionHistoryDao {

	@Override
	public List<AcquisitionHistory> getList(Integer siteId, Integer acquId) {
		Finder f = Finder.create("from AcquisitionHistory bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.acquisition.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (acquId != null) {
			f.append(" and bean.acquisition.id=:acquId");
			f.setParam("acquId", acquId);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	@Override
	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo, Integer pageSize) {
		Finder f = Finder.create("from AcquisitionHistory bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.acquisition.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (acquId != null) {
			f.append(" and bean.acquisition.id=:acquId");
			f.setParam("acquId", acquId);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	@Override
	public AcquisitionHistory findById(Integer id) {
		AcquisitionHistory entity = get(id);
		return entity;
	}

	@Override
	public AcquisitionHistory save(AcquisitionHistory bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public AcquisitionHistory deleteById(Integer id) {
		AcquisitionHistory entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	public Boolean checkExistByProperties(Boolean title, String value) {
		Criteria crit = createCriteria();
		if (title) {
			crit.add(Restrictions.eq("title", value));
		} else {
			crit.add(Restrictions.eq("contentUrl", value));
		}
		return crit.list().size() > 0 ? true : false;
	}
}