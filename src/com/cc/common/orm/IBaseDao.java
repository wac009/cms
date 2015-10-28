package com.cc.common.orm;

import java.io.Serializable;
import java.util.List;

import com.cc.common.orm.hibernate3.OrderBy;
import com.cc.common.page.Pagination;

public interface IBaseDao<T extends Serializable> {
	/**
	 * 通过ID查找对象
	 * 
	 * @param id
	 *            记录的ID
	 * @param lock
	 *            是否锁定对象
	 * @return 实体对象
	 */
	public T load(Serializable id, boolean lock);
	public T get(Serializable id);
	/**
	 * 通过ID查找对象,不锁定对象
	 * 
	 * @param id
	 *            记录的ID
	 * @return 实体对象
	 */
	public T load(Serializable id);
	/**
	 * 查找所有对象
	 * 
	 * @return 对象列表
	 */
	public List<T> findAll();
	public List<T> findAll(OrderBy... orders);
	public Pagination findAll(int pageNo, int pageSize, OrderBy... orders);
	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param eg
	 *            示例对象
	 * @param anyWhere
	 *            是否模糊查询，默认false。
	 * @param conds
	 *            排序和is null的字段。分别为OrderBy和String。
	 * @param exclude
	 *            需要排除的属性
	 * @return 对象列表
	 */
	public List<T> findByEgList(T eg, boolean rigor, Condition[] conds, String... exclude);
	public List<T> findByEgList(T eg, boolean rigor, Condition[] conds, int firstResult, int maxResult, String... exclude);
	public Pagination findByEg(T exampleInstance, boolean anyWhere, Condition[] conds, int pageNo, int pageSize, String... exclude);
	/**
	 * 按属性查找对象列表.
	 */
	public List<T> findByProperty(String property, Object value,OrderBy... orders);
	/**
	 * 按属性查找唯一对象.
	 */
	public T findUniqueByProperty(String property, Object value);
	/**
	 * 根据属性列表查找分页的对象列表
	 * 
	 * @param pageNo
	 *            页号
	 * @param pageSize
	 *            每页数据数
	 * @param property
	 *            字符串数组 参数列表
	 * @param ops
	 *            参数操作符
	 * @param value
	 *            对象数组 值列表
	 * @param orders
	 *            排序参数
	 * @return 查询到结果列表
	 */
	public List<T> findByProperty(List<String> property, List<String> ops, List<Object> value, OrderBy... orders);
	public Pagination getPage(int pageNo, int pageSize);
	public Pagination findByProperty(int pageNo, int pageSize, List<String> property, List<String> ops, List<Object> value,
			OrderBy... orders);
	/**
	 * 按属性查找对象的数量
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public int countByProperty(String property, Object value);
	/**
	 * 根据Updater更新对象
	 * 
	 * @param updater
	 * @return 持久化对象
	 */
	@SuppressWarnings("rawtypes")
	public Object updateByUpdater(Updater updater);
	public Object updateDefault(Object entity);
	/**
	 * 保存对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	public T save(T entity);
	/**
	 * 更新对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	public Object update(Object entity);
	/**
	 * 保存或更新对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	public Object saveOrUpdate(Object entity);
	/**
	 * 保存或更新对象拷贝
	 * 
	 * @param entity
	 * @return 已更新的持久化对象
	 */
	public Object merge(Object entity);
	/**
	 * 删除对象
	 */
	public void delete(Object entity);
	/**
	 * 根据ID删除记录
	 */
	public T deleteById(Serializable id);
	/**
	 * 刷新对象
	 */
	public void refresh(Object entity);
	/**
	 * 获得实体Class
	 */
	public Class<T> getPersistentClass();
	/**
	 * 创建实体类的对象
	 */
	public T createNewEntiey();
	/**
	 * 清除缓存 持久对象变成脱管对象
	 */
	public T evict(T entity);
}
