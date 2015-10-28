
package com.cc.common.page;

/**
 * 可分页接口
 */
public interface IPaginable {

	/**
	 * 总记录数
	 */
	public int getTotalCount();

	/**
	 * 总页数
	 */
	public int getTotalPage();

	/**
	 * 每页记录数
	 */
	public int getPageSize();

	/**
	 * 当前页号
	 */
	public int getPageNo();

	/**
	 * 是否第一页
	 */
	public boolean isFirstPage();

	/**
	 * 是否最后一页
	 */
	public boolean isLastPage();

	/**
	 * 返回下页的页号
	 */
	public int getNextPage();

	/**
	 * 返回上页的页号
	 */
	public int getPrePage();
}
