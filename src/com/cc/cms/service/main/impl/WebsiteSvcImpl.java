package com.cc.cms.service.main.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.cc.cms.dao.main.ISiteDao;
import com.cc.cms.entity.main.Site;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IConfigSvc;
import com.cc.cms.service.main.IWebsiteSvc;
import com.cc.common.orm.Updater;
import com.cc.common.util.ComUtils;

/**
 * @author wangcheng
 */
/**
 * 站点管理实现。
 * <p>
 * 系统启动时，加载所有站点信息。使用hibernate二级缓存，应用缓存保存域名domain到id的关系。
 * </p>
 * <ul>
 * <li>修改website是检查是否修改域名，否则清空缓存。</li>
 * <li>添加站点时，加入缓存</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class WebsiteSvcImpl extends CmsSvcImpl<Site> implements IWebsiteSvc {
	Logger log = LoggerFactory.getLogger(WebsiteSvcImpl.class);
	@Autowired
	@Qualifier("website")
	private Cache websiteCache;
	@Autowired
	@Qualifier("websiteDomain")
	private Cache websiteDomainCache;
	@Autowired
	@Qualifier("websiteAlias")
	private Cache websiteAliasCache;
	@Autowired
	private IConfigSvc configSvc;

	@Autowired
	public void setDao(ISiteDao dao) {
		super.setDao(dao);
	}

	@Override
	public ISiteDao getDao() {
		return (ISiteDao) super.getDao();
	}

	@Override
	public void loadAllToCache() {
		websiteDomainCache.removeAll();
		websiteAliasCache.removeAll();
		websiteCache.removeAll();
		List<Site> ws = getDao().findAll();
		log.info(">>>>------共加载："+ws.size()+" 个站点-------<<<<");
		if (!ws.isEmpty()) {
			websiteCache.put(new Element("websites", ws));
			for (Site w : ws) {
				websiteDomainCache.put(new Element(w.getDomain(), w.getId()));
				String[] alias = w.getAlias();
				if (alias != null) {
					for (String a : alias) {
						websiteAliasCache.put(new Element(a, w.getId()));
					}
				}
			}
		}
	}

	@Override
	public void handleWebsiteChange() {
		loadAllToCache();
	}

	@Override
	public List<Site> getRoots() {
		List<Site> list = new ArrayList<Site>();
		for (Site site : getAllWebsites()) {
			if (site.getParent() == null) {
				list.add(site);
			}
		}
		return list;
	}

	@Override
	public List<Site> getAllWebsites() {
		// 从缓存中获取，若缓存空就查询数据库并加入缓存
		Element e = websiteCache.get("websites");
		if (e != null) {
			return (List<Site>) e.getObjectValue();
		} else {
			List<Site> sites = getDao().findAll();
			websiteCache.put(new Element("websites", sites));
			return sites;
		}
	}

	@Override
	public Site getWebsite(Integer id) {
		Assert.notNull(id);
		for (Site site : getAllWebsites()) {
			if (id.equals(site.getId())) {
				return site;
			}
		}
		return null;
	}

	@Override
	public Site getWebsite(String domain) {
		Assert.notNull(domain);
		for (Site site : getAllWebsites()) {
			if (domain.equals(site.getDomain())) {
				return site;
			}
		}
		return null;
	}

	@Override
	public Site getByAlias(String domain) {
		Assert.notNull(domain);
		for (Site site : getAllWebsites()) {
			Assert.notNull(site);
			for (String a : site.getAlias()) {
				if (domain.equals(a)) {
					return site;
				}
			}
		}
		log.warn("get website by alias from cache, domain not exist:{}", domain);
		return null;
	}

	@Override
	public List<Site> getListByUser(Integer adminId) {
		return getDao().getListByUser(adminId);
	}

	@Override
	public List<Site> getListForUpdate(Integer webId) {
		List<Site> sites = new ArrayList<Site>();
		Site site = getWebsite(webId);
		for (Site ws : getAllWebsites()) {
			if (ws.getLft() < site.getLft() && ws.getRgt() > site.getRgt()) {
				sites.add(ws);
			}
		}
		for (Site ws : sites) {
			if (ws.getChild() != null) {
				handleChildForUpdate(ws, site);
			}
		}
		return sites;
	}

	private void handleChildForUpdate(Site bean, Site site) {
		for (Site child : bean.getChild()) {
			if (child.getId().equals(site.getId())) {
				bean.getTreeChild().remove(child);
			} else {
				if (child.getChild() != null) {
					handleChildForUpdate(child, site);
				}
			}
		}
	}

	@Override
	public Site save(Site site) {
		site.init();
		if (site.getParent() != null) {
			site.setRank(findById(site.getParent().getId()).getRank() + 1);
		} else {
			site.setRank(1);
		}
		site.setPriority(getDao().getMaxPriority() + 1);
		site.setCreateTime(ComUtils.now());
		if (site.getConfig() == null) {
			site.setConfig(configSvc.findById(1));
		}
		getDao().save(site);
		return site;
	}

	@Override
	public boolean checkDomain(String domain) {
		for (Site site : getAllWebsites()) {
			if (domain.equals(site.getDomain())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkResPath(String resPath) {
		for (Site site : getAllWebsites()) {
			if (resPath.equals(site.getResPath())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void delete(Integer id) {
		// 删除用户
		// List<Admin> admins=adminSvc.getList(id);
		// for (Iterator iterator = admins.iterator(); iterator.hasNext();) {
		// Admin admin = (Admin) iterator.next();
		// userSvc.delete(admin.getUser());
		// adminSvc.deleteById(admin.getId());
		// }
		// 删除站点资源
		// 删除站点相关数据库
		// 删除站点
		getDao().deleteById(id);
	}

	@Override
	public boolean isUp(Site bean) {
		if (getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(Site bean) {
		if (getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Site up(Integer id) {
		Site bean = findById(id);
		Integer oPriority = bean.getPriority();
		Site beanPre = getPrev(bean);
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public Site down(Integer id) {
		Site bean = findById(id);
		Integer oPriority = bean.getPriority();
		Site beanNext = getNext(bean);
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public Site getPrev(Site bean) {
		Site beanPrev = new Site();
		for (Site site : getAllWebsites()) {
			if (site.getParent() != null && site.getParent().getId() == bean.getParent().getId() && site.getPriority() < bean.getPriority()) {
				if ((beanPrev.getPriority() == null) || (beanPrev.getPriority() != null && beanPrev.getPriority() < site.getPriority())) {
					beanPrev = site;
				}
			}
		}
		return beanPrev.getId() == null ? null : beanPrev;
	}

	@Override
	public Site getNext(Site bean) {
		Site beanNext = new Site();
		for (Site site : getAllWebsites()) {
			if (site.getParent() != null && site.getParent().getId() == bean.getParent().getId() && site.getPriority() > bean.getPriority()) {
				if ((beanNext.getPriority() == null) || (beanNext.getPriority() != null && beanNext.getPriority() > site.getPriority())) {
					beanNext = site;
				}
			}
		}
		return beanNext.getId() == null ? null : beanNext;
	}
}
