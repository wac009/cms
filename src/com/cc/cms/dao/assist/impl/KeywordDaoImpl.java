/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IKeywordDao;
import com.cc.cms.entity.assist.Keyword;
import com.cc.common.orm.hibernate3.Finder;

@Repository
public class KeywordDaoImpl extends CmsDaoImpl<Keyword> implements IKeywordDao {

	@Override
	public List<Keyword> getList(Integer siteId, boolean onlyEnabled, boolean cacheable) {
		Finder f = Finder.create("from Keyword bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (onlyEnabled) {
			f.append(" and bean.disabled=false");
		}
		f.append(" order by bean.id desc");
		f.setCacheable(cacheable);
		return find(f);
	}

	@Override
	public List<Keyword> getListGlobal(boolean onlyEnabled, boolean cacheable) {
		Finder f = Finder.create("from Keyword bean where bean.site.id is null");
		if (onlyEnabled) {
			f.append(" and bean.disabled=false");
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	@Override
	public Keyword findById(Integer id) {
		Keyword entity = get(id);
		return entity;
	}

	@Override
	public Keyword save(Keyword bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public Keyword deleteById(Integer id) {
		Keyword entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
}