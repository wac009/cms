
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IUserExtDao;
import com.cc.cms.entity.main.User;
import com.cc.cms.entity.main.UserExt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IUserExtSvc;

/**
 * @author wangcheng
 */
@Service
@Transactional
public class UserExtSvcImpl extends CmsSvcImpl<UserExt> implements IUserExtSvc {

	@Autowired
	public void setDao(IUserExtDao dao) {
		super.setDao(dao);
	}

	@Override
	public IUserExtDao getDao() {
		return (IUserExtDao) super.getDao();
	}

	@Override
	public UserExt save(UserExt ext, User user) {
		ext.blankToNull();
		ext.setUser(user);
		getDao().save(ext);
		return ext;
	}

	@Override
	public UserExt update(UserExt ext, User user) {
		UserExt ue = findById(user.getId());
		if (ue == null) {
			ext = save(ext, user);
			return ext;
		} else {
			updateDefault(ext);
			ext.blankToNull();
			return ext;
		}
	}
}
