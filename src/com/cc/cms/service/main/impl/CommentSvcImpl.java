
package com.cc.cms.service.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.ICommentDao;
import com.cc.cms.entity.assist.Comment;
import com.cc.cms.entity.assist.CommentExt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.ICommentExtSvc;
import com.cc.cms.service.main.ICommentSvc;
import com.cc.cms.service.main.IContentCountSvc;
import com.cc.cms.service.main.IContentSvc;
import com.cc.cms.service.main.ISensitivitySvc;
import com.cc.cms.service.main.IUserSvc;
import com.cc.cms.service.main.IWebsiteSvc;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;

@Service
@Transactional
public class CommentSvcImpl extends CmsSvcImpl<Comment> implements ICommentSvc {

	@Autowired
	private ISensitivitySvc		cmsSensitivityMng;
	@Autowired
	private IUserSvc			cmsUserMng;
	@Autowired
	private IWebsiteSvc			cmsSiteMng;
	@Autowired
	private IContentSvc			contentMng;
	@Autowired
	private IContentCountSvc	contentCountMng;
	@Autowired
	private ICommentExtSvc		cmsCommentExtMng;

	@Autowired
	public void setDao(ICommentDao dao) {
		super.setDao(dao);
	}

	@Override
	public ICommentDao getDao() {
		return (ICommentDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int pageNo, int pageSize) {
		Pagination page = getDao().getPage(siteId, contentId, greaterThen, checked, recommend, desc, pageNo, pageSize, false);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPageForTag(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int pageNo,
			int pageSize) {
		Pagination page = getDao().getPage(siteId, contentId, greaterThen, checked, recommend, desc, pageNo, pageSize, true);
		return page;
	}

	@Override
	public Pagination getPageForMember(Integer siteId, Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Boolean checked,
			Boolean recommend, boolean desc, int pageNo, int pageSize) {
		Pagination page = getDao().getPageForMember(siteId, contentId, toUserId, fromUserId, greaterThen, checked, recommend, desc, pageNo, pageSize, false);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> getListForDel(Integer siteId, Integer userId, Integer commentUserId, String ip) {
		return getDao().getListForDel(siteId, userId, commentUserId, ip);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> getListForTag(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int count) {
		return getDao().getList(siteId, contentId, greaterThen, checked, recommend, desc, count, true);
	}

	@Override
	@Transactional(readOnly = true)
	public Comment findById(Integer id) {
		Comment entity = getDao().findById(id);
		return entity;
	}

	@Override
	public Comment comment(String text, String ip, Integer contentId, Integer siteId, Integer userId, boolean checked, boolean recommend) {
		Comment comment = new Comment();
		comment.setContent(contentMng.findById(contentId));
		comment.setSite(cmsSiteMng.findById(siteId));
		if (userId != null) {
			comment.setCommentUser(cmsUserMng.findById(userId));
		}
		comment.setChecked(checked);
		comment.setRecommend(recommend);
		comment.init();
		getDao().save(comment);
		text = cmsSensitivityMng.replaceSensitivity(text);
		cmsCommentExtMng.save(ip, text, comment);
		contentCountMng.commentCount(contentId);
		return comment;
	}

	@Override
	public Comment update(Comment bean, CommentExt ext) {
		Updater<Comment> updater = new Updater<Comment>(bean);
		bean = (Comment) getDao().updateByUpdater(updater);
		cmsCommentExtMng.update(ext);
		return bean;
	}

	@Override
	public int deleteByContentId(Integer contentId) {
		cmsCommentExtMng.deleteByContentId(contentId);
		return getDao().deleteByContentId(contentId);
	}

	@Override
	public Comment deleteById(Integer id) {
		Comment bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public Comment[] deleteByIds(Integer[] ids) {
		Comment[] beans = new Comment[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
	public void ups(Integer id) {
		Comment comment = findById(id);
		comment.setUps((short) (comment.getUps() + 1));
	}

	@Override
	public void downs(Integer id) {
		Comment comment = findById(id);
		comment.setDowns((short) (comment.getDowns() + 1));
	}
}