
package com.cc.cms.dao.main.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IGroupDao;
import com.cc.cms.entity.main.Group;

/** @author wangcheng */
@Repository
public class GroupDaoImpl extends CmsDaoImpl<Group> implements IGroupDao {
	@Override
	public Group getRegDef() {
		String hql = "from Group bean where bean.regDef=true";
		return (Group) findUnique(hql);
	}

	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(c.priority) from Group c ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Group getPrev(Group bean) {
		String hql = "from Group c where c.priority<? order by c.priority desc";
		List<Group> list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Group getNext(Group bean) {
		String hql = "from Group c where c.priority>? order by c.priority asc";
		List<Group> list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
}
