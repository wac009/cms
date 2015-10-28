
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.ContentType;

/** @author wangcheng */
public interface IContentTypeDao extends ICmsDao<ContentType> {
	public List<ContentType> getList(boolean containDisabled);

	public ContentType getDef();
}
