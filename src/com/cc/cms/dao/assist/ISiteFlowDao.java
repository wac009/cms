/** @author wangcheng */

package com.cc.cms.dao.assist;

import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.SiteFlow;

public interface ISiteFlowDao extends ICmsDao<SiteFlow> {

	@Override
	public SiteFlow save(SiteFlow siteFlow);

	public SiteFlow findUniqueByProperties(Integer siteId, String accessDate, String sessionId, String page);
}
