/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.ICommentExtDao;
import com.cc.cms.entity.assist.CommentExt;
import com.cc.common.page.Pagination;

@Repository
public class CommentExtDaoImpl extends CmsDaoImpl<CommentExt> implements ICommentExtDao {

	@Override
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
	public CommentExt findById(Integer id) {
		CommentExt entity = get(id);
		return entity;
	}

	@Override
	public CommentExt save(CommentExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public int deleteByContentId(Integer contentId) {
		String hql = "delete from CommentExt bean where bean.id in" + " (select c.id from Comment c where c.content.id=:contentId)";
		return getSession().createQuery(hql).setParameter("contentId", contentId).executeUpdate();
	}

	@Override
	public CommentExt deleteById(Integer id) {
		CommentExt entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
}