package com.cc.common.struts2.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.freemarker.tags.StrutsModels;

import com.opensymphony.xwork2.util.ValueStack;

public class FreemarkerModels extends StrutsModels {
	public FreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
	}

	protected TrModel			tr;
	protected TdModel			td;
	protected WebEditorModel	webEditor;

	public TrModel getTr() {
		if (tr == null) {
			tr = new TrModel(stack, req, res);
		}
		return tr;
	}
	public TdModel getTd() {
		if (td == null) {
			td = new TdModel(stack, req, res);
		}
		return td;
	}
	public WebEditorModel getWebeditor() {
		if (webEditor == null) {
			webEditor = new WebEditorModel(stack, req, res);
		}
		return webEditor;
	}
}
