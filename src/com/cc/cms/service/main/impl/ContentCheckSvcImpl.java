
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IContentCheckDao;
import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentCheck;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IContentCheckSvc;

/** @author wangcheng */
@Service
@Transactional
public class ContentCheckSvcImpl extends CmsSvcImpl<ContentCheck> implements IContentCheckSvc {

	@Autowired
	public void setDao(IContentCheckDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IContentCheckDao getDao() {
		return (IContentCheckDao) super.getDao();
	}

	@Override
	public ContentCheck save(ContentCheck check, Content content) {
		check.setContent(content);
		check.init();
		getDao().save(check);
		content.setContentCheck(check);
		return check;
	}
}
