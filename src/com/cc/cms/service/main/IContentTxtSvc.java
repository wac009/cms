package com.cc.cms.service.main;

import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentTxt;
import com.cc.cms.service.ICmsSvc;

public interface IContentTxtSvc extends ICmsSvc<ContentTxt> {
	public ContentTxt save(ContentTxt txt, Content content);

	public ContentTxt update(ContentTxt txt, Content content);
}
