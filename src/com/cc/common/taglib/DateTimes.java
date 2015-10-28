
package com.cc.common.taglib;

import java.io.*;
import java.util.Date;

import org.apache.commons.logging.*;
import org.apache.struts2.components.*;

import com.cc.common.util.*;
import com.opensymphony.xwork2.util.*;

public class DateTimes extends Component {

	private static final Log	logger	= LogFactory.getLog(DateTimes.class);

	public DateTimes(ValueStack arg0) {
		super(arg0);
	}

	private String	value;
	private String	format		= "yyyy-MM-dd HH:mm:ss";
	private String	datetype	= "timestamp";

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		if (value == null) {
			value = "top";
		} else if (altSyntax()) {
			if (value.startsWith("%{") && value.endsWith("}")) {
				value = value.substring(2, value.length() - 1);
			}
		}
		Date date = null;
		if (this.datetype.equalsIgnoreCase("timestamp")) {
			long atime = (Long) this.getStack().findValue(value);
			date = new Date(atime);
		} else if (this.datetype.equalsIgnoreCase("date")) {
			date = (Date) this.getStack().findValue(value);
			if (date == null) {
				date = new Date(0);
			}
		} else {
			date = new Date();
		}
		String stime = ComUtils.formatDate(date, format);
		try {
			writer.write(stime);
		} catch (IOException e) {
			logger.error(e);
		}
		return result;
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
