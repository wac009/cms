
package com.cc.cms.service.main;

import com.cc.cms.entity.main.Site;
import com.cc.cms.entity.main.User;
import com.cc.cms.entity.main.UserSite;
import com.cc.cms.service.ICmsSvc;

/**
 * @author wangcheng
 */
public interface IUserSiteSvc extends ICmsSvc<UserSite> {

	public UserSite save(Site site, User user, Byte step, Boolean allChannel);

	public void updateByUser(User user, Integer siteId, Byte step, Boolean allChannel);

	public void updateByUser(User user, Integer[] siteIds, Byte[] steps, Boolean[] allChannels);

	public int deleteBySiteId(Integer siteId);
}
