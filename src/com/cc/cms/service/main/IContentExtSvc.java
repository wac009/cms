package com.cc.cms.service.main;

import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentExt;
import com.cc.cms.service.ICmsSvc;

public interface IContentExtSvc extends ICmsSvc<ContentExt> {
	public ContentExt save(ContentExt ext, Content content);
	public ContentExt update(ContentExt ext, Content content);
}
