
package com.cc.cms.service.assist.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IAdvertisingDao;
import com.cc.cms.entity.assist.Advertising;
import com.cc.cms.entity.assist.base.BaseAdvertising;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IAdvertisingSpaceSvc;
import com.cc.cms.service.assist.IAdvertisingSvc;
import com.cc.common.orm.Updater;
import com.cc.common.page.Pagination;

@Service
@Transactional
public class AdvertisingSvcImpl extends CmsSvcImpl<Advertising> implements IAdvertisingSvc {

	@Autowired
	private IAdvertisingSpaceSvc	cmsAdvertisingSpaceMng;

	@Autowired
	public void setDao(IAdvertisingDao dao) {
		super.setDao(dao);
	}

	@Override
	public IAdvertisingDao getDao() {
		return (IAdvertisingDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer adspaceId, Boolean enabled, int pageNo, int pageSize) {
		Pagination page = getDao() .getPage(siteId, adspaceId, enabled, pageNo, pageSize);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Advertising> getList(Integer adspaceId, Boolean enabled) {
		return getDao() .getList(adspaceId, enabled);
	}

	@Override
	@Transactional(readOnly = true)
	public Advertising findById(Integer id) {
		Advertising entity = getDao() .findById(id);
		return entity;
	}

	@Override
	public Advertising save(Advertising bean, Integer adspaceId, Map<String, String> attr) {
		bean.setAdspace(cmsAdvertisingSpaceMng.findById(adspaceId));
		bean.setAttr(attr);
		bean.init();
		getDao() .save(bean);
		return bean;
	}

	@Override
	public Advertising update(Advertising bean, Integer adspaceId, Map<String, String> attr) {
		Updater<Advertising> updater = new Updater<Advertising>(bean);
		updater.include(BaseAdvertising.PROP_CODE);
		bean = (Advertising) getDao() .updateByUpdater(updater);
		bean.setAdspace(cmsAdvertisingSpaceMng.findById(adspaceId));
		bean.getAttr().clear();
		bean.getAttr().putAll(attr);
		return bean;
	}

	@Override
	public Advertising deleteById(Integer id) {
		Advertising bean = getDao() .deleteById(id);
		return bean;
	}

	@Override
	public Advertising[] deleteByIds(Integer[] ids) {
		Advertising[] beans = new Advertising[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
	public void display(Integer id) {
		Advertising ad = findById(id);
		if (ad != null) {
			ad.setDisplayCount(ad.getDisplayCount() + 1);
		}
	}

	@Override
	public void click(Integer id) {
		Advertising ad = findById(id);
		if (ad != null) {
			ad.setClickCount(ad.getClickCount() + 1);
		}
	}

}