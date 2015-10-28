
package com.cc.common.service;

import java.io.Serializable;
import java.util.*;

import com.cc.common.orm.Condition;
import com.cc.common.orm.Updater;
import com.cc.common.orm.hibernate3.OrderBy;
import com.cc.common.page.Pagination;

public interface IBaseService<T extends Serializable> {

	/**
	 * 通过ID查找对象
	 * 
	 * @param id
	 *            记录的ID
	 * @return 实体对象
	 */
	public T findById(Serializable id);

	public T load(Serializable id);

	/**
	 * 查找所有对象
	 * 
	 * @return 对象列表
	 */
	public List<T> findAll();

	public List<T> findAll(OrderBy... orders);

	public Pagination findAll(int pageNo, int pageSize, OrderBy... orderBys);

	/**
	 * 按属性查找对象列表.
	 */
	public List<T> findByProperty(String property, Object value, OrderBy... orders);

	/**
	 * 按属性查找唯一对象.
	 */
	public T findUniqueByProperty(String property, Object value);

	public T findUniqueByProperty(List<String> property, List<Object> value);

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
	 * @return 结果列表
	 */
	public List<T> findByProperty(List<String> property, List<Object> value);

	public List<T> findByProperty(List<String> property, List<Object> value, OrderBy... orders);

	public List<T> findByProperty(List<String> property, List<String> ops, List<Object> value, OrderBy... orders);

	public Pagination findByProperty(int pageNo, int pageSize, List<String> property, List<Object> value);

	public Pagination findByProperty(int pageNo, int pageSize, List<String> property, List<Object> value, OrderBy... orders);

	public Pagination findByProperty(int pageNo, int pageSize, List<String> property, List<String> ops, List<Object> value, OrderBy... orders);

	/**
	 * 通过示例对象查找对象列表
	 * 
	 * @param eg
	 *            示例对象
	 * @param anyWhere
	 *            是否模糊查询。默认false。
	 * @param conds
	 *            排序及is null。分别为OrderBy和String。
	 * @param exclude
	 *            排除属性
	 * @return
	 */
	public List<T> findByEgList(T eg, boolean rigor, Condition[] conds, String... exclude);

	public List<T> findByEgList(T eg, boolean rigor, String... exclude);

	public List<T> findByEgList(T eg, Condition[] conds, String... exclude);

	public List<T> findByEgList(T eg, boolean rigor, Condition[] conds, int firstResult, int maxResult, String... exclude);

	public List<T> findByEgList(T eg, boolean rigor, int firstResult, int maxResult, String... exclude);

	public List<T> findByEgList(T eg, Condition[] conds, int firstResult, int maxResult, String... exclude);

	public List<T> findByEgList(T eg, String... exclude);

	public Pagination findByEg(T eg, boolean rigor, Condition[] conds, int pageNo, int pageSize, String... exclude);

	public Pagination findByEg(T eg, boolean rigor, int pageNo, int pageSize, String... exclude);

	public Pagination findByEg(T eg, Condition[] conds, int pageNo, int pageSize, String... exclude);

	public Pagination findByEg(T eg, int pageNo, int pageSize, String... exclude);

	/**
	 * 保存对象
	 */
	public T save(T entity);

	/**
	 * 保存并刷新对象，避免many-to-one属性不完整
	 */
	public T saveAndRefresh(T entity);

	public Object saveOrUpdate(Object o);

	public Object update(Object o);

	public Object update(Object o, Set<String> include, Set<String> exclude);

	public Object updateAndRefresh(Object entity);

	@SuppressWarnings("rawtypes")
	public Object updateByUpdater(Updater updater);

	public Object updateDefault(Object entity);
	
	public Object merge(Object entity);

	/**
	 * 刷新对象
	 */
	public void refresh(Object entity);

	public void delete(Object o);

	/**
	 * 根据ID删除记录
	 */
	public T deleteById(Serializable id);

	/**
	 * 根据ID数组删除记录，当发生异常时，操作终止并回滚
	 */
	public List<T> deleteById(Serializable[] ids);

	/**
	 * 清除缓存 持久对象变成脱管对象
	 */
	public T evict(T entity);
}
