
package com.cc.cms.action.admin.assist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.assist.Sensitivity;
import com.cc.cms.service.main.ISensitivitySvc;
import com.cc.common.orm.hibernate3.OrderBy;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.sensitivityAct")
public class SensitivityAct extends CmsAct {

	@Autowired
	private ISensitivitySvc	sensitivitySvc;
	private Sensitivity		sensitivity;

	@Override
	public String list() {
		pagination = sensitivitySvc.findAll(pageNo, getCookieCount(), OrderBy.desc("id"));
		return LIST;
	}

	public String save() {
		sensitivitySvc.save(sensitivity);
		addActionMessage("添加成功！");
		return list();
	}

	public String update() {
		sensitivitySvc.updateDefault(sensitivity);
		addActionMessage("修改成功！");
		return list();
	}

	public String delete() {
		vldBatch();
		sensitivitySvc.deleteByIds(ids);
		addActionMessage("删除成功");
		return list();
	}

	public Sensitivity getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(Sensitivity sensitivity) {
		this.sensitivity = sensitivity;
	}

}
