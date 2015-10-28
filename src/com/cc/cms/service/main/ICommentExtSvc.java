
package com.cc.cms.service.main;

import com.cc.cms.entity.assist.Comment;
import com.cc.cms.entity.assist.CommentExt;
import com.cc.cms.service.ICmsSvc;

public interface ICommentExtSvc extends ICmsSvc<CommentExt> {

	public CommentExt save(String ip, String text, Comment comment);

	public CommentExt update(CommentExt bean);

	public int deleteByContentId(Integer contentId);
}