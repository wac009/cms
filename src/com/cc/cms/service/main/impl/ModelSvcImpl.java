
package com.cc.cms.service.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IModelDao;
import com.cc.cms.entity.main.Model;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IModelSvc;

@Service
@Transactional
public class ModelSvcImpl extends CmsSvcImpl<Model> implements IModelSvc {

	@Autowired
	public void setDao(IModelDao dao) {
		super.setDao(dao);
	}

	@Override
	public IModelDao getDao() {
		return (IModelDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Model> getList(boolean containDisabled) {
		return getDao().getList(containDisabled);
	}

	@Override
	public Model getDefModel() {
		return getDao().getDefModel();
	}
	
	@Override
	public Model save(Model entity) {
		entity.setPriority(getDao().getMaxPriority()+1);
		return super.save(entity);
	}
}