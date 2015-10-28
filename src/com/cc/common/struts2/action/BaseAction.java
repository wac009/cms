
package com.cc.common.struts2.action;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.struts2.*;
import org.slf4j.*;

import com.cc.common.page.*;
import com.opensymphony.xwork2.*;

@SuppressWarnings({ "serial", "rawtypes" })
public class BaseAction extends ActionSupport implements Preparable {

	private static final Logger	log						= LoggerFactory.getLogger(BaseAction.class);
	protected Integer[]			ids;
	protected Integer			id;
	protected Pagination		pagination;
	protected List				list;
	protected int				pageNo					= 1;
	public static final String	LIST					= "list";
	public static final String	EDIT					= "edit";
	public static final String	ADD						= "add";
	public static final String	SELECT					= "select";
	public static final String	QUERY					= "query";
	public static final String	LEFT					= "left";
	public static final String	RIGHT					= "right";
	public static final String	INDEX					= "index";
	public static final String	MAIN					= "main";
	public static final String	JSON					= "json";
	public static final String	DEFRESULT				= "defResult";
	public static final String	DEFRESULT_FREEMARKER	= "defResult_freemarker";

	@Override
	public void prepare() throws Exception {}

	public String list() throws Exception {
		return LIST;
	}

	public String edit() throws Exception {
		return EDIT;
	}

	public String add() throws Exception {
		return ADD;
	}

	public String select() throws Exception {
		return SELECT;
	}

	public String query() throws Exception {
		return QUERY;
	}

	public String left() throws Exception {
		return LEFT;
	}

	public String right() throws Exception {
		return RIGHT;
	}

	public String index() throws Exception {
		return INDEX;
	}

	public String main() throws Exception {
		return MAIN;
	}

	public String json() throws Exception {
		return JSON;
	}

	@Override
	public void validate() {}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	/** 验证批量操作 */
	protected boolean vldBatch() {
		if (id == null && (ids == null || ids.length <= 0)) {
			addActionError("ID不能为空");
			return true;
		} else {
			if (id != null && (ids == null || ids.length <= 0)) {
				ids = new Integer[] { id };
			}
		}
		return false;
	}

	/** 绕过Template,直接输出内容的简便函数. */
	protected String render(String text, String contentType) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/** 直接输出字符串. */
	protected String renderText(String text) {
		return render(text, "text/plain;charset=UTF-8");
	}

	/** 直接输出字符串GBK编码. */
	protected String renderHtmlGBK(String html) {
		return render(html, "text/html;charset=GBK");
	}

	/** 直接输出XML. */
	protected String renderXML(String xml) {
		return render(xml, "text/xml;charset=UTF-8");
	}
}
