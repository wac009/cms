
package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.assist.Sensitivity;
import com.cc.cms.service.ICmsSvc;

public interface ISensitivitySvc extends ICmsSvc<Sensitivity> {

	public String replaceSensitivity(String s);

	public List<Sensitivity> getList(boolean cacheable);

	public Sensitivity findById(Integer id);

	@Override
	public Sensitivity save(Sensitivity bean);

	public void updateEnsitivity(Integer[] ids, String[] searchs, String[] replacements);

	public Sensitivity deleteById(Integer id);

	public Sensitivity[] deleteByIds(Integer[] ids);
}