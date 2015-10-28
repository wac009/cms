
package com.cc.cms.service.main.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IContentTypeDao;
import com.cc.cms.entity.main.ContentType;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IContentTypeSvc;

/**
 * @author wangcheng
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class ContentTypeSvcImpl extends CmsSvcImpl<ContentType> implements IContentTypeSvc {

	@Autowired
	@Qualifier("contentType")
	private Cache	contentTypeCache;

	@Autowired
	public void setContentTypeDao(IContentTypeDao dao) {
		super.setDao(dao);
	}

	@Override
	public IContentTypeDao getDao() {
		return (IContentTypeDao) super.getDao();
	}

	@Override
	public void handleContentTypeChange() {
		loadAllContentTypeToCache();
	}

	@Override
	public ContentType getDef() {
		return getDao().getDef();
	}

	@Override
	public void loadAllContentTypeToCache() {
		contentTypeCache.removeAll();
		List<ContentType> contentTypes = getDao().findAll();
		if (!contentTypes.isEmpty()) {
			contentTypeCache.put(new Element("contentTypes", contentTypes));
		}
	}

	@Override
	public List<ContentType> getAllTypes() {
		// 从缓存中获取，若缓存空就查询数据库并加入缓存
		Element e = contentTypeCache.get("contentTypes");
		if (e != null) {
			return (List<ContentType>) e.getObjectValue();
		} else {
			List<ContentType> contentTypes = getDao().findAll();
			contentTypeCache.put(new Element("contentTypes", contentTypes));
			return contentTypes;
		}
	}

	@Override
	public List<ContentType> getEnableList() {
		List<ContentType> contentTypes = new ArrayList<ContentType>();
		for (ContentType contentType : getAllTypes()) {
			if (contentType != null && (contentType.getDisabled() != null && !contentType.getDisabled()) || contentType.getDisabled() == null) {
				contentTypes.add(contentType);
			}
		}
		return contentTypes;
	}

	@Override
	public ContentType getByIdFromCache(Integer id) {
		for (ContentType contentType : getAllTypes()) {
			if (id.equals(contentType.getId())) {
				return contentType;
			}
		}
		return null;
	}
}
