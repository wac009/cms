
package com.cc.cms.service.main;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.cc.cms.entity.main.Permission;
import com.cc.cms.entity.main.Role;
import com.cc.cms.service.ICmsSvc;

/** @author wangcheng */
public interface IPermissionSvc extends ICmsSvc<Permission> {

	/** 根据ID获取功能
	 * 
	 * @param id
	 * @return */
	@Override
	public Permission findById(Serializable id);

	/** 处理功能变化（增、删、改） */
	public void handleChange();

	/** 加载所有功能权限到缓存 */
	public void loadAllToCache();

	/** 获取所有功能权限 */
	public List<Permission> getAll();

	/** 获得管理员权限 */
	public List<Permission> getPermissions(Integer uid);

	/** 获得管理员快捷菜单 */
	public List<Permission> getQuickMenus(Integer uid);

	/** 根据URL获取Permission */
	public Permission getPermission(String url, Integer uid);

	/** 获得管理员权限项集合 */
	public Set<String> getItems(Integer uid);

	/** 获得所有根节点 */
	public List<Permission> getAllRoots();

	public List<Permission> getRoots(Integer uid);

	public Permission getRoot();

	public Permission getRoot(Integer uid);

	/** 获得列表。去除自身及自身的子菜单
	 * 
	 * @param webId */
	public List<Permission> getListForUpdate(Integer funId);

	/** 获得子节点
	 * 
	 * @param pid */
	public List<Permission> getChild(Integer pid);

	public List<Permission> getQuick();

	/** 过滤菜单函数，
	 * 
	 * @param filter 过滤器 如 根据adminId获取到的菜单集合
	 * @param src 要过滤的对象
	 * @return 过滤后的菜单列表 */
	public List<Permission> filterPermissions(List<Permission> filter, List<Permission> src);

	public Permission filterMenuPermission(Permission permission);

	/** 排序 检测是否可移动
	 * 
	 * @return 排序后的对象 */
	public boolean isUp(Permission bean);

	public boolean isDown(Permission bean);

	/** 排序
	 * 
	 * @return 排序后的对象 */
	public Permission up(Integer id);

	public Permission down(Integer id);

	public Permission getPrev(Permission bean);

	public Permission getNext(Permission bean);

	/** 添加权限 */
	public Permission save(Permission bean, List<Role> roles);

	public Permission update(Permission bean, List<Role> roles);
}
