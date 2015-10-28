
package com.cc.core.dao;

import java.util.*;

import com.cc.core.entity.*;

public interface IUnifiedUserDao extends ICoreDao<UnifiedUser> {

	public UnifiedUser getByUsername(String username);

	public List<UnifiedUser> getByEmail(String email);

	public int countByEmail(String email);
}