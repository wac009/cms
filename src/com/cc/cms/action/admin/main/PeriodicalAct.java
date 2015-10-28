
package com.cc.cms.action.admin.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.Periodical;
import com.cc.cms.entity.main.PeriodicalCatalog;
import com.cc.cms.entity.main.Publication;
import com.cc.cms.service.main.IPeriodicalCatalogSvc;
import com.cc.cms.service.main.IPeriodicalSvc;
import com.cc.cms.service.main.IPublicationSvc;
import com.cc.common.orm.hibernate3.OrderBy;
import com.cc.common.ui.OptionsInt;
import com.cc.common.ui.OptionsString;
import com.cc.common.util.ComUtils;

/** @author wangcheng */

@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.periodicalAct")
public class PeriodicalAct extends CmsAct {

	private static final Logger		log				= LoggerFactory.getLogger(PublicationAct.class);
	private String					addTime;
	private Integer					publicationId;
	private Publication				publication;
	@Autowired
	private IPublicationSvc			publicationSvc;
	@Autowired
	private IPeriodicalSvc			periodicalSvc;
	private Periodical				periodical;
	private List<Periodical>		curPeriodicals;
	@Autowired
	private IPeriodicalCatalogSvc	periodicalCatalogSvc;
	private List<OptionsInt>		commCtgs		= new ArrayList<OptionsInt>();
	private Boolean					copyPrevCtg;
	private List<Integer>			catalogs;
	private String					attachmentPath;
	private String					attachmentName;
	private String					attachmentFilename;
	/** 查询条件 */
	private List<OptionsString>		yearList		= new ArrayList<OptionsString>();
	private String					year;
	private List<Publication>		publicationList	= new ArrayList<Publication>();
	private List<String>			property		= new ArrayList<String>();
	private List<Object>			value			= new ArrayList<Object>();

	private void initList() {
		property.add("publication.website.id");
		value.add(getWebId());
		if (publicationId != null) {
			publication = publicationSvc.findById(publicationId);
			property.add("publication.id");
			value.add(publicationId);
		}
		property.add("year");
		value.add(year);
		if (isSearch(value)) {
			pagination = periodicalSvc.findByProperty(pageNo, getCookieCount(), property, value, new OrderBy[] { OrderBy.desc("priority") });
		} else {
			pagination = periodicalSvc.findAll(pageNo, getCookieCount(), new OrderBy[] { OrderBy.desc("priority") });
		}
		initCurrent();
		initPublicationList();
		if (publicationList != null && publicationList.size() > 1) {
			publicationList.get(0).setName("== 全部刊物 ==");
		}
	}

	private void initCurrent() {
		property.clear();
		value.clear();
		property.add("publication.website.id");
		value.add(getWebId());
		property.add("current");
		value.add(true);
		if (publicationId != null) {
			property.add("publication.id");
			value.add(publicationId);
		}
		if (year != null) {
			property.add("year");
			value.add(year);
		}
		curPeriodicals = periodicalSvc.findByProperty(property, value);
	}

	private void initPublicationList() {
		property.clear();
		value.clear();
		property.add("website.id");
		value.add(getWebId());
		publicationList = publicationSvc.findByProperty(property, value, new OrderBy[] { OrderBy.asc("priority") });
		if (publicationList != null && publicationList.size() > 1) {
			Publication publication = new Publication();
			publication.setName("== 请选择刊物 ==");
			publicationList.add(0, publication);
		}
	}

	private void initYearList() {
		for (String year : periodicalSvc.getYearList()) {
			yearList.add(new OptionsString(year, year));
		}
		if (yearList != null && yearList.size() > 1) {
			yearList.add(0, new OptionsString("", "==全部年份=="));
		}
	}

	@Override
	public String list() {
		initList();
		initYearList();
		return LIST;
	}

	private void initCommCtgs() {
		property.add("website.id");
		value.add(getWebId());
		property.add("publication.id");
		value.add(publicationId);
		property.add("common");
		value.add(true);
		List<PeriodicalCatalog> list = periodicalCatalogSvc.findByProperty(property, value, new OrderBy[] { OrderBy.asc("priority") });
		for (int i = 0; i < list.size(); i++) {
			PeriodicalCatalog ctg = list.get(i);
			commCtgs.add(new OptionsInt(ctg.getId(), ctg.getName()));
		}
	}

	public String commCtgs() {
		initCommCtgs();
		getJsonRoot().put("commCtgs", commCtgs);
		return JSON;
	}

	private void initAdd() {
		if (publicationId == null) {
			initPublicationList();
		} else {
			publication = publicationSvc.findById(publicationId);
			initCommCtgs();
		}
		addUploadRule();
	}

	@Override
	public String add() {
		initAdd();
		return ADD;
	}

	public String save() {
		handleTime();
		periodical = periodicalSvc.savePeriodical(periodical, attachmentPath, attachmentName, attachmentFilename);
		publicationId = periodical.getPublication().getId();
		handleCtg();
		addActionMessage("成功添加期刊");
		return list();
	}

	private void handleCtg() {
		Set<Integer> ctgIds = new HashSet<Integer>();
		Set<PeriodicalCatalog> ctgs = new HashSet<PeriodicalCatalog>();
		if (copyPrevCtg) {
			Periodical perv = periodicalSvc.getPrev(periodical);
			for (PeriodicalCatalog ctg : perv.getCatalogs()) {
				ctgIds.add(ctg.getId());
			}
		}
		if (catalogs != null && catalogs.size() > 0) {
			for (Integer cid : catalogs) {
				ctgIds.add(cid);
			}
		}
		for (Integer cid : ctgIds) {
			PeriodicalCatalog ctg = periodicalCatalogSvc.findById(cid);
			periodicalCatalogSvc.evict(ctg);
			ctg.setId(null);
			ctg.setPeriodical(periodical);
			ctg.setCommon(false);
			// 不添加重复的目录
			boolean has = false;
			for (PeriodicalCatalog c : ctgs) {
				if (c.getName().equals(ctg.getName())) {
					has = true;
				}
			}
			if (!has) {
				ctgs.add(ctg);
			}
		}
		for (PeriodicalCatalog ctg : ctgs) {
			periodicalCatalogSvc.savePeriodicalCatalog(ctg);
		}
	}

