package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Publication;
import com.cc.common.page.Pagination;

public interface IPublicationDao extends ICmsDao<Publication> {
	public Pagination getForTag(Integer webId, int orderBy, boolean isPage, int firstResult, int pageNo, int pageSize);
	public List<Publication> findAll(Integer webId);
	public Publication getPrev(Publication bean);
	public Publication getNext(Publication bean);
	public Integer getMaxPriority();
}
