
package com.cc.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.core.entity.*;
import com.cc.common.orm.hibernate3.HibernateBaseDao;
import com.cc.core.dao.FtpDao;

@Repository
public class FtpDaoImpl extends HibernateBaseDao<Ftp, Integer> implements FtpDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Ftp> getList() {
		String hql = "from Ftp bean";
		return find(hql);
	}

	@Override
	public Ftp findById(Integer id) {
		Ftp entity = get(id);
		return entity;
	}

	@Override
	public Ftp save(Ftp bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public Ftp deleteById(Integer id) {
		Ftp entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Ftp> getEntityClass() {
		return Ftp.class;
	}
}