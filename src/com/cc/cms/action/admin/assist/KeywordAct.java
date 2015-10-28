
package com.cc.cms.action.admin.assist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cc.cms.action.CmsAct;
import com.cc.cms.entity.assist.Keyword;
import com.cc.cms.service.assist.IKeywordSvc;
import com.cc.common.orm.hibernate3.OrderBy;

/** @author wangcheng */
@SuppressWarnings("rawtypes")
@Scope("prototype")
@Controller("web.action.admin.keywordAct")
public class KeywordAct extends CmsAct {

	private static final long	serialVersionUID	= -3955374688294687930L;
	@Autowired
	private IKeywordSvc			keywordSvc;
	private Keyword				keyword;

	@Override
	public String list() {
		pagination = keywordSvc.findAll(pageNo, getCookieCount(), OrderBy.desc("id"));
		return LIST;
	}

	public String save() {
		keyword.setSite(getWeb());
		keywordSvc.save(keyword);
		return list();
	}

	@Override
	public String edit() {
		keyword = keywordSvc.findById(id);
		return EDIT;
	}

	public String update() {
		keywordSvc.updateDefault(keyword);
		return list();
	}

	public String delete() {
		vldBatch();
		keywordSvc.deleteByIds(ids);
		return list();
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
}
