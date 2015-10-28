
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.ModelItem;

public interface IModelItemDao extends ICmsDao<ModelItem> {
	public List<ModelItem> getList(Integer modelId, boolean isChannel, boolean hasDisabled);

}