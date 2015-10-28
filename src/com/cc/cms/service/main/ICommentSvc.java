
package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.assist.Comment;
import com.cc.cms.entity.assist.CommentExt;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

public interface ICommentSvc extends ICmsSvc<Comment> {

	public Pagination getPage(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int pageNo, int pageSize);

	public Pagination getPageForTag(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int pageNo,
			int pageSize);

	/**
	 * @param siteId
	 * @param contentId
	 * @param toUserId
	 *            写评论的用户
	 * @param fromUserId
	 *            投稿的信息接收到的相关评论
	 * @param greaterThen
	 * @param checked
	 * @param recommend
	 * @param desc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPageForMember(Integer siteId, Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Boolean checked,
			Boolean recommend, boolean desc, int pageNo, int pageSize);

	/**
	 * @param siteId
	 * @param userId
	 *            发表信息用户id
	 * @param commentUserId
	 *            评论用户id
	 * @param ip
	 *            评论来访ip
	 * @return
	 */
	public List<Comment> getListForDel(Integer siteId, Integer userId, Integer commentUserId, String ip);

	public List<Comment> getListForTag(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int count);

	public Comment findById(Integer id);

	public Comment comment(String text, String ip, Integer contentId, Integer siteId, Integer userId, boolean checked, boolean recommend);

	public Comment update(Comment bean, CommentExt ext);

	public int deleteByContentId(Integer contentId);

	public Comment deleteById(Integer id);

	public Comment[] deleteByIds(Integer[] ids);

	public void ups(Integer id);

	public void downs(Integer id);
}