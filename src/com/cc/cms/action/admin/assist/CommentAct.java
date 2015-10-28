
package com.cc.cms.action.admin.assist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.assist.Comment;
import com.cc.cms.service.main.ICommentSvc;
import com.cc.common.orm.hibernate3.OrderBy;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.commentAct")
public class CommentAct extends CmsAct {

	private static final Logger	log			= LoggerFactory.getLogger(CommentAct.class);
	@Autowired
	private ICommentSvc			commentSvc;
	private Comment				comment;
	private Integer				queryContentId;
	private Boolean				queryChecked;
	private Boolean				queryRecommend;
	/** 查询条件 */
	private List<String>		property	= new ArrayList<String>();
	private List<Object>		value		= new ArrayList<Object>();

	@Override
	public String list() {
		property.add("site.id");
		value.add(getWebId());
		pagination = commentSvc.findByProperty(pageNo, getCookieCount(), property, value, OrderBy.desc("id"));
		return LIST;
	}

	@Override
	public String edit() {
		comment = commentSvc.findById(id);
		return EDIT;
	}

	public String update() {
		// 若回复内容不为空而且回复更新，则设置回复时间，已最新回复时间为准
		if (StringUtils.isNotBlank(comment.getCommentExt().getReply())) {
			comment.setReplayTime(new Date());
		}
		commentSvc.updateDefault(comment);
		return list();
	}

	public String delete() {
		for (Comment bean : commentSvc.deleteByIds(ids)) {
			log.info("删除评论 id={}", bean.getId());
			logOperating("删除评论", "删除评论成功id=" + bean.getId());
		}
		addActionMessage("删除成功");
		return list();
	}

	public Integer getQueryContentId() {
		return queryContentId;
	}

	public void setQueryContentId(Integer queryContentId) {
		this.queryContentId = queryContentId;
	}

	public Boolean getQueryChecked() {
		return queryChecked;
	}

	public void setQueryChecked(Boolean queryChecked) {
		this.queryChecked = queryChecked;
	}

	public Boolean getQueryRecommend() {
		return queryRecommend;
	}

	public void setQueryRecommend(Boolean queryRecommend) {
		this.queryRecommend = queryRecommend;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
}
