package com.cc.cms.service.main;

import com.cc.cms.entity.main.PeriodicalCatalog;
import com.cc.cms.service.ICmsSvc;

public interface IPeriodicalCatalogSvc extends ICmsSvc<PeriodicalCatalog> {
	public PeriodicalCatalog savePeriodicalCatalog(PeriodicalCatalog bean);
	public PeriodicalCatalog updatePeriodicalCatalog(PeriodicalCatalog bean);
	/**
	 * 排序 检测是否可移动
	 * @return 排序后的对象
	 */
	public boolean isUp(PeriodicalCatalog bean);
	public boolean isDown(PeriodicalCatalog bean);
	/**
	 * 排序
	 * @return 排序后的对象
	 */
	public PeriodicalCatalog up(Integer id);
	public PeriodicalCatalog down(Integer id);
	public PeriodicalCatalog getPrev(PeriodicalCatalog bean);
	public PeriodicalCatalog getNext(PeriodicalCatalog bean);
}
