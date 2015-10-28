
package com.cc.cms.action.admin.assist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.assist.Advertising;
import com.cc.cms.entity.assist.AdvertisingSpace;
import com.cc.cms.service.assist.IAdvertisingSpaceSvc;
import com.cc.cms.service.assist.IAdvertisingSvc;
import com.cc.common.orm.hibernate3.OrderBy;
import com.cc.common.util.ComUtils;
import com.cc.common.web.RequestUtils;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.advertisingAct")
public class AdvertisingAct extends CmsAct {

	private static final Logger		log			= LoggerFactory.getLogger(AdvertisingAct.class);
	@Autowired
	private IAdvertisingSvc			advertisingSvc;
	@Autowired
	private IAdvertisingSpaceSvc	advertisingSpaceSvc;
	private List<AdvertisingSpace>	spaces;
	private Advertising				advertising;
	private AdvertisingSpace		advertisingSpace;
	private Boolean					queryEnabled;
	private Integer					queryAdspaceId;
	private String					startTime;
	private String					endTime;
	private Map<String, String>		attr;
	/** 查询条件 */
	private List<String>			property	= new ArrayList<String>();
	private List<Object>			value		= new ArrayList<Object>();

	@Override
	public String list() {
		property.add("site.id");
		value.add(getWebId());
		if (queryEnabled != null) {
			property.add("enabled");
			value.add(queryEnabled);
		}
		if (queryAdspaceId != null) {
			property.add("adspace.id");
			value.add(queryAdspaceId);
		}
		pagination = advertisingSvc.findByProperty(pageNo, getCookieCount(), property, value, OrderBy.desc("id"));
		return LIST;
	}

	@Override
	public String add() {
		initSpace();
		addUploadRule();
		return ADD;
	}

	public String save() {
		handleTime();
		advertising.setSite(getWeb());
		advertising.setUser(getUser());
		advertising.setAddTime(ComUtils.now());
		Map<String, String> attr = RequestUtils.getRequestMap(contextPvd.getRequest(), "attr_");
		// 去除为空串的属性
		Set<String> toRemove = new HashSet<String>();
		for (Entry<String, String> entry : attr.entrySet()) {
			if (StringUtils.isBlank(entry.getValue())) {
				toRemove.add(entry.getKey());
			}
		}
		for (String key : toRemove) {
			attr.remove(key);
		}
		advertising = advertisingSvc.save(advertising, advertising.getAdspace().getId(), attr);
		log.info("添加广告成功");
		return list();
	}

	@Override
	public String edit() {
		advertising = advertisingSvc.findById(id);
		setStartTime(ComUtils.formatDate(advertising.getStartTime(), 1));
		setEndTime(ComUtils.formatDate(advertising.getEndTime(), 1));
		initSpace();
		attr = advertising.getAttr();
		addUploadRule();
		return EDIT;
	}

	public String update() {
		handleTime();
		Map<String, String> attr = RequestUtils.getRequestMap(contextPvd.getRequest(), "attr_");
		// 去除为空串的属性
		Set<String> toRemove = new HashSet<String>();
		for (Entry<String, String> entry : attr.entrySet()) {
			if (StringUtils.isBlank(entry.getValue())) {
				toRemove.add(entry.getKey());
			}
		}
		for (String key : toRemove) {
			attr.remove(key);
		}
		advertising = advertisingSvc.update(advertising, advertising.getAdspace().getId(), attr);
		log.info("修改广告成功");
		return list();
	}

	public String delete() {
		vldBatch();
		advertisingSvc.deleteByIds(ids);
		return list();
	}

	public String disable() {
		advertising = advertisingSvc.findById(id);
		advertising.setEnabled(false);
		advertisingSvc.update(advertising);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		advertising = advertisingSvc.findById(id);
		advertising.setEnabled(true);
		advertisingSvc.update(advertising);
		addActionMessage("启用成功");
		return list();
	}

	private void handleTime() {
		// 如果没有输入发布时间，则取系统时间；
		if (!ComUtils.nullOrBlank(startTime)) {
			advertising.setStartTime(ComUtils.parseString2Datetime(startTime));
		}
		if (!ComUtils.nullOrBlank(endTime)) {
			advertising.setEndTime(ComUtils.parseString2Datetime(endTime));
		}
	}

	private void initSpace() {
		spaces = advertisingSpaceSvc.findByProperty("site.id", getWebId());
	}

	public Advertising getAdvertising() {
		return advertising;
	}

	public void setAdvertising(Advertising advertising) {
		this.advertising = advertising;
	}

	public AdvertisingSpace getAdvertisingSpace() {
		return advertisingSpace;
	}

	public void setAdvertisingSpace(AdvertisingSpace advertisingSpace) {
		this.advertisingSpace = advertisingSpace;
	}

	public Boolean getQueryEnabled() {
		return queryEnabled;
	}

	public void setQueryEnabled(Boolean queryEnabled) {
		this.queryEnabled = queryEnabled;
	}

	public Integer getQueryAdspaceId() {
		return queryAdspaceId;
	}

	public void setQueryAdspaceId(Integer queryAdspaceId) {
		this.queryAdspaceId = queryAdspaceId;
	}

	public List<AdvertisingSpace> getSpaces() {
		return spaces;
	}

	public void setSpaces(List<AdvertisingSpace> spaces) {
		this.spaces = spaces;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Map<String, String> getAttr() {
		return attr;
	}

	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}
}
