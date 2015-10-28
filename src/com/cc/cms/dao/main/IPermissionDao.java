
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Permission;

/**
 * @author wangcheng
 */
public interface IPermissionDao extends ICmsDao<Permission> {

	/**
	 * 从数据库获取所有记录
	 * 
	 * @return
	 */
	@Override
	public List<Permission> findAll();

	/**
	 * 获得管理员的权限
	 * 
	 * @param uid
	 * @return
	 */
	public List<Permission> getPermissions(Integer uid);

	/**
	 * 获得所有根节点
	 * 
	 * @return
	 */
	public List<Permission> getRoots();

	/**
	 * 获得子节点
	 * 
	 * @param pid
	 * @return
	 */
	public List<Permission> getChild(Integer pid);

	public Permission getPrev(Permission bean);

	public Permission getNext(Permission bean);

	public Integer getMaxPriority();
}
