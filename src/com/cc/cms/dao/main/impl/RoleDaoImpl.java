package com.cc.cms.dao.main.impl;

import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IRoleDao;
import com.cc.cms.entity.main.Role;


/**
 * @author wangcheng
 */
@Repository
public class RoleDaoImpl extends CmsDaoImpl<Role> implements IRoleDao {
	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(c.priority) from Role c ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}
