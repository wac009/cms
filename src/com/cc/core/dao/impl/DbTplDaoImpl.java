
package com.cc.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cc.core.entity.*;
import com.cc.common.orm.hibernate3.HibernateBaseDao;
import com.cc.core.dao.DbTplDao;

@Repository
public class DbTplDaoImpl extends HibernateBaseDao<DbTpl, String> implements DbTplDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<DbTpl> getStartWith(String prefix) {
		StringUtils.replace(prefix, "_", "\\_");
		prefix = prefix + "%";
		String hql = "from DbTpl bean where bean.id like ? order by bean.id";
		return find(hql, prefix);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DbTpl> getChild(String path, boolean isDirectory) {
		StringUtils.replace(path, "_", "\\_");
		path = path + "/%";
		String notLike = path + "/%";
		String hql = "from DbTpl bean" + " where bean.id like ? and bean.id not like ?" + " and bean.directory=? order by bean.id";
		return find(hql, path, notLike, isDirectory);
	}

	@Override
	public DbTpl findById(String id) {
		DbTpl entity = get(id);
		return entity;
	}

	@Override
	public DbTpl save(DbTpl bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public DbTpl deleteById(String id) {
		DbTpl entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<DbTpl> getEntityClass() {
		return DbTpl.class;
	}
}