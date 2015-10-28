
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Template;

public interface ITemplateDao extends ICmsDao<Template> {

	@Override
	public List<Template> findAll();

	public Template getPrev(Template bean);

	public Template getNext(Template bean);

	public Integer getMaxPriority();
}
