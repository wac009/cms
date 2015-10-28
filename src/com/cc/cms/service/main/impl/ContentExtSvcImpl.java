
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IContentExtDao;
import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentExt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IContentExtSvc;

@Service
@Transactional
public class ContentExtSvcImpl extends CmsSvcImpl<ContentExt> implements IContentExtSvc {

	@Autowired
	public void setDao(IContentExtDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IContentExtDao getDao() {
		return (IContentExtDao) super.getDao();
	}

	@Override
	public ContentExt save(ContentExt ext, Content content) {
		content.setContentExt(ext);
		ext.setContent(content);
		if (ext.getReleaseDate() == null) {
			ext.setReleaseDate(content.getSortDate());
		}
		ext.init();
		getDao().save(ext);
		content.setContentExt(ext);
		return ext;
	}
	
	@Override
	public ContentExt update(ContentExt ext, Content content) {
		content.setContentExt(ext);
		ext.setContent(content);
		if (ext.getReleaseDate() == null) {
			ext.setReleaseDate(content.getSortDate());
		}
		getDao().updateDefault(ext);
		content.setContentExt(ext);
		return ext;
	}
}
