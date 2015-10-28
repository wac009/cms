
package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.Advertising;
import com.cc.common.page.Pagination;

public interface IAdvertisingDao extends ICmsDao<Advertising> {
	public Pagination getPage(Integer siteId, Integer adspaceId, Boolean enabled, int pageNo, int pageSize);

	public List<Advertising> getList(Integer adspaceId, Boolean enabled);

	public Advertising findById(Integer id);

	@Override
	public Advertising save(Advertising bean);

	public Advertising deleteById(Integer id);
}