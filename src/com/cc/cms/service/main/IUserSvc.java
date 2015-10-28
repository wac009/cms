
package com.cc.cms.service.main;

import java.util.List;
import java.util.Set;

import com.cc.cms.entity.main.Site;
import com.cc.cms.entity.main.User;
import com.cc.cms.entity.main.UserExt;
import com.cc.cms.service.ICmsSvc;
import com.cc.common.email.EmailSender;
import com.cc.common.email.MessageTemplate;

/** @author wangcheng */
public interface IUserSvc extends ICmsSvc<User> {

	public List<User> getList(String username, String email, Integer siteId, Integer groupId, Boolean disabled, Boolean admin, Integer rank);

	public List<User> getAdminList(Integer siteId, Boolean allChannel, Boolean disabled, Integer rank);

	public User findByUsername(String username);

	public User registerMember(String username, String email, String password, String ip, Integer groupId, UserExt userExt);

	public User registerMember(String username, String email, String password, String ip, Integer groupId, UserExt userExt, Boolean activation,
			EmailSender sender, MessageTemplate msgTpl);

	public void updateLoginInfo(Integer userId, String ip);

	public void updateUploadSize(Integer userId, Integer size);

	public void updatePwdEmail(Integer id, String password, String email);

	public boolean isPasswordValid(Integer id, String password);

	public User saveAdmin(User user, String password, String ip, Integer siteId, Integer groupId, Byte step, Boolean allChannels);

	public User updateAdmin(User user, String password, Integer siteId, Integer groupId, Byte step, Boolean allChannels);

	public User updateMember(User user, String password);

	public User updateUserConllection(User user, Integer cid, Integer operate);

	public void addSiteToUser(User user, Site site, Byte checkStep);

	public boolean usernameNotExist(String username);

	public boolean usernameNotExistInMember(String username);

	public boolean emailNotExist(String email);

	public Set<String> getRights(Integer uid);

	public void loadRightsToCache(Integer uid);

	public void handleRightsChange(Integer uid);

	public User deleteById(Integer id);

	public User[] deleteByIds(Integer[] ids);

	public boolean deleteBySite(Integer id);
}
