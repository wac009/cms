
package com.cc.cms.dao.main.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IModelDao;
import com.cc.cms.entity.main.Model;
import com.cc.common.orm.hibernate3.Finder;

@Repository
public class ModelDaoImpl extends CmsDaoImpl<Model> implements IModelDao {
	@Override
	public List<Model> getList(boolean containDisabled) {
		Finder f = Finder.create("from Model bean");
		if (!containDisabled) {
			f.append(" where bean.disabled=false");
		}
		f.append(" order by bean.priority");
		return find(f);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Model getDefModel() {
		String hql = "from Model bean where bean.def=true";
		List<Model> list = getSession().createQuery(hql).setMaxResults(1).list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(bean.priority) from Model bean ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}