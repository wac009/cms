package com.cc.common.orm.hibernate3;

import static org.hibernate.EntityMode.POJO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.cc.common.orm.Condition;
import com.cc.common.orm.IBaseDao;
import com.cc.common.orm.Nullable;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;
import com.cc.common.util.MyBeanUtils;

/** DAO基类。 提供hql分页查询，example分页查询，拷贝更新等功能。 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public abstract class BaseDaoImpl<T extends Serializable> extends HibernateSimpleDao implements IBaseDao<T> {
	protected Logger log = LoggerFactory.getLogger(getClass());
	private Class<T> persistentClass;
	public static final NotBlankPropertySelector NOT_BLANK = new NotBlankPropertySelector();
	protected SessionFactory sessionFactory;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public T save(T entity) {
		Assert.notNull(entity);
		getSession().save(entity);
		return entity;
	}

	@Override
	public Object update(Object entity) {
		Assert.notNull(entity);
		getSession().update(entity);
		return entity;
	}

	@Override
	public Object saveOrUpdate(Object entity) {
		Assert.notNull(entity);
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public Object merge(Object entity) {
		Assert.notNull(entity);
		return getSession().merge(entity);
	}

	@Override
	public void delete(Object entity) {
		Assert.notNull(entity);
		getSession().delete(entity);
	}

	@Override
	public T deleteById(Serializable id) {
		Assert.notNull(id);
		T entity = load(id);
		getSession().delete(entity);
		return entity;
	}

	@Override
	public T load(Serializable id) {
		Assert.notNull(id);
		return load(id, false);
	}

	@Override
	public T get(Serializable id) {
		Assert.notNull(id);
		return (T) getSession().get(getPersistentClass(), id);
	}

	@Override
	public T load(Serializable id, boolean lock) {
		Assert.notNull(id);
		T entity = null;
		if (lock) {
			entity = (T) getSession().load(getPersistentClass(), id, LockMode.UPGRADE);
		} else {
			entity = (T) getSession().load(getPersistentClass(), id);
		}
		return entity;
	}

	@Override
	public List<T> findAll() {
		return findByCriteria();
	}

	@Override
	public List<T> findAll(OrderBy... orders) {
		Criteria crit = createCriteria();
		if (orders != null) {
			for (OrderBy order : orders) {
				crit.addOrder(order.getOrder());
			}
		}
		return crit.list();
	}

	@Override
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
	public Pagination findAll(int pageNo, int pageSize, OrderBy... orders) {
		Criteria crit = createCriteria();
		return findByCriteria(crit, pageNo, pageSize, null, OrderBy.asOrders(orders));
	}

	/**
	 * 直接根据查询sql
	 * 
	 * @param sql
	 * @return
	 */
	protected List findBySql(String sql, Object... values) {
		return createSqlQuery(sql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 *            hql语句
	 * @param values
	 *            数量可变的参数
	 */
	@Override
	protected List find(String hql, Object... values) {
		getSession().clear();
		return createQuery(hql, values).list();
	}

	/** 按HQL查询唯一对象. */
	@Override
	protected Object findUnique(String hql, Object... values) {
		return createQuery(hql, values).uniqueResult();
	}

	/** 按属性查找对象列表. */
	@Override
	public List<T> findByProperty(String property, Object value, OrderBy... orders) {
		Assert.hasText(property);
		List<String> p = new ArrayList<String>();
		List<String> ops = new ArrayList<String>();
		List<Object> v = new ArrayList<Object>();
		p.add(property);
		ops.add("=");
		v.add(value);
		Finder finder = createFinderByProperty(p, ops, v, orders);
		return find(finder);
	}

	@Override
	public List<T> findByProperty(List<String> property, List<String> ops, List<Object> value, OrderBy... orders) {
		Finder finder = createFinderByProperty(property, ops, value, orders);
		return find(finder);
	}

	@Override
	public Pagination findByProperty(int pageNo, int pageSize, List<String> property, List<String> ops, List<Object> value, OrderBy... orders) {
		Finder finder = createFinderByProperty(property, ops, value, orders);
		return find(finder, pageNo, pageSize);
	}

	/** 按属性查找唯一对象. */
	@Override
	public T findUniqueByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return (T) createCriteria(Restrictions.eq(property, value)).uniqueResult();
	}

	@Override
	public int countByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return ((Number) (createCriteria(Restrictions.eq(property, value)).setProjection(Projections.rowCount()).uniqueResult())).intValue();
	}

	/**
	 * 根据参数获取Finder
	 * 
	 * @param property
	 * @param ops
	 * @param value
	 * @param orders
	 * @return Finder
	 */
	protected Finder createFinderByProperty(List<String> property, List<String> ops, List<Object> value, OrderBy... orders) {
		if (property.size() != value.size()) {
			return null;
		}
		Finder f = Finder.create("from " + getPersistentClass().toString().substring(getPersistentClass().toString().lastIndexOf('.') + 1) + " bean");
		appendPropertys(f, property, ops, value, true);
		appendOrder(f, orders);
		return f;
	}

	protected void appendPropertys(Finder f, List<String> property, List<String> ops, List<Object> value, Boolean isFirst) {
		if (ops == null) {
			ops = new ArrayList<String>();
			for (int i = 0; i < value.size(); i++) {
				ops.add("=");
			}
		}
		if (isFirst == null) {
			isFirst = true;
		}
		for (int i = 0; i < property.size(); i++) {
			if (value.get(i) != null && StringUtils.isNotBlank(value.get(i).toString())) {
				if (isFirst) {
					if ("like".equals(ops.get(i))) {
						f.append(" where " + "bean." + property.get(i) + " like :value_" + i).setParam("value_" + i, "%" + value.get(i) + "%");
					} else {
						f.append(" where " + "bean." + property.get(i) + ops.get(i) + ":value_" + i).setParam("value_" + i, value.get(i));
					}
					isFirst = false;
				} else {
					if ("like".equals(ops.get(i))) {
						f.append(" and " + "bean." + property.get(i) + " like :value_" + i).setParam("value_" + i, "%" + value.get(i) + "%");
					} else {
						f.append(" and " + "bean." + property.get(i) + ops.get(i) + ":value_" + i).setParam("value_" + i, value.get(i));
					}
				}
			}
		}
	}

	protected void appendOrder(Finder f, OrderBy... orders) {
		if (orders != null) {
			for (int i = 0; i < orders.length; i++) {
				f.append(" order by " + orders[i].getField() + " " + orders[i].getOrder());
			}
		}
	}

	@Override
	protected Pagination find(Finder finder, int pageNo, int pageSize) {
		int totalCount = countQueryResult(finder);
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		List<T> list = query.list();
		p.setList(list);
		return p;
	}

	@Override
	protected List<T> find(Finder finder) {
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(finder.getFirstResult());
		if (finder.getMaxResults() > 0) {
			query.setMaxResults(finder.getMaxResults());
		}
		List<T> list = query.list();
		return list;
	}

	@Override
	public List<T> findByEgList(T eg, boolean rigor, Condition[] conds, String... exclude) {
		Criteria crit = getCritByEg(eg, rigor, conds, exclude);
		return crit.list();
	}

	@Override
	public List<T> findByEgList(T eg, boolean rigor, Condition[] conds, int firstResult, int maxResult, String... exclude) {
		Criteria crit = getCritByEg(eg, rigor, conds, exclude);
		crit.setFirstResult(firstResult);
		crit.setMaxResults(maxResult);
		return crit.list();
	}

	@Override
	public Pagination findByEg(T eg, boolean rigor, Condition[] conds, int page, int pageSize, String... exclude) {
		Order[] orderArr = null;
		Condition[] condArr = null;
		if (conds != null && conds.length > 0) {
			List<Order> orderList = new ArrayList<Order>();
			List<Condition> condList = new ArrayList<Condition>();
			for (Condition c : conds) {
				if (c instanceof OrderBy) {
					orderList.add(((OrderBy) c).getOrder());
				} else {
					condList.add(c);
				}
			}
			orderArr = new Order[orderList.size()];
			condArr = new Condition[condList.size()];
			orderArr = orderList.toArray(orderArr);
			condArr = condList.toArray(condArr);
		}
		Criteria crit = getCritByEg(eg, rigor, condArr, exclude);
		return findByCriteria(crit, page, pageSize, null, orderArr);
	}

	/** 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数. */
	@Override
	protected Query createQuery(String queryString, Object... values) {
		Assert.hasText(queryString);
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	/** 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数. */
	protected SQLQuery createSqlQuery(String queryString, Object... values) {
		Assert.hasText(queryString);
		SQLQuery queryObject = getSession().createSQLQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	/**
	 * 按Criterion查询对象列表.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 */
	protected List<T> findByCriteria(Criterion... criterion) {
		return createCriteria(criterion).list();
	}

	protected Pagination findByCriteria(Criteria crit, int pageNo, int pageSize, Projection projection, Order... orders) {
		int totalCount = ((Number) crit.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		Pagination p = new Pagination(pageNo, pageSize, totalCount);
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		crit.setProjection(projection);
		if (projection == null) {
			crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (orders != null) {
			for (Order order : orders) {
				crit.addOrder(order);
			}
		}
		crit.setFirstResult(p.getFirstResult());
		crit.setMaxResults(p.getPageSize());
		p.setList(crit.list());
		return p;
	}

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @param finder
	 * @return
	 */
	@Override
	protected int countQueryResult(Finder finder) {
		Query query = getSession().createQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
		return ((Number) query.iterate().next()).intValue();
	}

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @return page对象中的totalCount属性将赋值.
	 */
	protected int countQueryResult(Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;
		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();
		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) MyBeanUtils.getFieldValue(impl, "orderEntries");
			MyBeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			log.error("不可能抛出的异常:{}", e.getMessage());
		}
		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();
		if (totalCount < 1) {
			return 0;
		}
		// 将之前的Projection和OrderBy条件重新设回去
		c.setProjection(projection);
		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			MyBeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			log.error("不可能抛出的异常:{}", e.getMessage());
		}
		return totalCount;
	}

	protected Criteria getCritByEg(T bean, boolean rigor, Condition[] conds, String... exclude) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(bean);
		example.setPropertySelector(NOT_BLANK);
		if (!rigor) {
			example.enableLike(MatchMode.ANYWHERE);
			example.ignoreCase();
		}
		for (String p : exclude) {
			example.excludeProperty(p);
		}
		crit.add(example);
		// 处理排序和is null字段
		if (conds != null) {
			for (Condition o : conds) {
				if (o instanceof OrderBy) {
					OrderBy order = (OrderBy) o;
					crit.addOrder(order.getOrder());
				} else if (o instanceof Nullable) {
					Nullable isNull = (Nullable) o;
					if (isNull.isNull()) {
						crit.add(Restrictions.isNull(isNull.getField()));
					} else {
						crit.add(Restrictions.isNotNull(isNull.getField()));
					}
				} else {
					// never
				}
			}
		}
		// 处理many to one查询
		ClassMetadata cm = getCmd(bean.getClass());
		String[] fieldNames = cm.getPropertyNames();
		for (String field : fieldNames) {
			Object o = cm.getPropertyValue(bean, field, POJO);
			if (o == null) {
				continue;
			}
			ClassMetadata subCm = getCmd(o.getClass());
			if (subCm == null) {
				continue;
			}
			Serializable id = subCm.getIdentifier(o, POJO);
			if (id != null) {
				Serializable idName = subCm.getIdentifierPropertyName();
				crit.add(Restrictions.eq(field + "." + idName, id));
			} else {
				crit.createCriteria(field).add(Example.create(o));
			}
		}
		return crit;
	}

	@Override
	public void refresh(Object entity) {
		getSession().refresh(entity);
	}

	@Override
	public Object updateDefault(Object entity) {
		return updateByUpdater(Updater.create(entity));
	}

	@Override
	public T updateByUpdater(Updater updater) {
		ClassMetadata cm = getCmd(updater.getBean().getClass());
		if (cm == null) {
			throw new RuntimeException("所更新的对象没有映射或不是实体对象");
		}
		T bean = (T) updater.getBean();
		T po = (T) getSession().load(bean.getClass(), cm.getIdentifier(bean, POJO));
		updaterCopyToPersistentObject(updater, po);
		return po;
	}

	@Override
	public T evict(T entity) {
		getSession().evict(entity);
		return entity;
	}

	/**
	 * 将更新对象拷贝至实体对象，并处理many-to-one的更新。
	 * 
	 * @param updater
	 * @param po
	 */
	private void updaterCopyToPersistentObject(Updater updater, Object po) {
		Map map = MyBeanUtils.describe(updater.getBean());
		Set<Map.Entry<String, Object>> set = map.entrySet();
		for (Map.Entry<String, Object> entry : set) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (!updater.isUpdate(name, value)) {
				continue;
			}
			if (value != null) {
				Class valueClass = value.getClass();
				ClassMetadata cm = getCmd(valueClass);
				if (cm != null) {
					Serializable vid = cm.getIdentifier(value, POJO);
					// 如果更新的many to one的对象的id为空，则将many to one设置为null。
					if (vid != null) {
						value = getSession().load(valueClass, vid);
					} else {
						value = null;
					}
				}
			}
			try {
				PropertyUtils.setProperty(po, name, value);
			} catch (Exception e) {
				// never
				log.warn("更新对象时，拷贝属性异常", e);
			}
		}
	}

	/** 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数. */
	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	protected Criteria createCriteria(String[] property, Object[] value, boolean rigor) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		int len = property.length;
		for (int i = 0; i < len; i++) {
			if (value[i] != null && StringUtils.isNotBlank(value[i].toString())) {
				if (rigor) {
					criteria.add(Restrictions.eq(property[i], value[i]));
				} else {
					criteria.add(Restrictions.like(property[i], value[i]));
				}
			}
		}
		return criteria;
	}

	public BaseDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T createNewEntiey() {
		try {
			return getPersistentClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("不能创建实体对象：" + getPersistentClass().getName());
		}
	}

	private ClassMetadata getCmd(Class clazz) {
		return sessionFactory.getClassMetadata(clazz);
	}

	/** 不为空的EXAMPLE属性选择方式 */
	static final class NotBlankPropertySelector implements PropertySelector {
		private static final long serialVersionUID = 1L;

		@Override
		public boolean include(Object object, String property, Type type) {
			return object != null && !(object instanceof String && StringUtils.isBlank((String) object));
		}
	}
}
