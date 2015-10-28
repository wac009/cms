
package com.cc.cms.service.assist.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IGuestbookExtDao;
import com.cc.cms.entity.assist.Guestbook;
import com.cc.cms.entity.assist.GuestbookExt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IGuestbookExtSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class GuestbookExtSvcImpl extends CmsSvcImpl<GuestbookExt> implements IGuestbookExtSvc {

	@Autowired
	public void setDao(IGuestbookExtDao dao) {
		super.setDao(dao);
	}

	@Override
	public IGuestbookExtDao getDao() {
		return (IGuestbookExtDao) super.getDao();
	}

	@Override
	public GuestbookExt save(GuestbookExt ext, Guestbook guestbook) {
		guestbook.setExt(ext);
		ext.setGuestbook(guestbook);
		ext.init();
		getDao().save(ext);
		return ext;
	}

	@Override
	public GuestbookExt update(GuestbookExt ext) {
		Updater<GuestbookExt> updater = new Updater<GuestbookExt>(ext);
		GuestbookExt entity = (GuestbookExt) getDao().updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}
}