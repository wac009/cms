
package com.cc.cms.action.admin.main;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.Model;
import com.cc.cms.service.main.IModelSvc;
import com.cc.common.orm.hibernate3.OrderBy;

/** @author wangcheng */

@SuppressWarnings("rawtypes")
@Scope("prototype")
@Controller("web.action.admin.modelAct")
public class ModelAct extends CmsAct {

	private static final long	serialVersionUID	= -2530157155907423658L;
	public static final Logger	log					= LoggerFactory.getLogger(ModelAct.class);

	@Autowired
	private IModelSvc			modelSvc;
	private Model				model;

	@Override
	public String list() {
		list = modelSvc.findAll(OrderBy.asc("priority"));
		return LIST;
	}

	public String save() {
		model = modelSvc.save(model);
		logOperating("添加模型", "添加模型成功:[" + model.getName() + "]");
		addActionMessage("添加成功");
		return list();
	}

	@Override
	public String edit() {
		model = modelSvc.findById(id);
		return EDIT;
	}

	public String update() {
		modelSvc.updateDefault(model);
		addActionMessage("修改成功");
		logOperating("修改模型", "修改模型成功:[" + model.getName() + "]");
		return list();
	}

	public String disable() {
		model = modelSvc.findById(id);
		model.setDisabled(true);
		modelSvc.updateDefault(model);
		addActionMessage("禁用成功");
		return list();
	}

	public String enable() {
		model = modelSvc.findById(id);
		model.setDisabled(false);
		modelSvc.updateDefault(model);
		addActionMessage("启用成功");
		return list();
	}

	public String delete() {
		try {
			for (Integer id : ids) {
				modelSvc.delete(id);
				log.info("删除成功");
			}
			addActionMessage("成功删除站点 ");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除失败,记录被引用");
		}
		return list();
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
