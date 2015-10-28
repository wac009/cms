
package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.main.ModelItem;
import com.cc.cms.service.ICmsSvc;

public interface IModelItemSvc extends ICmsSvc<ModelItem> {
	public List<ModelItem> getList(Integer modelId, boolean isChannel, boolean hasDisabled);

	public ModelItem save(ModelItem bean, Integer modelId);

	public void saveList(List<ModelItem> list);

}