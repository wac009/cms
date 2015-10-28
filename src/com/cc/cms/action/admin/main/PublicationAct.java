
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
import com.cc.cms.entity.main.Publication;
import com.cc.cms.entity.main.PublicationType;
import com.cc.cms.entity.main.Site;
import com.cc.cms.service.main.IPublicationSvc;
import com.cc.cms.service.main.IPublicationTypeSvc;
import com.cc.common.orm.hibernate3.OrderBy;
import com.cc.common.util.ComUtils;
import com.cc.common.util.SelectTreeUtils;

/** @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.publicationAct")
public class PublicationAct extends CmsAct {

	private static final long		serialVersionUID	= 297692819874912661L;
	private static final Logger		log					= LoggerFactory.getLogger(PublicationAct.class);
	private List<Site>				websiteList;
	private List<PublicationType>	typeList;
	private Publication				publication;
	private String					createTime;
	private String					addTime;
	@Autowired
	private IPublicationSvc			publicationSvc;
	@Autowired
	private IPublicationTypeSvc		publicationTypeSvc;
	/** 查询条件 */
	private Integer					typeId;
	private List<String>			property			= new ArrayList<String>();
	private List<Object>			value				= new ArrayList<Object>();

	@Override
	public String left() {
		setLeftMenuType("list");
		return super.left();
	}

	private void initList() {
		if (typeId != null) {
			property.add("type.id");
			value.add(typeId);
		}
		property.add("website.id");
		value.add(getWebId());
		list = publicationSvc.findByProperty(property, value, new OrderBy[] { OrderBy.asc("priority") });
		initTypeList();
		if (typeList != null && typeList.size() > 1) {
			typeList.get(0).setName("==全部类型==");
		}
	}

	@Override
	public String list() {
		initList();
		return LIST;
	}

	private void initTypeList() {
		typeList = publicationTypeSvc.findByProperty("website.id", getWebId());
		if (typeList != null && typeList.size() > 1) {
			PublicationType type = new PublicationType();
			type.setName("== 请选择类型 ==");
			typeList.add(0, type);
		}
	}

	private void initValues() {
		websiteList = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(websiteSvc.getAllWebsites()));
		initTypeList();
	}

	private void initAdd() {
		addUploadRule();
		initValues();
	}

	@Override
	public String add() {
		initAdd();
		return ADD;
	}

	public String save() {
		handleTime();
		handleType();
		publicationSvc.savePublication(publication);
		addActionMessage("成功添加出版物");
		return redirect("publication_list.jspa");
	}

	private void initEdit() {
		addUploadRule();
		initValues();
		publication = publicationSvc.findById(id);
		setCreateTime(ComUtils.formatDate(publication.getCreateTime(), 1));
		setAddTime(ComUtils.formatDate(publication.getAddTime(), 1));
	}

	@Override
	public String edit() {
		initEdit();
		return EDIT;
	}

	public String update() {
		handleTime();
		handleType();
		publicationSvc.updateDefault(publication);
		log.info("修改成功:{}", publication.getName());
		addActionMessage("修改成功");
		return redirect("publication_list.jspa");
	}

	public String delete() {
		try {
			for (Publication publication : publicationSvc.deleteById(ids)) {
				log.info("删除成功:{}", publication.getName());
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
		publication = publicationSvc.findById(id);
		publication.setDisabled(true);
		publicationSvc.update(publication);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		publication = publicationSvc.findById(id);
		publication.setDisabled(false);
		publicationSvc.update(publication);
		addActionMessage("启用成功");
		return list();
	}

	public boolean validateSave() {
		if (hasErrors()) {
			log.error("添加时出现action/field错误");
			initAdd();
			addActionError("添加时出现错误");
			return true;
		}
		return false;
	}

	public String up() {
		publicationSvc.up(id);
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
		if (!publicationSvc.isUp(publicationSvc.findById(id))) {
			addActionError("不能向上移动");
			initList();
			return true;
		}
		return false;
	}

	public String down() {
		publicationSvc.down(id);
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
		if (!publicationSvc.isDown(publicationSvc.findById(id))) {
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
			initValues();
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
		Publication entity = publicationSvc.findById(id);
		if (entity == null) {
			addActionError("数据不存在：" + id);
			return true;
		}
		return false;
	}

	private void handleType() {
		if (publication.getType() != null && publication.getType().getId() == null) {
			publication.setType(null);
		}
	}

	private void handleTime() {
		// 如果没有输入创建时间，则取系统时间；
		if (ComUtils.nullOrBlank(createTime)) {
			publication.setCreateTime(ComUtils.now());
		} else {
			publication.setCreateTime(ComUtils.parseString2Datetime(createTime));
		}
		// 如果没有输入添加时间，则取系统时间；
		if (ComUtils.nullOrBlank(addTime)) {
			publication.setAddTime(ComUtils.now());
		} else {
			publication.setAddTime(ComUtils.parseString2Datetime(addTime));
		}
	}

	public List<Site> getWebsiteList() {
		return websiteList;
	}

	public void setWebsiteList(List<Site> websiteList) {
		this.websiteList = websiteList;
	}

	public List<PublicationType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<PublicationType> typeList) {
		this.typeList = typeList;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
}
