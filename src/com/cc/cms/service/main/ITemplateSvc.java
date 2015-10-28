
package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.main.Template;
import com.cc.cms.service.ICmsSvc;

public interface ITemplateSvc extends ICmsSvc<Template> {

	@Override
	public List<Template> findAll();

	/**
	 * 排序 检测是否可移动
	 * 
	 * @return 排序后的对象
	 */
	public boolean isUp(Template bean);

	public boolean isDown(Template bean);

	/**
	 * 排序
	 * 
	 * @return 排序后的对象
	 */
	public Template up(Integer id);

	public Template down(Integer id);

	public Template getPrev(Template bean);

	public Template getNext(Template bean);

	public Template saveTemplate(Template bean);
}
