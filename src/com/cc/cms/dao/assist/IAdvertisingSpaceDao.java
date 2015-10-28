
package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.AdvertisingSpace;

public interface IAdvertisingSpaceDao extends ICmsDao<AdvertisingSpace> {
	public List<AdvertisingSpace> getList(Integer siteId);

	public AdvertisingSpace findById(Integer id);

	@Override
	public AdvertisingSpace save(AdvertisingSpace bean);

	public AdvertisingSpace deleteById(Integer id);
}