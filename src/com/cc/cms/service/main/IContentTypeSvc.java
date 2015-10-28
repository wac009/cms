
package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.main.ContentType;
import com.cc.cms.service.ICmsSvc;

/**
 * @author wangcheng
 */
public interface IContentTypeSvc extends ICmsSvc<ContentType> {

	public ContentType getDef();

	public void handleContentTypeChange();

	public void loadAllContentTypeToCache();

	/**
	 * 获得站点内容属性列表
	 * 
	 * @param disabled
	 *            是否停止使用，为null则查询所有。
	 * @return
	 */
	public List<ContentType> getAllTypes();

	public List<ContentType> getEnableList();

	public ContentType getByIdFromCache(Integer id);
}
