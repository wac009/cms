
package com.cc.cms.service.main.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.ISensitivityDao;
import com.cc.cms.entity.assist.Sensitivity;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.ISensitivitySvc;

@Service
@Transactional
public class SensitivitySvcImpl extends CmsSvcImpl<Sensitivity> implements ISensitivitySvc {

	@Autowired
	public void setDao(ISensitivityDao dao) {
		super.setDao(dao);
	}

	@Override
	public ISensitivityDao getDao() {
		return (ISensitivityDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public String replaceSensitivity(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		List<Sensitivity> list = getList(true);
		for (Sensitivity sen : list) {
			s = StringUtils.replace(s, sen.getSearch(), sen.getReplacement());
		}
		return s;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sensitivity> getList(boolean cacheable) {
		return getDao().getList(cacheable);
	}

	@Override
	@Transactional(readOnly = true)
	public Sensitivity findById(Integer id) {
		Sensitivity entity = getDao().findById(id);
		return entity;
	}

	@Override
	public Sensitivity save(Sensitivity bean) {
		getDao().save(bean);
		return bean;
	}

	@Override
	public void updateEnsitivity(Integer[] ids, String[] searchs, String[] replacements) {
		Sensitivity ensitivity;
		for (int i = 0, len = ids.length; i < len; i++) {
			ensitivity = findById(ids[i]);
			ensitivity.setSearch(searchs[i]);
			ensitivity.setReplacement(replacements[i]);
		}
	}

	@Override
	public Sensitivity deleteById(Integer id) {
		Sensitivity bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public Sensitivity[] deleteByIds(Integer[] ids) {
		Sensitivity[] beans = new Sensitivity[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
}