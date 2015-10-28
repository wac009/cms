package com.cc.common.orm;

/**
 * 实体按优先级排序类的接口。用于hibernate的sort排序
 * 
 * @author wangcheng
 * 
 */
public interface IPriorityInterface {
	/**
	 * 获得优先级
	 * 
	 * @return
	 */
	public Number getPriority();

	/**
	 * 获得ID
	 * 
	 * @return
	 */
	public Number getId();
}
