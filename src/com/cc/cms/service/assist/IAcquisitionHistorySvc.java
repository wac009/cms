
package com.cc.cms.service.assist;

import java.util.List;

import com.cc.cms.entity.assist.AcquisitionHistory;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

public interface IAcquisitionHistorySvc extends ICmsSvc<AcquisitionHistory> {

	public List<AcquisitionHistory> getList(Integer siteId, Integer acquId);

	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo, Integer pageSize);

	public AcquisitionHistory findById(Integer id);

	@Override
	public AcquisitionHistory save(AcquisitionHistory bean);

	public AcquisitionHistory update(AcquisitionHistory bean);

	public AcquisitionHistory deleteById(Integer id);

	public AcquisitionHistory[] deleteByIds(Integer[] ids);

	public Boolean checkExistByProperties(Boolean title, String value);
}