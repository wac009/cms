package com.cc.cms.service.main;

import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentCheck;
import com.cc.cms.service.ICmsSvc;


/**
 * @author wangcheng
 */
public interface IContentCheckSvc extends ICmsSvc<ContentCheck> {
	public ContentCheck save(ContentCheck check, Content content);
}
