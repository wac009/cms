package com.cc.cms.dao.main;

import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Role;


/**
 * @author wangcheng
 */
public interface IRoleDao extends ICmsDao<Role> {
	public Integer getMaxPriority();
}
