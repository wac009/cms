
package com.cc.cms.service.assist;

import java.util.List;
import java.util.Map;

import com.cc.cms.entity.assist.Advertising;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

public interface IAdvertisingSvc extends ICmsSvc<Advertising> {
	public Pagination getPage(Integer siteId, Integer adspaceId, Boolean enabled, int pageNo, int pageSize);

	public List<Advertising> getList(Integer adspaceId, Boolean enabled);

	public Advertising findById(Integer id);

	public Advertising save(Advertising bean, Integer adspaceId, Map<String, String> attr);

	public Advertising update(Advertising bean, Integer adspaceId, Map<String, String> attr);

	public Advertising deleteById(Integer id);

	public Advertising[] deleteByIds(Integer[] ids);

	public void display(Integer id);

	public void click(Integer id);
}