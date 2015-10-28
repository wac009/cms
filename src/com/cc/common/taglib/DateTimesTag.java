
package com.cc.common.taglib;

import javax.servlet.http.*;

import org.apache.struts2.components.*;
import org.apache.struts2.views.jsp.*;

import com.opensymphony.xwork2.util.*;

public class DateTimesTag extends ComponentTagSupport {

	private static final long	serialVersionUID	= -8402343390806757666L;

	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return new DateTimes(arg0);
	}

	private String	value;
	private String	format		= "yyyy-MM-dd HH:mm:ss";
	private String	datetype	= "timestamp";

	@Override
	protected void populateParams() {
		super.populateParams();
		DateTimes tag = (DateTimes) component;
		tag.setDatetype(datetype);
		tag.setFormat(format);
		tag.setValue(value);
	}

	public String getDatetype() {
		return datetype;
	}

	public void setDatetype(String datetype) {
		this.datetype = datetype;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
