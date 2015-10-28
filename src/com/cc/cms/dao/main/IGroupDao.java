
package com.cc.cms.dao.main;

import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Group;

/**
 * @author wangcheng
 */
public interface IGroupDao extends ICmsDao<Group> {

	public Group getRegDef();
	
	public Group getPrev(Group bean);

	public Group getNext(Group bean);

	public Integer getMaxPriority();
	
}
