
package com.cc.cms.service.main;

import com.cc.cms.entity.main.Role;
import com.cc.cms.service.ICmsSvc;

/**
 * @author wangcheng
 */
public interface IRoleSvc extends ICmsSvc<Role> {
	@Override
	public Role save(Role role);
}
