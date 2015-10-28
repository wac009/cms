
package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.main.Model;
import com.cc.cms.service.ICmsSvc;

/** 模型Manager接口
 * 
 * @author wangcheng */
public interface IModelSvc extends ICmsSvc<Model> {
	/** 获得模型列表
	 * 
	 * @param containDisabled 是否所有模型（即包含禁用模型）
	 * @return */
	public List<Model> getList(boolean containDisabled);

	/** 获得默认模型
	 * 
	 * @return */
	public Model getDefModel();

	@Override
	public Model save(Model bean);

}