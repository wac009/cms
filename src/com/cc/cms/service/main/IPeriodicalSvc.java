package com.cc.cms.service.main;

import java.util.List;

import com.cc.cms.entity.main.Periodical;
import com.cc.cms.service.ICmsSvc;

public interface IPeriodicalSvc extends ICmsSvc<Periodical> {
	public List<Periodical> findByPublication(Integer publicationId);
	public List<String> getYearList();
	public Periodical savePeriodical(Periodical bean,String attachmentPath, String attachmentName, String attachmentFilename);
	public Periodical updatePeriodical(Periodical bean, String attachmentPath, String attachmentName, String attachmentFilename);
	/**
	 * 排序 检测是否可移动
	 * 
	 * @return 排序后的对象
	 */
	public boolean isUp(Periodical bean);
	public boolean isDown(Periodical bean);
	/**
	 * 排序
	 * 
	 * @return 排序后的对象
	 */
	public Periodical up(Integer id);
	public Periodical down(Integer id);
	public Periodical getPrev(Periodical bean);
	public Periodical getNext(Periodical bean);
}
