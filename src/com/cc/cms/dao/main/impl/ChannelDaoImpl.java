
package com.cc.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IChannelDao;
import com.cc.cms.entity.main.Channel;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;

/**
 * @author wangcheng
 */
@SuppressWarnings("rawtypes")
@Repository
public class ChannelDaoImpl extends CmsDaoImpl<Channel> implements IChannelDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Channel> findAll(Integer webId) {
		String hql = "from Channel c where c.site.id=? order by c.priority asc";
		return find(hql, webId);
	}
	
	@Override
	public List<Channel> getTopList(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable) {
		Finder f = getTopFinder(siteId, hasContentOnly, displayOnly, cacheable);
		return find(f);
	}

	@Override
	public Pagination getTopPage(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize) {
		Finder f = getTopFinder(siteId, hasContentOnly, displayOnly, cacheable);
		return find(f, pageNo, pageSize);
	}

	private Finder getTopFinder(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable) {
		Finder f = Finder.create("from Channel bean");
		f.append(" where bean.site.id=:siteId and bean.parent is null");
		f.setParam("siteId", siteId);
		if (hasContentOnly) {
			f.append(" and bean.ext.hasContent=true");
		}
		if (displayOnly) {
			f.append(" and bean.ext.display=true");
		}
		f.append(" order by bean.priority asc,bean.id asc");
		f.setCacheable(cacheable);
		return f;
	}

	@Override
	public List<Channel> getTopListByRigth(Integer userId, Integer siteId, boolean hasContentOnly) {
		Finder f = Finder.create("select bean from Channel bean");
		if (userId!=null) {
			f.append(" join bean.users user");
			f.append(" where user.id=:userId and bean.site.id=:siteId");
			f.setParam("userId", userId).setParam("siteId", siteId);
		}else {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (hasContentOnly) {
			f.append(" and bean.ext.hasContent=true");
		}
		f.append(" and bean.parent is null");
		f.append(" order by bean.priority asc,bean.id asc");
		return find(f);
	}

	@Override
	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable) {
		Finder f = getChildFinder(parentId, hasContentOnly, displayOnly, cacheable);
		return find(f);
	}

	@Override
	public Pagination getChildPage(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize) {
		Finder f = getChildFinder(parentId, hasContentOnly, displayOnly, cacheable);
		return find(f, pageNo, pageSize);
	}

	private Finder getChildFinder(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable) {
		Finder f = Finder.create("from Channel bean");
		f.append(" where bean.parent.id=:parentId");
		f.setParam("parentId", parentId);
		if (hasContentOnly) {
			f.append(" and bean.ext.hasContent=true");
		}
		if (displayOnly) {
			f.append(" and bean.ext.display=true");
		}
		f.append(" order by bean.priority asc,bean.id asc");
		return f;
	}

	@Override
	public List<Channel> getChildListByRight(Integer userId, Integer parentId, boolean hasContentOnly) {
		Finder f = Finder.create("select bean from Channel bean");
		f.append(" join bean.users user");
		f.append(" where user.id=:userId and bean.parent.id=:parentId");
		f.setParam("userId", userId).setParam("parentId", parentId);
		if (hasContentOnly) {
			f.append(" and bean.ext.hasContent=true");
		}
		f.append(" order by bean.priority asc,bean.id asc");
		return find(f);
	}

	@Override
	public Channel findByPath(String path, Integer siteId, boolean cacheable) {
		String hql = "from Channel bean where bean.path=? and bean.site.id=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, path).setParameter(1, siteId);
		// 做一些容错处理，因为毕竟没有在数据库中限定path是唯一的。
		query.setMaxResults(1);
		return (Channel) query.setCacheable(cacheable).uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Channel> getChnlsAndExclude(Integer excludeNode) {
		String hql = "select node from Channel node,Channel enode where (node.lft>enode.rgt or node.lft<enode.lft) and enode.id=? order by node.priority asc";
		return find(hql, excludeNode);
	}
	
	@Override
	public boolean isChild(Integer pid, Integer cid) {
		String hql = "select count(*) from Channel c,Channel p where c.lft between p.lft and p.rgt and c.id=? and p.id=?";
		int count = ((Number) findUnique(hql, cid, pid)).intValue();
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Channel getPrev(Channel bean) {
		String hql = "from Channel c where c.priority<? and c.parent=? order by c.priority desc";
		if (bean.getParent() == null) {
			hql = "from Channel c where c.priority<? and c.parent is null order by c.priority desc";
			List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
			if (list == null || list.size() == 0) {
				return null;
			}
			return (Channel) list.get(0);
		}
		List list = createQuery(hql, bean.getPriority(), bean.getParent()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Channel) list.get(0);
	}
	
	@Override
	public Channel getNext(Channel bean) {
		String hql = "from Channel c where c.priority>? and c.parent=? order by c.priority asc";
		if (bean.getParent() == null) {
			hql = "from Channel c where c.priority>? and c.parent is null order by c.priority desc";
			List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
			if (list == null || list.size() == 0) {
				return null;
			}
			return (Channel) list.get(0);
		}
		List list = createQuery(hql, bean.getPriority(), bean.getParent()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Channel) list.get(0);
	}
	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(c.priority) from Channel c ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}
