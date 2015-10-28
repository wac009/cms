
package com.cc.cms.dao.assist;

/** @author wangcheng */
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.CommentExt;
import com.cc.common.page.Pagination;

public interface ICommentExtDao extends ICmsDao<CommentExt>{

	@Override
	public Pagination getPage(int pageNo, int pageSize);

	public CommentExt findById(Integer id);

	@Override
	public CommentExt save(CommentExt bean);

	public int deleteByContentId(Integer contentId);

	public CommentExt deleteById(Integer id);
}