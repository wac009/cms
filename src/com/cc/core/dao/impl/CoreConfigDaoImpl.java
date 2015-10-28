
package com.cc.core.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.core.entity.*;
import com.cc.core.dao.ICoreConfigDao;

@Repository
public class CoreConfigDaoImpl extends CoreDaoImpl<CoreConfig> implements ICoreConfigDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<CoreConfig> getList() {
		String hql = "from CoreConfig";
		return find(hql);
	}

	@Override
	public CoreConfig findById(String id) {
		CoreConfig entity = get(id);
		return entity;
	}

	@Override
	public CoreConfig deleteById(String id) {
		CoreConfig entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
}