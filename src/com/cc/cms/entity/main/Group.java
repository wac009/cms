package com.cc.cms.entity.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cc.cms.entity.main.base.*;
import com.cc.common.orm.IPriorityInterface;

public class Group extends BaseGroup implements IPriorityInterface {
	private static final long serialVersionUID = 1L;

	/**
	 * 从集合中提取ID数组
	 * 
	 * @param groups
	 * @return
	 */
	public static Integer[] fetchIds(Collection<Group> groups) {
		Integer[] ids = new Integer[groups.size()];
		int i = 0;
		for (Group g : groups) {
			ids[i++] = g.getId();
		}
		return ids;
	}

	/**
	 * 根据列表排序
	 * 
	 * @param source
	 *            元素集合
	 * @param target
	 *            有顺序列表
	 * @return 一个新的、按目标排序的列表
	 */
	public static List<Group> sortByList(Set<Group> source,
			List<Group> target) {
		List<Group> list = new ArrayList<Group>(source.size());
		for (Group g : target) {
			if (source.contains(g)) {
				list.add(g);
			}
		}
		return list;
	}

	/**
	 * 是否允许上传后缀
	 * 
	 * @param ext
	 * @return
	 */
	public boolean isAllowSuffix(String ext) {
		String suffix = getAllowSuffix();
		if (StringUtils.isBlank(suffix)) {
			return true;
		}
		String[] attr = StringUtils.split(suffix, ",");
		for (int i = 0, len = attr.length; i < len; i++) {
			if (attr[i].equals(ext)) {
				return true;
			}
		}
		return false;
	}

	public void init() {
		if (getRegDef() == null) {
			setRegDef(false);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Group () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Group (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Group (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Integer priority,
		java.lang.Integer allowPerDay,
		java.lang.Integer allowMaxFile,
		java.lang.Boolean needCaptcha,
		java.lang.Boolean needCheck,
		java.lang.Boolean regDef) {

		super (
			id,
			name,
			priority,
			allowPerDay,
			allowMaxFile,
			needCaptcha,
			needCheck,
			regDef);
	}

	/* [CONSTRUCTOR MARKER END] */

}