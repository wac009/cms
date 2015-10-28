/** @author wangcheng */

package com.cc.cms.dao.assist;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.Sensitivity;

public interface ISensitivityDao extends ICmsDao<Sensitivity> {

	public List<Sensitivity> getList(boolean cacheable);

	public Sensitivity findById(Integer id);

	@Override
	public Sensitivity save(Sensitivity bean);

	public Sensitivity deleteById(Integer id);
}