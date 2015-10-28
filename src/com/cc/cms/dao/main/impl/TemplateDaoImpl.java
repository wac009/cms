
package com.cc.cms.dao.main.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.main.ITemplateDao;
import com.cc.cms.entity.main.Template;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class TemplateDaoImpl extends CmsDaoImpl<Template> implements ITemplateDao {

	@Override
	public List<Template> findAll() {
		String hql = "from Template t where t.isDisabled=false order by t.priority asc";
		return find(hql);
	}

	@Override
	public Template getPrev(Template bean) {
		String hql = "from Template t where t.priority<? order by t.priority desc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Template) list.get(0);
	}

	@Override
	public Template getNext(Template bean) {
		String hql = "from Template t where t.priority>? order by t.priority asc";
		List list = createQuery(hql, bean.getPriority()).setFirstResult(0).setMaxResults(1).list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Template) list.get(0);
	}

	@Override
	public Integer getMaxPriority() {
		Integer c = (Integer) createQuery("select max(t.priority) from Template t ").uniqueResult();
		if (c == null)
			c = 0;
		return c;
	}
}
