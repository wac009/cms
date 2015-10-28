
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IContentTxtDao;
import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentTxt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IContentTxtSvc;

@Service
@Transactional
public class ContentTxtSvcImpl extends CmsSvcImpl<ContentTxt> implements IContentTxtSvc {
	@Autowired
	public void setDao(IContentTxtDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IContentTxtDao getDao() {
		return (IContentTxtDao) super.getDao();
	}

	@Override
	public ContentTxt save(ContentTxt txt, Content content) {
		if (txt.isAllBlank()) {
			return null;
		} else {
			txt.setContent(content);
			txt.init();
			getDao().save(txt);
			content.setContentTxt(txt);
			return txt;
		}
	}

	@Override
	public ContentTxt update(ContentTxt txt, Content content) {
		ContentTxt entity = findById(content.getId());
		if (entity == null) {
			entity = save(txt, content);
			return entity;
		} else {
			if (txt.isAllBlank()) {
				return null;
			} else {
				updateDefault(txt);
				entity.blankToNull();
				return entity;
			}
		}
	}

}
