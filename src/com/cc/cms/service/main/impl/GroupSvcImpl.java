
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IGroupDao;
import com.cc.cms.entity.main.Group;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IGroupSvc;

/** @author wangcheng */
@Service
@Transactional
public class GroupSvcImpl extends CmsSvcImpl<Group> implements IGroupSvc {

	@Autowired
	public void setDao(IGroupDao dao) {
		super.setDao(dao);
	}

	@Override
	public IGroupDao getDao() {
		return (IGroupDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public Group getRegDef() {
		return getDao().getRegDef();
	}

	@Override
	public void updateRegDef(Integer regDefId) {
		if (regDefId != null) {
			for (Group g : getDao().findAll()) {
				if (g.getId().equals(regDefId)) {
					g.setRegDef(true);
				} else {
					g.setRegDef(false);
				}
			}
		}
	}

	@Override
	public Group save(Group group) {
		group.setPriority(getDao().getMaxPriority()+1);
		if (group.getRegDef() == null) {
			group.setRegDef(false);
		}
		getDao().save(group);
		return group;
	}
}
