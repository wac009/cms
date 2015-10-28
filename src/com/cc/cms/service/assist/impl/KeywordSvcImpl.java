
package com.cc.cms.service.assist.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.assist.IKeywordDao;
import com.cc.cms.entity.assist.Keyword;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.assist.IKeywordSvc;

@Service
@Transactional
public class KeywordSvcImpl extends CmsSvcImpl<Keyword> implements IKeywordSvc {

	@Autowired
	public void setDao(IKeywordDao dao) {
		super.setDao(dao);
	}

	@Override
	public IKeywordDao getDao() {
		return (IKeywordDao) super.getDao();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Keyword> getListBySiteId(Integer siteId, boolean onlyEnabled, boolean cacheable) {
		List<Keyword> list = getDao().getListGlobal(onlyEnabled, cacheable);
		// TODO 关键字分站点？
		// list.addAll(getDao().getList(siteId, onlyEnabled, cacheable));
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public String attachKeyword(Integer siteId, String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		List<Keyword> list = getListBySiteId(siteId, true, true);
		int len = list.size();
		if (len <= 0) {
			return txt;
		}
		String[] searchArr = new String[len];
		String[] replacementArr = new String[len];
		int i = 0;
		for (Keyword k : list) {
			searchArr[i] = k.getName();
			replacementArr[i] = k.getUrl();
			i++;
		}
		try {
			Lexer lexer = new Lexer(txt);
			Node node;
			StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
			while ((node = lexer.nextNode()) != null) {
				if (node instanceof TextNode) {
					sb.append(StringUtils.replaceEach(node.toHtml(), searchArr, replacementArr));
				} else {
					sb.append(node.toHtml());
				}
			}
			return sb.toString();
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Keyword findById(Integer id) {
		Keyword entity = getDao().findById(id);
		return entity;
	}

	@Override
	public Keyword save(Keyword bean) {
		bean.init();
		getDao().save(bean);
		return bean;
	}

	@Override
	public void updateKeywords(Integer[] ids, String[] names, String[] urls, Boolean[] disableds) {
		Keyword keyword;
		for (int i = 0, len = ids.length; i < len; i++) {
			keyword = findById(ids[i]);
			keyword.setName(names[i]);
			keyword.setUrl(urls[i]);
			keyword.setDisabled(disableds[i]);
		}
	}

	@Override
	public Keyword deleteById(Integer id) {
		Keyword bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public Keyword[] deleteByIds(Integer[] ids) {
		Keyword[] beans = new Keyword[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
}