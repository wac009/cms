
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
import com.cc.cms.entity.main.PublicationType;
import com.cc.cms.service.main.IPublicationTypeSvc;
import com.cc.common.orm.Updater;
import com.cc.common.orm.hibernate3.OrderBy;

/** @author wangcheng */
@SuppressWarnings("rawtypes")
@Scope("prototype")
@Controller("web.action.admin.publicationTypeAct")
public class PublicationTypeAct extends CmsAct {

	private static final long	serialVersionUID	= 8348373916378080622L;
	private static final Logger	log					= LoggerFactory.getLogger(PublicationTypeAct.class);
	@Autowired
	private IPublicationTypeSvc	publicationTypeSvc;
	private PublicationType		type;
	/** 查询条件 */
	private List<String>		property			= new ArrayList<String>();
	private List<Object>		value				= new ArrayList<Object>();

	private void initList() {
		property.add("website.id");
		value.add(getWebId());
		list = publicationTypeSvc.findByProperty(property, value, new OrderBy[] { OrderBy.asc("priority") });
	}

	@Override
	public String list() {
		initList();
		return LIST;
	}

	@Override
	public String add() {
		return ADD;
	}

	public String save() {
		publicationTypeSvc.savePublicationType(type);
		addActionMessage("成功添加刊物类型");
		return list();
	}

	private void initEdit() {
		type = publicationTypeSvc.findById(id);
	}

	@Override
	public String edit() {
		initEdit();
		return EDIT;
	}

	public String update() {
		type = (PublicationType) publicationTypeSvc.updateByUpdater(Updater.create(type));
		log.info("修改刊物类型成功:{}", type.getName());
		addActionMessage("修改成功");
		return list();
	}

	public String delete() {
		try {
			for (PublicationType type : publicationTypeSvc.deleteById(ids)) {
				log.info("删除刊物类型成功:{}", type.getName());
			}
			addActionMessage("成功删除");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除失败,记录被引用");
		}
		return list();
	}

	public String disable() {
		type = publicationTypeSvc.findById(id);
		type.setDisabled(true);
		publicationTypeSvc.update(type);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		type = publicationTypeSvc.findById(id);
		type.setDisabled(false);
		publicationTypeSvc.update(type);
		addActionMessage("启用成功");
		return list();
	}

	public boolean validateSave() {
		if (hasErrors()) {
			log.error("添加时出现action/field错误");
			addActionError("添加时出现错误");
			return true;
		}
		return false;
	}

	public String up() {
		publicationTypeSvc.up(id);
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
		if (!publicationTypeSvc.isUp(publicationTypeSvc.findById(id))) {
			addActionError("不能向上移动");
			initList();
			return true;
		}
		return false;
	}

	public String down() {
		publicationTypeSvc.down(id);
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
		if (!publicationTypeSvc.isDown(publicationTypeSvc.findById(id))) {
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
		PublicationType entity = publicationTypeSvc.findById(id);
		if (entity == null) {
			addActionError("数据不存在：" + id);
			return true;
		}
		return false;
	}

	public PublicationType getType() {
		return type;
	}

	public void setType(PublicationType type) {
		this.type = type;
	}
}
