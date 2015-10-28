
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.User;

/**
 * @author wangcheng
 */
public interface IUserDao extends ICmsDao<User> {

	public List<User> getList(String username, String email, Integer siteId, Integer groupId, Boolean disabled, Boolean admin, Integer rank);

	public List<User> getAdminList(Integer siteId, Boolean allChannel, Boolean disabled, Integer rank);

	public User findByUsername(String username);

	public int countByUsername(String username);

	public int countMemberByUsername(String username);

	public int countByEmail(String email);
}
