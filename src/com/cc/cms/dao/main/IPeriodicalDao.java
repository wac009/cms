package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Periodical;

public interface IPeriodicalDao extends ICmsDao<Periodical> {
	public List<Periodical> findByPublication(Integer publicationId);
	public Periodical getPrev(Periodical bean);
	public Periodical getNext(Periodical bean);
	public Integer getMaxPriority();
	public List<String> getYearList();
	public void clearCurrent(Integer publicationId);
}
