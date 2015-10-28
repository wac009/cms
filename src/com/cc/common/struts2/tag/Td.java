package com.cc.common.struts2.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "form", tldTagClass = "org.apache.struts2.views.jsp.ui.FormTag", description = "Renders an input form")
public class Td extends ClosingUIBean {
	public static final String	OPEN_TEMPLATE	= "td";
	public static final String	TEMPLATE		= "td-close";

	public Td(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
	}
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
	@Override
	public String getDefaultOpenTemplate() {
		return OPEN_TEMPLATE;
	}
}
