
package com.cc.cms.service.main;

import com.cc.cms.entity.main.User;
import com.cc.cms.entity.main.UserExt;
import com.cc.cms.service.ICmsSvc;

/**
 * @author wangcheng
 */
public interface IUserExtSvc extends ICmsSvc<UserExt> {

	public UserExt save(UserExt ext, User user);

	public UserExt update(UserExt ext, User user);
}
