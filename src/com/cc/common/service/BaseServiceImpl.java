
package com.cc.common.service;

import java.io.*;
import java.util.*;

import org.slf4j.*;
import org.springframework.transaction.annotation.*;

import com.cc.common.orm.Condition;
import com.cc.common.orm.IBaseDao;
import com.cc.common.orm.Updater;
import com.cc.common.orm.hibernate3.OrderBy;
import com.cc.common.page.*;

@Transactional
public class BaseServiceImpl<T extends Serializable> implements IBaseService<T> {

	protected Logger	log	= LoggerFactory.getLogger(getClass());
	private IBaseDao<T>	dao;

	public void setDao(IBaseDao<T> dao) {
		this.dao = dao;
	}

	protected IBaseDao<T> getDao() {
		return this.dao;
	}

	@Override
	@Transactional(readOnly = true)
	public T findById(Serializable id) {
		return dao.get(id);
	}

	@Override
	@Transactional(readOnly = true)
	public T load(Serializable id) {
		return dao.load(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll(OrderBy... orders) {
		return dao.findAll(orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination findAll(int pageNo, int pageSize, OrderBy... orders) {
		return dao.findAll(pageNo, pageSize, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public T findUniqueByProperty(String property, Object value) {
		return dao.findUniqueByProperty(property, value);
	}

	@Override
	@Transactional(readOnly = true)
	public T findUniqueByProperty(List<String> property, List<Object> value) {
		List<T> list = findByProperty(property, value);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByProperty(String property, Object value,OrderBy... orders) {
		return dao.findByProperty(property, value, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByProperty(List<String> property, List<Object> value) {
		return findByProperty(property, null, value);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByProperty(List<String> property, List<Object> value, OrderBy... orders) {
		return findByProperty(property, null, value, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByProperty(List<String> property, List<String> ops, List<Object> value, OrderBy... orders) {
		return dao.findByProperty(property, ops, value, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination findByProperty(int pageNo, int pageSize, List<String> property, List<Object> value) {
		return findByProperty(pageNo, pageSize, property, null, value);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination findByProperty(int pageNo, int pageSize, List<String> property, List<Object> value, OrderBy... orders) {
		return findByProperty(pageNo, pageSize, property, null, value, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination findByProperty(int pageNo, int pageSize, List<String> property, List<String> ops, List<Object> value, OrderBy... orders) {
		return dao.findByProperty(pageNo, pageSize, property, ops, value, orders);
	}

	/**
	 * 实例查找返回列表
	 */
	@Override
	@Transactional(readOnly = true)
	public List<T> findByEgList(T eg, boolean rigor, Condition[] conds, String... exclude) {
		return dao.findByEgList(eg, rigor, conds, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByEgList(T eg, boolean rigor, String... exclude) {
		return this.findByEgList(eg, rigor, null, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByEgList(T eg, Condition[] conds, String... exclude) {
		return this.findByEgList(eg, true, conds, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByEgList(T eg, boolean rigor, Condition[] conds, int firstResult, int maxResult, String... exclude) {
		return dao.findByEgList(eg, rigor, conds, firstResult, maxResult, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByEgList(T eg, boolean rigor, int firstResult, int maxResult, String... exclude) {
		return this.findByEgList(eg, rigor, null, firstResult, maxResult, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByEgList(T eg, Condition[] conds, int firstResult, int maxResult, String... exclude) {
		return this.findByEgList(eg, true, conds, firstResult, maxResult, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findByEgList(T eg, String... exclude) {
		return this.findByEgList(eg, true, null, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination findByEg(T eg, boolean rigor, Condition[] conds, int pageNo, int pageSize, String... exclude) {
		return dao.findByEg(eg, rigor, conds, pageNo, pageSize, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination findByEg(T eg, boolean rigor, int pageNo, int pageSize, String... exclude) {
		return this.findByEg(eg, rigor, null, pageNo, pageSize, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination findByEg(T eg, Condition[] conds, int pageNo, int pageSize, String... exclude) {
		return this.findByEg(eg, true, conds, pageNo, pageSize, exclude);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination findByEg(T eg, int pageNo, int pageSize, String... exclude) {
		return this.findByEg(eg, true, null, pageNo, pageSize, exclude);
	}

	@Override
	public T save(T entity) {
		return dao.save(entity);
	}

	@Override
	public T saveAndRefresh(T entity) {
		this.save(entity);
		getDao().refresh(entity);
		return entity;
	}

	@Override
	public Object saveOrUpdate(Object o) {
		getDao().saveOrUpdate(o);
		return o;
	}

	@Override
	public Object update(Object o) {
		getDao().update(o);
		return o;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object update(Object o, Set<String> include, Set<String> exclude) {
		Updater updater = Updater.create(o);
		for (String string : include) {
			updater.include(string);
		}
		for (String string : exclude) {
			updater.exclude(string);
		}
		return updateByUpdater(updater);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object updateByUpdater(Updater o) {
		getDao().updateByUpdater(o);
		return o;
	}

	@Override
	public Object updateDefault(Object entity) {
		return updateByUpdater(Updater.create(entity));
	}

	@Override
	public Object updateAndRefresh(Object entity) {
		getDao().update(entity);
		getDao().refresh(entity);
		return entity;
	}

	@Override
	public Object merge(Object o) {
		return getDao().merge(o);
	}

	@Override
	public void refresh(Object entity) {
		getDao().refresh(entity);
	}

	@Override
	public void delete(Object o) {
		getDao().delete(o);
	}

	@Override
	public T deleteById(Serializable id) {
		if (id == null) {
			return null;
		}
		return dao.deleteById(id);
	}

	@Override
	public List<T> deleteById(Serializable[] ids) {
		List<T> dts = new ArrayList<T>();
		T del = null;
		if (ids != null && ids.length > 0) {
			for (Serializable id : ids) {
				del = deleteById(id);
				if (del != null) {
					dts.add(del);
				}
			}
		}
		return dts;
	}

	@Override
	public T evict(T entity) {
		return getDao().evict(entity);
	}
}
