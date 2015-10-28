package com.cc.cms.entity.main;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.main.base.*;

public class UserExt extends BaseUserExt {
	private static final long serialVersionUID = 1L;

	public void blankToNull() {
		// 将空串设置为null，便于前台处理。
		if (StringUtils.isBlank(getRealname())) {
			setRealname(null);
		}
		if (StringUtils.isBlank(getIntro())) {
			setIntro(null);
		}
		if (StringUtils.isBlank(getAddress())) {
			setAddress(null);
		}
		if (StringUtils.isBlank(getMobile())) {
			setMobile(null);
		}
		if (StringUtils.isBlank(getTel())) {
			setTel(null);
		}
		if (StringUtils.isBlank(getMsn())) {
			setMsn(null);
		}
		if (StringUtils.isBlank(getQq())) {
			setQq(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public UserExt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public UserExt (java.lang.Integer id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}