
package com.cc.cms.service.main;

import net.sf.ehcache.*;

import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.core.service.*;

public interface ISiteFlowSvc extends ICoreSvc<SiteFlow> {

	public SiteFlow save(Site site, String ip, String page, String sessionId);

	public SiteFlow findUniqueByProperties(Integer siteId, String accessDate, String sessionId, String page);

	public int freshCacheToDB(Ehcache cache);
}
