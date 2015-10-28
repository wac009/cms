
package com.cc.cms.service.main;

import com.cc.cms.entity.main.Group;
import com.cc.cms.service.ICmsSvc;

/** @author wangcheng */
public interface IGroupSvc extends ICmsSvc<Group> {

	public Group getRegDef();

	public void updateRegDef(Integer regDefId);
	
	@Override
	public Group save(Group group);
}
