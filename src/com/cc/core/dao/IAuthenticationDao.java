
package com.cc.core.dao;

import java.util.*;

import com.cc.core.entity.*;

public interface IAuthenticationDao extends ICoreDao<Authentication> {

	public int deleteExpire(Date d);

	public Authentication getByUserId(Long userId);
}