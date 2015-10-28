package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.AcquisitionTemp;

public interface IAcquisitionTempDao extends ICmsDao<AcquisitionTemp> {
	public List<AcquisitionTemp> getList(Integer siteId);

	public AcquisitionTemp findById(Integer id);

	@Override
	public AcquisitionTemp save(AcquisitionTemp bean);

	public AcquisitionTemp deleteById(Integer id);
	
	public Integer getPercent(Integer siteId);
	
	public void clear(Integer siteId,String channelUrl);
}