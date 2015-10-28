
package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.main.Site;
import com.cc.cms.service.ICmsSvc;

/**
 * @author wangcheng
 */
public interface IWebsiteSvc extends ICmsSvc<Site> {

	/**
	 * 将所有站点读入缓存
	 */
	public void loadAllToCache();

	/**
	 * 获取所有根站点
	 */
	public List<Site> getRoots();

	/**
	 * 从缓存中获得所有站点列表
	 */
	public List<Site> getAllWebsites();

	/**
	 * 根据ID从缓存查找站点
	 */
	public Site getWebsite(Integer id);

	/**
	 * 根据域名从缓存查找站点。
	 */
	public Site getWebsite(String domain);

	/**
	 * 通过域名别名查找站点
	 * 
	 * @param domainName
	 *            网站别名
	 * @return 如果站点不存在则返回null
	 */
	public Site getByAlias(String domainName);

	/**
	 * 处理站点变化（增、删、改）
	 */
	public void handleWebsiteChange();

	/**
	 * 添加网站
	 */
	@Override
	public Site save(Site site);

	/**
	 * 获得管理员的所有站点
	 */
	public List<Site> getListByUser(Integer adminId);

	/**
	 * 获得列表。去除自身及自身的子站点
	 */
	public List<Site> getListForUpdate(Integer webId);

	/**
	 * 检查域名是否存在
	 */
	public boolean checkDomain(String domain);

	/**
	 * 检查资源路径是否存在
	 */
	public boolean checkResPath(String resPath);

	/**
	 * 删除站点
	 */
	public void delete(Integer id);

	/** 排序 */
	public boolean isUp(Site bean);

	public boolean isDown(Site bean);

	public Site up(Integer id);

	public Site down(Integer id);

	public Site getPrev(Site bean);

	public Site getNext(Site bean);
}
