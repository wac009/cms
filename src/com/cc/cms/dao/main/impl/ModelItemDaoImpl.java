
package com.cc.cms.dao.main.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.IModelItemDao;
import com.cc.cms.entity.main.ModelItem;

@Repository
public class ModelItemDaoImpl extends CmsDaoImpl<ModelItem> implements IModelItemDao {
	@Override
	@SuppressWarnings("unchecked")
	public List<ModelItem> getList(Integer modelId, boolean isChannel, boolean hasDisabled) {
		StringBuilder sb = new StringBuilder("from ModelItem bean where bean.model.id=? and bean.channel=?");
		if (!hasDisabled) {
			sb.append(" and bean.display=true");
		}
		sb.append(" order by bean.priority asc,bean.id asc");
		return find(sb.toString(), modelId, isChannel);
	}
}