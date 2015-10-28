package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.main.Publication;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.page.Pagination;

public interface IPublicationSvc extends ICmsSvc<Publication> {
	/**
	 * 为前台标签获得文章列表
	 * 
	 * @param webId
	 * @param chnlId
	 * @param ctgId
	 * @param searchKey
	 * @param hasTitleImg
	 * @param recommend
	 * @param topLevel
	 * @param orderBy
	 * @param isPage
	 * @param firstResult
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getForTag(Integer webId, int orderBy, boolean isPage, int firstResult, int pageNo, int pageSize);
	public List<Publication> findAll(Integer webId);
	public Publication savePublication(Publication publication);
	/**
	 * 排序 检测是否可移动
	 * 
	 * @return 排序后的对象
	 */
	public boolean isUp(Publication bean);
	public boolean isDown(Publication bean);
	/**
	 * 排序
	 * 
	 * @return 排序后的对象
	 */
	public Publication up(Integer id);
	public Publication down(Integer id);
	public Publication getPrev(Publication bean);
	public Publication getNext(Publication bean);
}
