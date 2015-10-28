package com.cc.common.struts2.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "webeditor", tldTagClass = "org.apache.struts2.views.jsp.ui.TextareaTag", description = "Render WebEditor tag.")
public class WebEditor extends UIBean {
	final public static String	TEMPLATE	= "webeditor";
	protected String			cols;
	protected String			readonly;
	protected String			rows;
	protected String			wrap;

	public WebEditor(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
	@Override
	public void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (readonly != null) {
			addParameter("readonly", findValue(readonly, Boolean.class));
		}
		if (cols != null) {
			addParameter("cols", findString(cols));
		}
		if (rows != null) {
			addParameter("rows", findString(rows));
		}
		if (wrap != null) {
			addParameter("wrap", findString(wrap));
		}
	}
	@StrutsTagAttribute(description = "HTML cols attribute", type = "Integer")
	public void setCols(String cols) {
		this.cols = cols;
	}
	@StrutsTagAttribute(description = "Whether the textarea is readonly", type = "Boolean", defaultValue = "false")
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	@StrutsTagAttribute(description = "HTML rows attribute", type = "Integer")
	public void setRows(String rows) {
		this.rows = rows;
	}
	@StrutsTagAttribute(description = "HTML wrap attribute")
	public void setWrap(String wrap) {
		this.wrap = wrap;
	}
}
