
package com.cc.cms.service.main;

import java.util.Collection;
import java.util.List;

import com.cc.cms.entity.main.ContentTag;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

/** @author wangcheng */
public interface IContentTagSvc extends ICmsSvc<ContentTag> {

	public List<ContentTag> getListForTag(Integer count);

	public Pagination getPageForTag(int pageNo, int pageSize);

	public Pagination getPage(String name, int pageNo, int pageSize);

	public ContentTag findByName(String name);

	public ContentTag findByNameForTag(String name);

	/**
	 * 保存标签。不存在的保存，存在的数量加一。
	 * 
	 * @param tagArr
	 *            标签名数组
	 * @return 标签列表
	 */
	public List<ContentTag> saveTags(String[] tagArr);

	public ContentTag saveTag(String name);

	public List<ContentTag> updateTags(List<ContentTag> tags, String[] tagArr);

	public void removeTags(Collection<ContentTag> tags);

	@Override
	public ContentTag save(ContentTag bean);

	public ContentTag deleteById(Integer id);

	public ContentTag[] deleteByIds(Integer[] ids);
}
