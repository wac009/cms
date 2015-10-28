
package com.cc.cms.action.admin.main;

import java.util.ArrayList;
import java.util.List;

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

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.periodicalCatalogAct")
public class PeriodicalCatalogAct extends CmsAct {
	private static final Logger		log				= LoggerFactory.getLogger(PeriodicalCatalogAct.class);
	@Autowired
	private IPeriodicalSvc			periodicalSvc;
	private Integer					periodicalId;
	private Periodical				periodical;
	private List<Periodical>		periodicalList	= new ArrayList<Periodical>();
	@Autowired
	private IPublicationSvc			publicationSvc;
	private Integer					publicationId;
	private Publication				publication;
	private List<Publication>		publicationList	= new ArrayList<Publication>();
	@Autowired
	private IPeriodicalCatalogSvc	periodicalCatalogSvc;
	private PeriodicalCatalog		periodicalCatalog;
	/** 查询条件 */
	private List<String>			property		= new ArrayList<String>();
	private List<Object>			value			= new ArrayList<Object>();

	private void initList() {
		initPublicationList();
		periodical = periodicalSvc.findById(periodicalId);
		publicationId = periodical.getPublication().getId();
		property.add("periodical");
		value.add(periodical);
		list = periodicalCatalogSvc.findByProperty(property, value, new OrderBy[] { OrderBy.asc("priority") });
		initPeriodicalList();
	}

