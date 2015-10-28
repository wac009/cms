
package com.cc.cms.action.admin.main;

import java.util.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.contentTagAct")
public class ContentTagAct extends CmsAct {

	public static final Logger	log			= LoggerFactory.getLogger(ContentTagAct.class);
	@Autowired
	private IContentTagSvc		tagSvc;
	private ContentTag			tag;
	private String				name;
	/** 查询条件 */
	private List<String>		property	= new ArrayList<String>();
	private List<Object>		value		= new ArrayList<Object>();

	@Override
	public String list() {
		property.add("site.id");
		value.add(getWebId());
		pagination = tagSvc.findByProperty(pageNo, getCookieCount(), property, value);
		return LIST;
	}

	public String save() {
		tag = new ContentTag();
		tag.setName(name);
		tag.setSite(getWeb());
		tagSvc.save(tag);
		return list();
	}

	@SuppressWarnings("unchecked")
	public String rename() {
		tag = tagSvc.findById(id);
		tag.setName(name);
		tagSvc.updateDefault(tag);
		log.info("重命名成功");
		getJsonRoot().put("success", true);
		return JSON;
	}

	public String delete() {
		vldBatch();
		tagSvc.deleteByIds(ids);
		return list();
	}

	public ContentTag getTag() {
		return tag;
	}

	public void setTag(ContentTag tag) {
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
