
package com.cc.cms.service.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IModelItemDao;
import com.cc.cms.entity.main.ModelItem;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IModelItemSvc;
import com.cc.cms.service.main.IModelSvc;

@Service
@Transactional
public class ModelItemSvcImpl extends CmsSvcImpl<ModelItem> implements IModelItemSvc {

	@Autowired
	public void setDao(IModelItemDao dao) {
		super.setDao(dao);
	}

	@Override
	public IModelItemDao getDao() {
		return (IModelItemDao) super.getDao();
	}

	@Autowired
	private IModelSvc	modelSvc;

	@Override
	@Transactional(readOnly = true)
	public List<ModelItem> getList(Integer modelId, boolean isChannel, boolean hasDisabled) {
		return getDao().getList(modelId, isChannel, hasDisabled);
	}

	@Override
	public ModelItem save(ModelItem bean, Integer modelId) {
		bean.setModel(modelSvc.findById(modelId));
		return save(bean);
	}

	@Override
	public void saveList(List<ModelItem> list) {
		for (ModelItem item : list) {
			save(item);
		}
	}

}