
package com.cc.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.cc.core.entity.*;
import com.cc.common.orm.hibernate3.HibernateBaseDao;
import com.cc.core.dao.DbFileDao;

@Repository
public class DbFileDaoImpl extends HibernateBaseDao<DbFile, String> implements DbFileDao {

	@Override
	public DbFile findById(String id) {
		DbFile entity = get(id);
		return entity;
	}

	@Override
	public DbFile save(DbFile bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public DbFile deleteById(String id) {
		DbFile entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<DbFile> getEntityClass() {
		return DbFile.class;
	}
}