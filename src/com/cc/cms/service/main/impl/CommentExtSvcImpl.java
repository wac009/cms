
package com.cc.cms.service.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.ICommentExtDao;
import com.cc.cms.entity.assist.Comment;
import com.cc.cms.entity.assist.CommentExt;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.ICommentExtSvc;
import com.cc.common.orm.Updater;

@Service
@Transactional
public class CommentExtSvcImpl extends CmsSvcImpl<CommentExt> implements ICommentExtSvc {

	@Autowired
	public void setDao(ICommentExtDao dao) {
		super.setDao(dao);
	}

	@Override
	public ICommentExtDao getDao() {
		return (ICommentExtDao) super.getDao();
	}

	@Override
	public CommentExt save(String ip, String text, Comment comment) {
		CommentExt ext = new CommentExt();
		ext.setText(text);
		ext.setIp(ip);
		ext.setComment(comment);
		comment.setCommentExt(ext);
		getDao().save(ext);
		return ext;
	}

	@Override
	public CommentExt update(CommentExt bean) {
		Updater<CommentExt> updater = new Updater<CommentExt>(bean);
		bean = (CommentExt) getDao().updateByUpdater(updater);
		return bean;
	}

	@Override
	public int deleteByContentId(Integer contentId) {
		return getDao().deleteByContentId(contentId);
	}
}