package com.cc.core.service;

/**
 * 通用ID缓存
 * <p>
 * 由于hibernate只支持通过ID查找缓存对象，对于通过唯一属性查找对象不适用。 为此，提供通过属性查找ID的公共缓存。
 * </p>
 */
public interface IdCacheSvc extends ICacheSvc {
	/**
	 * 加入缓存
	 * 
	 * @param id
	 * @param key
	 * @param otherKeys
	 *        多个KEY用'@'符号连接
	 */
	public void put(Long id, Object key, Object... otherKeys);
	/**
	 * 获得缓存
	 * 
	 * @param key
	 * @param otherKeys
	 * @return 获得缓存值，缓存不存在则返回null
	 */
	public Long get(Object key, Object... otherKeys);
	/**
	 * 移除缓存
	 * 
	 * @param key
	 * @param otherKeys
	 * @return 成功移除返回true，缓存不存在或其他情况返回false。
	 */
	public boolean remove(Object key, Object... otherKeys);
}
