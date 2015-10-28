
package com.cc.cms.action.admin.assist;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.assist.*;
import com.cc.cms.service.assist.*;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.advertisingSpaceAct")
public class AdvertisingSpaceAct extends CmsAct {

	private static final Logger		log			= LoggerFactory.getLogger(AdvertisingSpaceAct.class);
	@Autowired
	private IAdvertisingSpaceSvc	advertisingSpaceSvc;
	private AdvertisingSpace		advertisingSpace;
	/** 查询条件 */
	private List<String>			property	= new ArrayList<String>();
	private List<Object>			value		= new ArrayList<Object>();

	@Override
	public String list() {
		property.add("site.id");
		value.add(getWebId());
		pagination = advertisingSpaceSvc.findByProperty(pageNo, getCookieCount(), property, value);
		return LIST;
	}

	@Override
	public String add() {
		return ADD;
	}

	public String save() {
		advertisingSpace.setSite(getWeb());
		advertisingSpace = advertisingSpaceSvc.save(advertisingSpace);
		log.info("添加广告位成功");
		addActionMessage("添加成功");
		logOperating("添加广告位", "添加广告位成功");
		return list();
	}

	@Override
	public String edit() {
		advertisingSpace = advertisingSpaceSvc.findById(id);
		return EDIT;
	}

	public String update() {
		advertisingSpaceSvc.updateDefault(advertisingSpace);
		log.info("修改广告位成功");
		addActionMessage("修改成功");
		logOperating("修改广告位", "修改广告位成功");
		return list();
	}

	public String delete() {
		vldBatch();
		advertisingSpaceSvc.deleteByIds(ids);
		log.info("删除广告位成功");
		addActionMessage("删除成功");
		logOperating("删除广告位", "删除广告位成功");
		return list();
	}

	public String disable() {
		advertisingSpace = advertisingSpaceSvc.findById(id);
		advertisingSpace.setEnabled(false);
		advertisingSpaceSvc.update(advertisingSpace);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		advertisingSpace = advertisingSpaceSvc.findById(id);
		advertisingSpace.setEnabled(true);
		advertisingSpaceSvc.update(advertisingSpace);
		addActionMessage("启用成功");
		return list();
	}

	public AdvertisingSpace getAdvertisingSpace() {
		return advertisingSpace;
	}

	public void setAdvertisingSpace(AdvertisingSpace advertisingSpace) {
		this.advertisingSpace = advertisingSpace;
	}
}
