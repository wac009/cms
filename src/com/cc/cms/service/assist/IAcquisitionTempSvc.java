
package com.cc.cms.service.assist;

import java.util.List;

import com.cc.cms.entity.assist.AcquisitionTemp;
import com.cc.cms.service.ICmsSvc;

public interface IAcquisitionTempSvc extends ICmsSvc<AcquisitionTemp> {

	public List<AcquisitionTemp> getList(Integer siteId);

	public AcquisitionTemp findById(Integer id);

	@Override
	public AcquisitionTemp save(AcquisitionTemp bean);

	public AcquisitionTemp update(AcquisitionTemp bean);

	public AcquisitionTemp deleteById(Integer id);

	public AcquisitionTemp[] deleteByIds(Integer[] ids);

	public Integer getPercent(Integer siteId);

	public void clear(Integer siteId);

	public void clear(Integer siteId, String channelUrl);
}