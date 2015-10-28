/** @author wangcheng */

package com.cc.cms.dao.assist;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.Keyword;

public interface IKeywordDao extends ICmsDao<Keyword> {

	public List<Keyword> getList(Integer siteId, boolean onlyEnabled, boolean cacheable);

	public List<Keyword> getListGlobal(boolean onlyEnabled, boolean cacheable);

	public Keyword findById(Integer id);

	@Override
	public Keyword save(Keyword bean);

	public Keyword deleteById(Integer id);
}