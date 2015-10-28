/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.ISensitivityDao;
import com.cc.cms.entity.assist.Sensitivity;

@Repository
public class SensitivityDaoImpl extends CmsDaoImpl<Sensitivity> implements ISensitivityDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Sensitivity> getList(boolean cacheable) {
		String hql = "from Sensitivity bean order by bean.id desc";
		return getSession().createQuery(hql).setCacheable(cacheable).list();
	}

	@Override
	public Sensitivity findById(Integer id) {
		Sensitivity entity = get(id);
		return entity;
	}

	@Override
	public Sensitivity save(Sensitivity bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public Sensitivity deleteById(Integer id) {
		Sensitivity entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
}