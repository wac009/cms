package com.cc.cms.dao.main;

import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.PublicationType;

public interface IPublicationTypeDao extends ICmsDao<PublicationType> {
	public PublicationType getPrev(PublicationType bean);
	public PublicationType getNext(PublicationType bean);
	public Integer getMaxPriority();
}
