
package com.cc.cms.dao.main;

import net.sf.ehcache.Ehcache;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.ContentCount;

public interface IContentCountDao extends ICmsDao<ContentCount> {
	public int clearCount(boolean week, boolean month);

	public int copyCount();

	public int freshCacheToDB(Ehcache cache);
}
