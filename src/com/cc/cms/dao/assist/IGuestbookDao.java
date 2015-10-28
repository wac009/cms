
package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.Guestbook;
import com.cc.common.page.Pagination;
import com.cc.common.util.TimeRange;

public interface IGuestbookDao extends ICmsDao<Guestbook> {

	public Pagination getPage(Integer siteId, Integer ctgId, Integer userId, Boolean recommend, Boolean checked, boolean desc, boolean cacheable, int pageNo,
			int pageSize);

	public List<Guestbook> getList(Integer siteId, Integer ctgId, Boolean recommend, Boolean checked, boolean desc, boolean cacheable, int first, int max);

	public Guestbook findById(Integer id);

	@Override
	public Guestbook save(Guestbook bean);

	public Guestbook deleteById(Integer id);

	public long getCheckedCountByTimeRange(Integer siteId, TimeRange timeRange);
}