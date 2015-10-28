package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.Comment;
import com.cc.common.page.Pagination;

public interface ICommentDao extends ICmsDao<Comment> {
	public Pagination getPage(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int pageNo, int pageSize, boolean cacheable);

	public List<Comment> getList(Integer siteId, Integer contentId, Integer greaterThen, Boolean checked, boolean recommend, boolean desc, int count, boolean cacheable);

	public Comment findById(Integer id);

	public int deleteByContentId(Integer contentId);

	@Override
	public Comment save(Comment bean);

	public Comment deleteById(Integer id);

	public Pagination getPageForMember(Integer siteId, Integer contentId, Integer toUserId, Integer fromUserId, Integer greaterThen, Boolean checked, Boolean recommend, boolean desc, int pageNo,
			int pageSize, boolean cacheable);

	public List<Comment> getListForDel(Integer siteId, Integer userId, Integer commentUserId, String ip);
}