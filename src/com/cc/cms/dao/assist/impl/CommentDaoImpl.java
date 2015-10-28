/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.ICommentDao;
import com.cc.cms.entity.assist.Comment;
import com.cc.common.orm.hibernate3.Finder;
import com.cc.common.page.Pagination;

@Repository
public class CommentDaoImpl extends CmsDaoImpl<Comment> implements ICommentDao {

	@Override
	public Pagination getPage(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int pageNo,
			int pageSize, boolean cacheable) {
		Finder f = getFinder(siteId, contentId, null, null, greaterThen, checked, recommend, desc, cacheable);
		return find(f, pageNo, pageSize);
	}

	@Override
	public List<Comment> getList(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int count,
			boolean cacheable) {
		Finder f = getFinder(siteId, contentId, null, null, greaterThen, checked, recommend, desc, cacheable);
		f.setMaxResults(count);
		return find(f);
	}

	@Override
	public Pagination getPageForMember(Integer siteId, Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Boolean checked,
			Boolean recommend, boolean desc, int pageNo, int pageSize, boolean cacheable) {
		Finder f = getFinder(siteId, contentId, toUserId, fromUserId, greaterThen, checked, recommend, desc, cacheable);
		return find(f, pageNo, pageSize);
	}

	@Override
	public List<Comment> getListForDel(Integer siteId, Integer userId, Integer commentUserId, String ip) {
		Finder f = Finder.create("from Comment bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (commentUserId != null) {
			f.append(" and bean.commentUser.id=:commentUserId");
			f.setParam("commentUserId", commentUserId);
		}
		if (userId != null) {
			f.append(" and bean.content.user.id=:fromUserId");
			f.setParam("fromUserId", userId);
		}
		f.setCacheable(false);
		return find(f);
	}

	private Finder getFinder(Integer siteId, Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, boolean cacheable) {
		Finder f = Finder.create("from Comment bean where 1=1");
		if (contentId != null) {
			f.append(" and bean.content.id=:contentId");
			f.setParam("contentId", contentId);
		} else if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (toUserId != null) {
			f.append(" and bean.commentUser.id=:commentUserId");
			f.setParam("commentUserId", toUserId);
		} else if (fromUserId != null) {
			f.append(" and bean.content.user.id=:fromUserId");
			f.setParam("fromUserId", fromUserId);
		}
		if (greaterThen != null) {
			f.append(" and bean.ups>=:greatTo");
			f.setParam("greatTo", greaterThen);
		}
		if (checked != null) {
			f.append(" and bean.checked=:checked");
			f.setParam("checked", checked);
		}
		if (recommend != null) {
			if (recommend) {
				f.append(" and bean.recommend=true");
			}
		}
		if (desc) {
			f.append(" order by bean.id desc");
		} else {
			f.append(" order by bean.id asc");
		}
		f.setCacheable(cacheable);
		return f;
	}

	@Override
	public Comment findById(Integer id) {
		Comment entity = get(id);
		return entity;
	}

	@Override
	public Comment save(Comment bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public Comment deleteById(Integer id) {
		Comment entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	public int deleteByContentId(Integer contentId) {
		String hql = "delete from Comment bean where bean.content.id=:contentId";
		return getSession().createQuery(hql).setParameter("contentId", contentId).executeUpdate();
	}
}