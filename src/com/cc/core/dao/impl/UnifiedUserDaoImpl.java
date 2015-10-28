
package com.cc.core.dao.impl;

import java.util.*;

import org.hibernate.*;
import org.springframework.stereotype.*;

import com.cc.core.dao.*;
import com.cc.core.entity.*;

@Repository
public class UnifiedUserDaoImpl extends CoreDaoImpl<UnifiedUser> implements IUnifiedUserDao {

	@Override
	public UnifiedUser getByUsername(String username) {
		return findUniqueByProperty("username", username);
	}

	@Override
	public List<UnifiedUser> getByEmail(String email) {
		return findByProperty("email", email);
	}

	@Override
	public int countByEmail(String email) {
		String hql = "select count(*) from UnifiedUser bean where bean.email=:email";
		Query query = getSession().createQuery(hql);
		query.setParameter("email", email);
		return ((Number) query.iterate().next()).intValue();
	}
}