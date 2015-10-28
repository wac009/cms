package com.cc.cms.service.main;

import com.cc.cms.entity.main.PublicationType;
import com.cc.cms.service.ICmsSvc;

public interface IPublicationTypeSvc extends ICmsSvc<PublicationType> {
	public PublicationType savePublicationType(PublicationType type);
	/**
	 * 排序 检测是否可移动
	 * 
	 * @return 排序后的对象
	 */
	public boolean isUp(PublicationType bean);
	public boolean isDown(PublicationType bean);
	/**
	 * 排序
	 * 
	 * @return 排序后的对象
	 */
	public PublicationType up(Integer id);
	public PublicationType down(Integer id);
	public PublicationType getPrev(PublicationType bean);
	public PublicationType getNext(PublicationType bean);
}
