
package com.cc.cms.dao.main.impl;

import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IUserSiteDao;
import com.cc.cms.entity.main.UserSite;

/**
 * @author wangcheng
 */
@Repository
public class UserSiteDaoImpl extends CmsDaoImpl<UserSite> implements IUserSiteDao {

	@Override
	public int deleteBySiteId(Integer siteId) {
		String hql = "delete from UserSite bean where bean.site.id=:siteId";
		return getSession().createQuery(hql).setParameter("siteId", siteId).executeUpdate();
	}
}
