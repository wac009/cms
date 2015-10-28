/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IGuestbookExtDao;
import com.cc.cms.entity.assist.GuestbookExt;

@Repository
public class GuestbookExtDaoImpl extends CmsDaoImpl<GuestbookExt> implements IGuestbookExtDao {

	@Override
	public GuestbookExt findById(Integer id) {
		GuestbookExt entity = get(id);
		return entity;
	}

	@Override
	public GuestbookExt save(GuestbookExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public GuestbookExt deleteById(Integer id) {
		GuestbookExt entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
}