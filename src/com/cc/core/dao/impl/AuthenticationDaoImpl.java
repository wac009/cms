
package com.cc.core.dao.impl;

import java.util.*;

import org.springframework.stereotype.*;

import com.cc.core.dao.*;
import com.cc.core.entity.*;

@Repository
public class AuthenticationDaoImpl extends CoreDaoImpl<Authentication> implements IAuthenticationDao {
	@Override
	public int deleteExpire(Date d) {
		String hql = "delete Authentication bean where bean.updateTime <= :d";
		return getSession().createQuery(hql).setTimestamp("d", d).executeUpdate();
	}

	@Override
	public Authentication getByUserId(Long userId) {
		String hql = "from Authentication bean where bean.uid=?";
		return (Authentication) findUnique(hql, userId);
	}
}