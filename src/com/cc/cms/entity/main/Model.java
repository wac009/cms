
package com.cc.cms.entity.main;

import static com.cc.cms.Constants.*;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.main.base.*;

public class Model extends BaseModel {
	private static final long	serialVersionUID	= 1L;

	public String getTplChannel(String solution, boolean def) {
		StringBuilder t = new StringBuilder();
		t.append(solution).append("/");
		if (getHasContent()) {
			t.append(TPLDIR_CHANNEL);
		} else {
			t.append(TPLDIR_ALONE);
		}
		t.append("/");
		String prefix = getTplChannelPrefix();
		if (def) {
			if (!StringUtils.isBlank(prefix)) {
				t.append(prefix);
			} else {
				t.append(DEFAULT);
			}
			t.append(TPL_SUFFIX);
		} else {
			if (!StringUtils.isBlank(prefix)) {
				t.append(prefix);
			}
		}
		return t.toString();
	}

	public String getTplContent(String solution, boolean def) {
		StringBuilder t = new StringBuilder();
		t.append(solution).append("/");
		t.append(TPLDIR_CONTENT);
		t.append("/");
		String prefix = getTplContentPrefix();
		if (def) {
			if (!StringUtils.isBlank(prefix)) {
				t.append(prefix);
			} else {
				t.append(DEFAULT);
			}
			t.append(TPL_SUFFIX);
		} else {
			if (!StringUtils.isBlank(prefix)) {
				t.append(prefix);
			}
		}
		return t.toString();

	}

	public void init() {
		if (getDisabled() == null) {
			setDisabled(false);
		}
		if (getDef() == null) {
			setDef(false);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Model() {
		super();
	}

	/** Constructor for primary key */
	public Model(java.lang.Integer id) {
		super(id);
	}

	/** Constructor for required fields */
	public Model(java.lang.Integer id, java.lang.String name, java.lang.String path, java.lang.Integer titleImgWidth,
			java.lang.Integer titleImgHeight, java.lang.Integer contentImgWidth, java.lang.Integer contentImgHeight, java.lang.Integer priority,
			java.lang.Boolean hasContent, java.lang.Boolean disabled, java.lang.Boolean def) {

		super(id, name, path, titleImgWidth, titleImgHeight, contentImgWidth, contentImgHeight, priority, hasContent, disabled, def);
	}

	/* [CONSTRUCTOR MARKER END] */

}