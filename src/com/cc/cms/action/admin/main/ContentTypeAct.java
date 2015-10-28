
package com.cc.cms.action.admin.main;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.main.ContentType;
import com.cc.cms.service.main.IContentTypeSvc;
import com.cc.common.orm.Updater;

/** @author wangcheng */
@SuppressWarnings({ "rawtypes", "serial" })
@Scope("prototype")
@Controller("web.action.admin.contentTypeAct")
public class ContentTypeAct extends CmsAct {

	public static final Logger	log	= LoggerFactory.getLogger(ContentTypeAct.class);
	@Autowired
	private IContentTypeSvc		contentTypeSvc;
	private ContentType			contentType;

	@Override
	public String list() {
		list = contentTypeSvc.getAllTypes();
		return LIST;
	}

	public String save() {
		contentTypeSvc.save(contentType);
		contentTypeSvc.handleContentTypeChange();
		log.info("成功添加内容类型");
		return list();
	}

	@Override
	public String edit() {
		contentType = contentTypeSvc.getByIdFromCache(id);
		return EDIT;
	}

	public String update() {
		contentTypeSvc.updateByUpdater(Updater.create(contentType));
		log.info("成功修改内容类型");
		contentTypeSvc.handleContentTypeChange();
		return list();
	}

	public String enable() {
		contentType = contentTypeSvc.findById(id);
		contentType.setDisabled(false);
		contentTypeSvc.update(contentType);
		contentTypeSvc.handleContentTypeChange();
		addActionMessage("成功启用内容类型");
		return list();
	}

	public String disable() {
		contentType = contentTypeSvc.findById(id);
		contentType.setDisabled(true);
		contentTypeSvc.update(contentType);
		contentTypeSvc.handleContentTypeChange();
		addActionMessage("成功禁用内容类型");
		return list();
	}

	public String delete() {
		try {
			for (ContentType ct : contentTypeSvc.deleteById(ids)) {
				log.info("删除内容类型成功:{}", ct.getName());
			}
			addActionMessage("成功删除内容类型");
			contentTypeSvc.handleContentTypeChange();
		} catch (DataIntegrityViolationException e) {
			addActionError("记录被引用，不能删除!");
			log.info("删除内容类型，记录被引用");
		}
		return list();
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			addActionError("删除内容类型出现错误!");
			return true;
		}
		if (vldBatch()) {
			return true;
		}
		for (Integer id : ids) {
			if (vldExist(id)) {
				return true;
			}
		}
		return false;
	}

	private boolean vldExist(Integer id) {
		ContentType entity = contentTypeSvc.getByIdFromCache(id);
		if (entity == null) {
			addActionError("记录不存在：" + id);
			return true;
		}
		return false;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
}
