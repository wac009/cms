
package com.cc.cms.service.assist;

import java.util.List;

import com.cc.cms.entity.assist.AdvertisingSpace;
import com.cc.cms.service.ICmsSvc;

public interface IAdvertisingSpaceSvc extends ICmsSvc<AdvertisingSpace> {
	public List<AdvertisingSpace> getList(Integer siteId);

	public AdvertisingSpace findById(Integer id);

	@Override
	public AdvertisingSpace save(AdvertisingSpace bean);

	public AdvertisingSpace update(AdvertisingSpace bean);

	public AdvertisingSpace deleteById(Integer id);

	public AdvertisingSpace[] deleteByIds(Integer[] ids);
}