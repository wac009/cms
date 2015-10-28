package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Model;

public interface IModelDao extends ICmsDao<Model> {
	public List<Model> getList(boolean containDisabled);

	public Model getDefModel();

	public Integer getMaxPriority();
}