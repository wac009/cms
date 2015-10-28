
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.ContentTag;
import com.cc.common.page.Pagination;

/** @author wangcheng */
public interface IContentTagDao extends ICmsDao<ContentTag> {
	public List<ContentTag> getList(Integer count, boolean cacheable);

	public Pagination getPage(String name, int pageNo, int pageSize, boolean cacheable);

	public ContentTag findByName(String name, boolean cacheable);

	public int deleteContentRef(Integer id);

	public int countContentRef(Integer id);
}