	private void initEdit() {
		addUploadRule();
		periodical = periodicalSvc.findById(id);
		attachmentFilename = periodical.getAttachment().getFilename();
		attachmentName = periodical.getAttachment().getName();
		attachmentPath = periodical.getAttachment().getPath();
		setAddTime(ComUtils.formatDate(periodical.getAddTime(), 1));
	}

	@Override
	public String edit() {
		initEdit();
		return EDIT;
	}

	public String update() {
		handleTime();
		periodical = periodicalSvc.updatePeriodical(periodical, attachmentPath, attachmentName, attachmentFilename);
		periodicalSvc.refresh(periodical);
		log.info("修改期刊成功:{}", periodical.getTotalPeriod());
		addActionMessage("修改成功");
		return list();
	}

	public String delete() {
		try {
			for (Periodical periodical : periodicalSvc.deleteById(ids)) {
				log.info("删除期刊成功:{}", periodical.getTotalPeriod());
			}
			addActionMessage("成功删除");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除失败,记录被引用");
		}
		setId(null);
		return list();
	}

	public String disable() {
		periodical = periodicalSvc.findById(id);
		periodical.setDisabled(true);
		periodicalSvc.update(periodical);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		periodical = periodicalSvc.findById(id);
		periodical.setDisabled(false);
		periodicalSvc.update(periodical);
		addActionMessage("启用成功");
		return list();
	}

	public String lock() {
		periodical = periodicalSvc.findById(id);
		periodical.setLock(true);
		periodicalSvc.update(periodical);
		addActionMessage("锁定成功");
		return list();
	}

	public String unlock() {
		periodical = periodicalSvc.findById(id);
		periodical.setLock(false);
		periodicalSvc.update(periodical);
		addActionMessage("解锁成功");
		return list();
	}

	public boolean validateSave() {
		if (hasErrors()) {
			log.error("添加时出现action/field错误");
			initAdd();
			addActionError("添加时出现错误");
			return true;
		}
		if (periodical.getPublication() == null || periodical.getPublication().getId() == null) {
			initAdd();
			addActionError("请选择刊物");
			return true;
		}
		if (copyPrevCtg == null) {
			copyPrevCtg = false;
		}
		return false;
	}

	public String up() {
		periodicalSvc.up(id);
		addActionMessage("排序成功");
		return list();
	}

	public boolean validateUp() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
			addActionError("发生错误");
			return true;
		}
		if (!periodicalSvc.isUp(periodicalSvc.findById(id))) {
			addActionError("不能向上移动");
			initList();
			return true;
		}
		return false;
	}

	public String down() {
		periodicalSvc.down(id);
		addActionMessage("排序成功");
		return list();
	}

	public boolean validateDown() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
			addActionError("发生错误");
			return true;
		}
		if (!periodicalSvc.isDown(periodicalSvc.findById(id))) {
			addActionError("不能向下移动");
			initList();
			return true;
		}
		return false;
	}

	public boolean validateEdit() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
			addActionError("发生错误");
			return true;
		}
		if (vldExist(id)) {
			initList();
			return true;
		}
		return false;
	}

	public boolean validateUpdate() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initEdit();
			addActionError("发生错误");
			return true;
		}
		return false;
	}

	private boolean vldExist(Integer id) {
		Periodical entity = periodicalSvc.findById(id);
		if (entity == null) {
			addActionError("数据不存在：" + id);
			return true;
		}
		return false;
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			initList();
			return true;
		}
		if (vldBatch()) {
			log.error("删除ID不能为空");
			initList();
			return true;
		}
		for (Integer cid : ids) {
			if (vldExist(cid)) {
				initList();
				return true;
			}
		}
		return false;
	}

	private void handleTime() {
		// 如果没有输入添加时间，则取系统时间；
		if (ComUtils.nullOrBlank(addTime)) {
			periodical.setAddTime(ComUtils.now());
		} else {
			periodical.setAddTime(ComUtils.parseString2Datetime(addTime));
		}
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public Periodical getPeriodical() {
		return periodical;
	}

	public void setPeriodical(Periodical periodical) {
		this.periodical = periodical;
	}

	public List<Periodical> getCurPeriodicals() {
		return curPeriodicals;
	}

	public void setCurPeriodicals(List<Periodical> curPeriodicals) {
		this.curPeriodicals = curPeriodicals;
	}

	public Integer getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(Integer publicationId) {
		this.publicationId = publicationId;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public List<OptionsString> getYearList() {
		return yearList;
	}

	public void setYearList(List<OptionsString> yearList) {
		this.yearList = yearList;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public Boolean getCopyPrevCtg() {
		return copyPrevCtg;
	}

	public void setCopyPrevCtg(Boolean copyPrevCtg) {
		this.copyPrevCtg = copyPrevCtg;
	}

	public List<Integer> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<Integer> catalogs) {
		this.catalogs = catalogs;
	}

	public List<OptionsInt> getCommCtgs() {
		return commCtgs;
	}

	public void setCommCtgs(List<OptionsInt> commCtgs) {
		this.commCtgs = commCtgs;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentFilename() {
		return attachmentFilename;
	}

	public void setAttachmentFilename(String attachmentFilename) {
		this.attachmentFilename = attachmentFilename;
	}
}
