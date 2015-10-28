
package com.cc.cms.dao.main.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IPermissionDao;
import com.cc.cms.entity.main.Permission;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class PermissionDaoImpl extends CmsDaoImpl<Permission> implements IPermissionDao {

	@Override
	public List<Permission> findAll() {
		String hql = " from Permission bean order by bean.priority asc";
		return find(hql);
	}

	@Override
	public List<Permission> getPermissions(Integer uid) {
		String hql = "from Permission bean where bean.id in" + " (select f1.id from User u join u.roles role join role.permission f1 where u.id = ?) order by bean.priority asc";
		return find(hql, uid);
	}

	@Override
	public List<Permission> getRoots() {
		String hql = "from Permission bean where bean.parent.id is null order by bean.priority asc";
		return find(hql);
	}

	@Override
	public List<Permission> getChild(Integer pid) {
		String hql = "from Permission bean where bean.parent.id = ? order by bean.priority asc";
		return find(hql, pid);
	}

	@Override
	public Permission getPrev(Permission bean) {
		String hql = "from Permission bean where bean.priority<? and bean.parent=? order by bean.priority desc";
		List list = createQuery(hql, bean.getPriority(), bean.getParent()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Permission) list.get(0);
	}

	@Override
	public Permission getNext(Permission bean) {
		String hql = "from Permission bean where bean.priority>? and bean.parent=? order by bean.priority asc";
		List list = createQuery(hql, bean.getPriority(), bean.getParent()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Permission) list.get(0);
	}

	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(bean.priority) from Permission bean ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}
