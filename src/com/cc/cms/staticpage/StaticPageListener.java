
package com.cc.cms.staticpage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.cms.entity.main.Content;
import com.cc.cms.service.listener.ContentListenerAbstract;
import com.cc.cms.staticpage.service.IStaticPageSvc;

import freemarker.template.TemplateException;

@Component
public class StaticPageListener extends ContentListenerAbstract {
	private static final Logger	log			= LoggerFactory.getLogger(StaticPageListener.class);
	/** 是否已审核 */
	private static final String	IS_CHECKED	= "isChecked";
	@Autowired
	private IStaticPageSvc		staticPageSvc;

	@Override
	public void afterSave(Content content) {
		if (content.isChecked()) {
			try {
				staticPageSvc.contentRelated(content);
			} catch (IOException e) {
				log.error("", e);
			} catch (TemplateException e) {
				log.error("", e);
			}
		}
	}

	@Override
	public Map<String, Object> preChange(Content content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IS_CHECKED, content.isChecked());
		return map;
	}

	@Override
	public void afterChange(Content content, Map<String, Object> map) {
		boolean pre = (Boolean) map.get(IS_CHECKED);
		boolean curr = content.isChecked();
		try {
			if (pre && !curr) {
				staticPageSvc.deleteContent(content);
				staticPageSvc.index(content.getSite());
			} else if (!pre && curr) {
				staticPageSvc.contentRelated(content);
			} else if (pre && curr) {
				staticPageSvc.contentRelated(content);
			}
		} catch (IOException e) {
			log.error("", e);
		} catch (TemplateException e) {
			log.error("", e);
		}
	}

	@Override
	public void afterDelete(Content content) {
		try {
			staticPageSvc.deleteContent(content);
			staticPageSvc.index(content.getSite());
		} catch (IOException e) {
			log.error("", e);
		} catch (TemplateException e) {
			log.error("", e);
		}
	}
}