	private void initCommList() {
		property.add("website.id");
		value.add(getWebId());
		if (publicationId != null) {
			publication = publicationSvc.findById(publicationId);
			property.add("publication.id");
			value.add(publicationId);
		}
		property.add("common");
		value.add(true);
		list = periodicalCatalogSvc.findByProperty(property, value, new OrderBy[] { OrderBy.asc("priority") });
		initPublicationList();
		if (publicationList.size() > 1) {
			publicationList.get(0).setName("== 全部刊物 ==");
		}
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

	private void initPeriodicalList() {
		property.clear();
		value.clear();
		property.add("publication.id");
		value.add(publicationId);
		property.add("lock");
		value.add(false);
		// property.add("website.id");
		// value.add(getWebId());
		periodicalList = periodicalSvc.findByProperty(property, value, new OrderBy[] { OrderBy.asc("priority") });
		if (periodicalList == null || periodicalList.size() == 0) {
			Periodical root = new Periodical();
			root.setName("== 期刊列表 ==");
			periodicalList.add(0, root);
		}
	}

	@Override
	public String list() {
		if (periodicalId == null) {
			return commList();
		}
		initList();
		return LIST;
	}

	public String commList() {
		initCommList();
		return LIST;
	}

	private void initAdd() {
		periodicalCatalog = new PeriodicalCatalog();
		if (periodicalId != null) {
			periodical = periodicalSvc.findById(periodicalId);
			periodicalCatalog.setPeriodical(periodical);
		}else if (publicationId != null) {
			publication = publicationSvc.findById(publicationId);
			periodicalCatalog.setPublication(publication);
		}else {
			initPublicationList();
		}
	}

	@Override
	public String add() {
		initAdd();
		return ADD;
	}

	public String save() {
		periodicalCatalogSvc.savePeriodicalCatalog(periodicalCatalog);
		addActionMessage("成功添加期刊目录");
		return list();
	}

	private void initEdit() {
		periodicalCatalog = periodicalCatalogSvc.findById(id);
		periodical = periodicalCatalog.getPeriodical();
		publication = periodicalCatalog.getPublication();
	}

	@Override
	public String edit() {
		initEdit();
		return EDIT;
	}

	public String update() {
		periodicalCatalog = periodicalCatalogSvc.updatePeriodicalCatalog(periodicalCatalog);
		periodicalCatalogSvc.refresh(periodicalCatalog);
		log.info("修改期刊目录成功:{}", periodicalCatalog.getName());
		addActionMessage("修改成功");
		return list();
	}

	public String delete() {
		try {
			for (PeriodicalCatalog periodicalCatalog : periodicalCatalogSvc.deleteById(ids)) {
				log.info("删除期刊目录成功:{}", periodicalCatalog.getName());
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
		periodicalCatalog = periodicalCatalogSvc.findById(id);
		periodicalCatalog.setDisabled(true);
		periodicalCatalogSvc.update(periodicalCatalog);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		periodicalCatalog = periodicalCatalogSvc.findById(id);
		periodicalCatalog.setDisabled(false);
		periodicalCatalogSvc.update(periodicalCatalog);
		addActionMessage("启用成功");
		return list();
	}

	public String up() {
		periodicalCatalogSvc.up(id);
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
		if (!periodicalCatalogSvc.isUp(periodicalCatalogSvc.findById(id))) {
			addActionError("不能向上移动");
			initList();
			return true;
		}
		return false;
	}

	public String down() {
		periodicalCatalogSvc.down(id);
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
		if (!periodicalCatalogSvc.isDown(periodicalCatalogSvc.findById(id))) {
			addActionError("不能向下移动");
			initList();
			return true;
		}
		return false;
	}

	public boolean validateSave() {
		if (hasErrors()) {
			log.error("添加时出现action/field错误");
			initAdd();
			addActionError("添加时出现错误");
			return true;
		}
		if (periodicalCatalog.getPeriodical() != null && periodicalCatalog.getPeriodical().getId() == null) {
			periodicalCatalog.setPeriodical(null);
		}
		if (periodicalCatalog.getPublication() != null && periodicalCatalog.getPublication().getId() == null) {
			periodicalCatalog.setPublication(null);
		}
		if (periodicalCatalog.getWebsite() != null && periodicalCatalog.getWebsite().getId() == null) {
			periodicalCatalog.setWebsite(getWeb());
		}
		if (periodicalCatalog.getPublication() == null && periodicalCatalog.getPeriodical() == null) {
			addActionError("请选择刊物");
			initAdd();
			return true;
		}
		return false;
	}

	public boolean validateEdit() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			initList();
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
			return true;
		}
		if (periodicalCatalog.getPeriodical() != null && periodicalCatalog.getPeriodical().getId() == null) {
			periodicalCatalog.setPeriodical(null);
		}
		if (periodicalCatalog.getPublication() != null && periodicalCatalog.getPublication().getId() == null) {
			periodicalCatalog.setPublication(null);
		}
		if (periodicalCatalog.getWebsite() != null && periodicalCatalog.getWebsite().getId() == null) {
			periodicalCatalog.setWebsite(getWeb());
		}
		if (periodicalCatalog.getPublication() == null && periodicalCatalog.getPeriodical() == null) {
			addActionError("请选择刊物");
			initEdit();
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

	private boolean vldExist(Integer id) {
		PeriodicalCatalog entity = periodicalCatalogSvc.findById(id);
		if (entity == null) {
			addActionError("数据不存在：" + id);
			return true;
		}
		return false;
	}

	public Integer getPeriodicalId() {
		return periodicalId;
	}

	public void setPeriodicalId(Integer periodicalId) {
		this.periodicalId = periodicalId;
	}

	public Periodical getPeriodical() {
		return periodical;
	}

	public void setPeriodical(Periodical periodical) {
		this.periodical = periodical;
	}

	public PeriodicalCatalog getPeriodicalCatalog() {
		return periodicalCatalog;
	}

	public void setPeriodicalCatalog(PeriodicalCatalog periodicalCatalog) {
		this.periodicalCatalog = periodicalCatalog;
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

	public List<Publication> getPublicationList() {
		return publicationList;
	}

	public void setPublicationList(List<Publication> publicationList) {
		this.publicationList = publicationList;
	}

	public List<Periodical> getPeriodicalList() {
		return periodicalList;
	}

	public void setPeriodicalList(List<Periodical> periodicalList) {
		this.periodicalList = periodicalList;
	}
}
