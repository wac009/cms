package com.cc.core.dao.impl;

import java.io.Serializable;

import com.cc.common.orm.hibernate3.BaseDaoImpl;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.core.dao.ICoreDao;

public class CoreDaoImpl<T extends Serializable> extends BaseDaoImpl<T> implements ICoreDao<T> {
	protected Finder createCacheableFinder(String hql) {
		Finder finder = Finder.create(hql);
		finder.setCacheable(true);
		return finder;
	}
}
