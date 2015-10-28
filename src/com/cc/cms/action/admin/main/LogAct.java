
package com.cc.cms.action.admin.main;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.Log;
import com.cc.common.orm.hibernate3.OrderBy;

/**
 * @author wangcheng
 */
@SuppressWarnings("rawtypes")
@Scope("prototype")
@Controller("web.action.admin.logAct")
public class LogAct extends CmsAct {

	private static final long	serialVersionUID	= 458147869306569504L;
	public static final Logger	log	= LoggerFactory.getLogger(LogAct.class);
	private Integer				days;
	/** 查询条件 */
	private List<String>		property			= new ArrayList<String>();
	private List<Object>		value				= new ArrayList<Object>();

	@Override
	public String list() {
		property.add("category");
		value.add(Log.OPERATING);
		pagination = logSvc.findByProperty(pageNo, getCookieCount(), property, value, OrderBy.desc("id"));
		return LIST;
	}

	public String listOperating() {
		property.add("category");
		value.add(Log.OPERATING);
		pagination = logSvc.findByProperty(pageNo, getCookieCount(), property, value, OrderBy.desc("id"));
		return "listOperating";
	}

	public String listSuccess() {
		property.add("category");
		value.add(Log.LOGIN_SUCCESS);
		pagination = logSvc.findByProperty(pageNo, getCookieCount(), property, value, OrderBy.desc("id"));
		return "listSuccess";
	}

	public String listFailure() {
		property.add("category");
		value.add(Log.LOGIN_FAILURE);
		pagination = logSvc.findByProperty(pageNo, getCookieCount(), property, value, OrderBy.desc("id"));
		return "listFailure";
	}
	
	public String delete() {
		try {
			for (Integer id : ids) {
				logSvc.deleteById(id);
			}
			addActionMessage("成功删除 ");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除失败,记录被引用");
		}
		return list();
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public List<String> getProperty() {
		return property;
	}

	public void setProperty(List<String> property) {
		this.property = property;
	}

	public List<Object> getValue() {
		return value;
	}

	public void setValue(List<Object> value) {
		this.value = value;
	}
}
