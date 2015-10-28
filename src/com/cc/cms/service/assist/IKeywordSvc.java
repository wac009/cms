
package com.cc.cms.service.assist;

import java.util.List;

import com.cc.cms.entity.assist.Keyword;
import com.cc.cms.service.ICmsSvc;

public interface IKeywordSvc extends ICmsSvc<Keyword> {

	public List<Keyword> getListBySiteId(Integer siteId, boolean onlyEnabled, boolean cacheable);

	public String attachKeyword(Integer siteId, String txt);

	public Keyword findById(Integer id);

	@Override
	public Keyword save(Keyword bean);

	public void updateKeywords(Integer[] ids, String[] names, String[] urls, Boolean[] disableds);

	public Keyword deleteById(Integer id);

	public Keyword[] deleteByIds(Integer[] ids);
}