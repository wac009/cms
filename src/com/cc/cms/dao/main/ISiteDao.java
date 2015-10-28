
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Site;

/**
 * 站点DAO接口
 * 
 * @author wangcheng
 */
public interface ISiteDao extends ICmsDao<Site> {

	/**
	 * 获得站点数量
	 */
	public int siteCount(boolean cacheable);

	/**
	 * 获得所有站点
	 */
	public List<Site> getList(boolean cacheable);

	public Site findByDomain(String domain, boolean cacheable);

	/**
	 * @param userId
	 */
	public List<Site> getListByUser(Integer userId);

	/**
	 * 获得列表。去除自身及自身的子站点
	 */
	public List<Site> getListForUpdate(Integer webId);

	public List<Site> getListByAdmin(Integer adminId);

	public Site getPrev(Site bean);

	public Site getNext(Site bean);

	public Integer getMaxPriority();
}