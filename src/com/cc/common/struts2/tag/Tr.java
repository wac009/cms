package com.cc.common.struts2.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "tr", tldTagClass = "org.apache.struts2.views.jsp.ui.LabelTag", description = "Render </tr><tr>")
public class Tr extends UIBean {
	final public static String	TEMPLATE	= "tr";

	public Tr(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}
	@Override
	protected String getDefaultTemplate() {
		return TEMPLATE;
	}
}
