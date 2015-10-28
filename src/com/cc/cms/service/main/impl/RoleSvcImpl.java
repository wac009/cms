
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IRoleDao;
import com.cc.cms.entity.main.Role;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IRoleSvc;

/** @author wangcheng */
@Service
@Transactional
public class RoleSvcImpl extends CmsSvcImpl<Role> implements IRoleSvc {

	@Autowired
	public void setDao(IRoleDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IRoleDao getDao() {
		return (IRoleDao) super.getDao();
	}

	@Override
	public Role save(Role role) {
		role.setPriority(getDao().getMaxPriority()+1);
		role.setSuper(true);
		return getDao().save(role);
	}
}
