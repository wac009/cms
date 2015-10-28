
package com.cc.cms.entity.main;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.main.base.*;

public class Topic extends BaseTopic {
	private static final long	serialVersionUID	= 1L;

	/** 获得简短名称，如果不存在则返回名称
	 * 
	 * @return */
	public String getSname() {
		if (!StringUtils.isBlank(getShortName())) {
			return getShortName();
		} else {
			return getName();
		}
	}

	public void init() {
		blankToNull();
		if (getDisabled() == null) {
			setDisabled(false);
		}
		if (getRecommend() == null) {
			setRecommend(false);
		}
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getTitleImg())) {
			setTitleImg(null);
		}
		if (StringUtils.isBlank(getContentImg())) {
			setContentImg(null);
		}
		if (StringUtils.isBlank(getShortName())) {
			setShortName(null);
		}
	}

	/** 从集合中获取ID数组
	 * 
	 * @param topics
	 * @return */
	public static Integer[] fetchIds(Collection<Topic> topics) {
		Integer[] ids = new Integer[topics.size()];
		int i = 0;
		for (Topic g : topics) {
			ids[i++] = g.getId();
		}
		return ids;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Topic() {
		super();
	}

	/** Constructor for primary key */
	public Topic(java.lang.Integer id) {
		super(id);
	}

	/** Constructor for required fields */
	public Topic(java.lang.Integer id, java.lang.String name, java.lang.Integer priority, java.lang.Boolean recommend) {

		super(id, name, priority, recommend);
	}

	/* [CONSTRUCTOR MARKER END] */

}