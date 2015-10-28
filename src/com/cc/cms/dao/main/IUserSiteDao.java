
package com.cc.cms.dao.main;

import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.UserSite;

/**
 * @author wangcheng
 */
public interface IUserSiteDao extends ICmsDao<UserSite> {

	public int deleteBySiteId(Integer siteId);
}
