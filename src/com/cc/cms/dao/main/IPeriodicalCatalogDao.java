package com.cc.cms.dao.main;

import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.PeriodicalCatalog;

public interface IPeriodicalCatalogDao extends ICmsDao<PeriodicalCatalog> {
	public PeriodicalCatalog getPrev(PeriodicalCatalog bean);
	public PeriodicalCatalog getNext(PeriodicalCatalog bean);
	public Integer getMaxPriority();
}
