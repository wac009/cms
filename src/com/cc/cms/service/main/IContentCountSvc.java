
package com.cc.cms.service.main;

import net.sf.ehcache.Ehcache;

import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentCount;
import com.cc.cms.service.ICmsSvc;

public interface IContentCountSvc extends ICmsSvc<ContentCount> {
	public int contentUp(Integer id);

	public int contentDown(Integer id);

	public void downloadCount(Integer contentId);

	public void commentCount(Integer contentId);

	public int freshCacheToDB(Ehcache cache);

	public ContentCount save(ContentCount count, Content content);

}
