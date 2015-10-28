
package com.cc.cms.service.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.ITemplateDao;
import com.cc.cms.entity.main.Template;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.ITemplateSvc;
import com.cc.common.orm.Updater;
import com.cc.common.util.ComUtils;

@Service
@Transactional
public class TemplateSvcImpl extends CmsSvcImpl<Template> implements ITemplateSvc {

	@Autowired
	public void setTemplateDao(ITemplateDao dao) {
		super.setDao(dao);
	}

	@Override
	protected ITemplateDao getDao() {
		return (ITemplateDao) super.getDao();
	}

	@Override
	public Template saveTemplate(Template bean) {
		initTemplate(bean);
		return getDao().save(bean);
	}

	@Override
	public List<Template> findAll() {
		return super.findAll();
	}

	/**
	 * 初始化模板参数
	 */
	private void initTemplate(Template bean) {
		bean.setPriority(getDao().getMaxPriority() + 1);
		if (bean.getIsDisabled() == null) {
			bean.setIsDisabled(false);
		}
		bean.setCreateTime(ComUtils.now());
	}

	/**
	 * 排序
	 */
	@Override
	public boolean isUp(Template bean) {
		if (getDao().getPrev(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isDown(Template bean) {
		if (getDao().getNext(bean) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Template up(Integer id) {
		Template bean = findById(id);
		Integer oPriority = bean.getPriority();
		Template beanPre = getDao().getPrev(bean);
		bean.setPriority(beanPre.getPriority());
		beanPre.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanPre));
		return bean;
	}

	@Override
	public Template down(Integer id) {
		Template bean = findById(id);
		Integer oPriority = bean.getPriority();
		Template beanNext = getDao().getNext(bean);
		bean.setPriority(beanNext.getPriority());
		beanNext.setPriority(oPriority);
		updateByUpdater(Updater.create(bean));
		updateByUpdater(Updater.create(beanNext));
		return bean;
	}

	@Override
	public Template getPrev(Template bean) {
		return getDao().getPrev(bean);
	}

	@Override
	public Template getNext(Template bean) {
		return getDao().getNext(bean);
	}
}
