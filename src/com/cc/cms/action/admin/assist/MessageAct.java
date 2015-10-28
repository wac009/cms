
package com.cc.cms.action.admin.assist;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.assist.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.service.assist.*;
import com.cc.cms.service.main.*;
import com.cc.common.util.*;

/**
 * @author wangcheng
 */
@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
@Scope("prototype")
@Controller("web.action.admin.messageAct")
public class MessageAct extends CmsAct {

	private static final Logger	log	= LoggerFactory.getLogger(MessageAct.class);
	@Autowired
	private IMessageSvc			messageSvc;
	@Autowired
	private IReceiverMessageSvc	receiverMessageSvc;
	@Autowired
	private IUserSvc			userSvc;
	@Autowired
	private IGroupSvc			groupSvc;
	private List<User>			users;
	private List<Group>			groups;
	private Integer				uid;
	private Integer				gid;
	private Message				message;
	private ReceiverMessage		receiverMessage;
	private Integer				mf;												// 0:收信;1:发信
	private Integer				receiveCount;
	private Integer				notReadCount;
	private Integer				sendCount;
	private Integer				draftCount;
	private Integer				trashCount;
	/** 查询条件 */
	private String				queryTitle;
	private Boolean				queryStatus;
	private String				queryBeginTime;
	private String				queryEndTime;
	private Timestamp			sendBeginTime;
	private Timestamp			sendEndTime;

	@Override
	public String list() {
		pagination = receiverMessageSvc.getPage(getWebId(), null, getUserId(), queryTitle, sendBeginTime, sendEndTime, queryStatus, 0, false, pageNo, getCookieCount());
		initCount();
		return LIST;
	}

	// 收件箱
	public String receivebox() {
		pagination = receiverMessageSvc.getPage(getWebId(), null, getUserId(), queryTitle, sendBeginTime, sendEndTime, queryStatus, 0, false, pageNo, getCookieCount());
		initCount();
		return "receivebox";
	}

	// 发件箱
	public String sendbox() {
		pagination = messageSvc.getPage(getWebId(), getUserId(), null, queryTitle, sendBeginTime, sendEndTime, queryStatus, 1, false, pageNo, getCookieCount());
		initCount();
		return "sendbox";
	}

	// 草稿箱
	public String draftbox() {
		pagination = messageSvc.getPage(getWebId(), getUserId(), null, queryTitle, sendBeginTime, sendEndTime, queryStatus, 2, false, pageNo, getCookieCount());
		initCount();
		return "draftbox";
	}

	// 垃圾箱
	public String trashbox() {
		pagination = receiverMessageSvc.getPage(getWebId(), getUserId(), null, queryTitle, sendBeginTime, sendEndTime, queryStatus, 3, false, pageNo, getCookieCount());
		initCount();
		return "trashbox";
	}

	private void initCount() {
		receiveCount = receiverMessageSvc.getCount(getWebId(), null, getUserId(), null, 0);
		notReadCount = receiverMessageSvc.getCount(getWebId(), null, getUserId(), false, 0);
		sendCount = messageSvc.getCount(getWebId(), getUserId(), null, null, 1);
		draftCount = messageSvc.getCount(getWebId(), getUserId(), null, null, 2);
		trashCount = receiverMessageSvc.getCount(getWebId(), null, getUserId(), null, 3);
	}

	// 发送站内信
	@Override
	public String add() {
		initCount();
		initUsers();
		initGroups();
		return ADD;
	}

	// 直接发送
	public String send() {
		Timestamp now = ComUtils.now();
		ReceiverMessage receiverMessage = new ReceiverMessage();
		User msgReceiverUser = null;
		// 单个会员发送
		if (uid != null) {
			msgReceiverUser = userSvc.findById(uid);
			if (msgReceiverUser != null) {
				messageInfoSet(message, receiverMessage, getUser(), msgReceiverUser, now, getWeb());
			}
		}
		// 按会员组推送站内信
		if (gid != null && !gid.equals(-1)) {
			List<User> users;
			User tempUser;
			Message tempMsg;
			ReceiverMessage tempReceiverMsg;
			if (gid.equals(0)) {
				// 所有未禁用会员
				users = userSvc.getList(null, null, null, null, false, false, null);
				if (users != null && users.size() > 0) {
					for (int i = 0; i < users.size(); i++) {
						tempUser = users.get(i);
						tempMsg = new Message();
						tempMsg.setMsgTitle(message.getMsgTitle());
						tempMsg.setMsgContent(message.getMsgContent());
						tempReceiverMsg = new ReceiverMessage();
						if (msgReceiverUser != null) {
							if (!tempUser.equals(msgReceiverUser)) {
								messageInfoSet(tempMsg, tempReceiverMsg, getUser(), tempUser, now, getWeb());
							}
						} else {
							messageInfoSet(tempMsg, tempReceiverMsg, getUser(), tempUser, now, getWeb());
						}
					}
				}
			} else {
				// 非禁用的会员
				users = userSvc.getList(null, null, null, gid, false, false, null);
				if (users != null && users.size() > 0) {
					for (int i = 0; i < users.size(); i++) {
						tempUser = users.get(i);
						tempMsg = new Message();
						tempMsg.setMsgTitle(message.getMsgTitle());
						tempMsg.setMsgContent(message.getMsgContent());
						tempReceiverMsg = new ReceiverMessage();
						if (msgReceiverUser != null) {
							if (!tempUser.equals(msgReceiverUser)) {
								messageInfoSet(tempMsg, tempReceiverMsg, getUser(), tempUser, now, getWeb());
							}
						} else {
							messageInfoSet(tempMsg, tempReceiverMsg, getUser(), tempUser, now, getWeb());
						}
					}
				}
			}
		}
		return sendbox();
	}

	private void messageInfoSet(Message message, ReceiverMessage receiverMessage, User sendUser, User receiverUser, Timestamp sendTime, Site site) {
		message.setMsgBox(1);
		message.setMsgSendUser(sendUser);
		message.setMsgReceiverUser(receiverUser);
		message.setMsgStatus(false);
		message.setSendTime(sendTime);
		message.setSite(site);
		messageSvc.save(message);
		receiverMessage.setMsgBox(0);
		receiverMessage.setMsgContent(message.getMsgContent());
		receiverMessage.setMsgSendUser(sendUser);
		receiverMessage.setMsgReceiverUser(receiverUser);
		receiverMessage.setMsgStatus(false);
		receiverMessage.setMsgTitle(message.getMsgTitle());
		receiverMessage.setSendTime(sendTime);
		receiverMessage.setSite(site);
		receiverMessage.setMessage(message);
		// 接收端（有一定冗余）
		receiverMessageSvc.save(receiverMessage);
		log.info("member CmsMessage send CmsMessage success. id={}", message.getId());
	}

	// 存草稿
	public String saveDraft() {
		message.setMsgBox(2);
		message.setMsgSendUser(getUser());
		User msgReceiverUser = userSvc.findById(uid);
		message.setMsgReceiverUser(msgReceiverUser);
		message.setMsgStatus(false);
		// 作为草稿和发件箱的区别
		message.setSendTime(null);
		// message.setSendTime(new Date());
		message.setSite(getWeb());
		messageSvc.save(message);
		ReceiverMessage receiverMessage = new ReceiverMessage(message);
		receiverMessage.setMsgBox(2);
		receiverMessage.setMessage(message);
		// 接收端（有一定冗余）
		receiverMessageSvc.save(receiverMessage);
		addActionMessage("成功保存为草稿!");
		return draftbox();
	}

	public String resend() {
		initCount();
		message = messageSvc.findById(id);
		initGroups();
		initUsers();
		return "resend";
	}

	public String tosend() {
		message = messageSvc.findById(id);
		message.setMsgBox(1);
		message.setSendTime(ComUtils.now());
		messageSvc.update(message);
		Set<ReceiverMessage> receiverMessageSet = message.getReceiverMsgs();
		Iterator<ReceiverMessage> it = receiverMessageSet.iterator();
		ReceiverMessage receiverMessage;
		while (it.hasNext()) {
			receiverMessage = it.next();
			receiverMessage.setMsgBox(0);
			receiverMessage.setSendTime(new Date());
			receiverMessage.setMessage(message);
			// 接收端（有一定冗余）
			receiverMessageSvc.update(receiverMessage);
		}
		log.info("member CmsMessage send CmsMessage success. id={}", message.getId());
		return sendbox();
	}

	// 修改消息
	@Override
	public String edit() {
		initCount();
		message = messageSvc.findById(id);
		initGroups();
		initUsers();
		return EDIT;
	}

	// 更新修改
	public String update() {
		message = messageSvc.update(message);
		// 更新发送表的信息，收件表的信息同步更新
		Set<ReceiverMessage> receiverMessageSet = message.getReceiverMsgs();
		Iterator<ReceiverMessage> it = receiverMessageSet.iterator();
		ReceiverMessage receiverMessage;
		while (it.hasNext()) {
			receiverMessage = it.next();
			receiverMessage.setMsgContent(message.getContentHtml());
			receiverMessage.setMsgReceiverUser(message.getMsgReceiverUser());
			receiverMessage.setMsgTitle(message.getMsgTitle());
			receiverMessage.setMessage(message);
			// 接收端（有一定冗余）
			receiverMessageSvc.update(receiverMessage);
		}
		log.info("member CmsMessage update CmsMessage success. id={}", message.getId());
		return sendbox();
	}

	// 阅读
	public String read() {
		initCount();
		if (mf == 0) {
			// 阅读收信
			// 收件人查看更新已读状态
			receiverMessage = receiverMessageSvc.findById(id);
			if (receiverMessage.getMsgReceiverUser().equals(getUser())) {
				receiverMessage.setMsgStatus(true);
				receiverMessageSvc.update(receiverMessage);
			}
		} else {
			// 阅读已发信
			message = messageSvc.findById(id);
			message.setMsgStatus(true);
			messageSvc.updateDefault(message);
		}
		return "read";
	}

	// 转发
	public String forward() {
		initCount();
		if (mf == 0) {
			receiverMessage = receiverMessageSvc.findById(id);
			receiverMessage.setMsgTitle("[转发]" + receiverMessage.getMsgTitle());
		} else {
			message = messageSvc.findById(id);
			message.setMsgTitle("[转发]" + message.getMsgTitle());
		}
		initGroups();
		initUsers();
		return "forward";
	}

	// 回复
	public String reply() {
		initCount();
		receiverMessage = receiverMessageSvc.findById(id);
		receiverMessage.setMsgTitle("[回复]" + receiverMessage.getMsgTitle());
		return "reply";
	}

	// 删除到垃圾箱
	public String trash() {
		vldBatch();
		Iterator<ReceiverMessage> it;
		for (Integer i = 0; i < ids.length; i++) {
			message = messageSvc.findById(ids[i]);
			receiverMessage = receiverMessageSvc.findById(ids[i]);
			if (message != null && message.getMsgSendUser().equals(getUser())) {
				// 清空发信表的同时复制该数据到收信表（收信人未空）
				receiverMessage = new ReceiverMessage();
				receiverMessage.setMsgBox(3);
				receiverMessage.setMsgContent(message.getMsgContent());
				receiverMessage.setMsgSendUser(message.getMsgSendUser());
				receiverMessage.setMsgReceiverUser(getUser());
				receiverMessage.setMsgStatus(message.getMsgStatus());
				receiverMessage.setMsgTitle(message.getMsgTitle());
				receiverMessage.setSendTime(message.getSendTime());
				receiverMessage.setSite(message.getSite());
				receiverMessage.setMessage(null);
				// 接收端（有一定冗余）
				receiverMessageSvc.save(receiverMessage);
				// 清空该发件对应的收件关联关系
				Set<ReceiverMessage> receiverMessages = message.getReceiverMsgs();
				ReceiverMessage tempReceiverMessage;
				if (receiverMessages != null && receiverMessages.size() > 0) {
					it = receiverMessages.iterator();
					while (it.hasNext()) {
						tempReceiverMessage = it.next();
						tempReceiverMessage.setMessage(null);
						receiverMessageSvc.update(tempReceiverMessage);
					}
				}
				messageSvc.deleteById(ids[i]);
			}
			if (receiverMessage != null && receiverMessage.getMsgReceiverUser().equals(getUser())) {
				receiverMessage.setMsgBox(3);
				receiverMessageSvc.update(receiverMessage);
			}
			log.info("member CmsMessage trash CmsMessage success. id={}", ids[i]);
		}
		addActionMessage("成功删除到垃圾箱!");
		return list();
	}

	// 删除
	public String delete() {
		vldBatch();
		receiverMessageSvc.deleteByIds(ids);
		addActionMessage("删除成功!");
		return list();
	}

	// 还原
	public String revert() {
		vldBatch();
		for (Integer i = 0; i < ids.length; i++) {
			receiverMessage = receiverMessageSvc.findById(ids[i]);
			// 收件箱
			if (receiverMessage != null && receiverMessage.getMsgReceiverUser().equals(getUser())) {
				receiverMessage.setMsgBox(0);
				receiverMessageSvc.update(receiverMessage);
			}
			log.info("member CmsMessage revert CmsMessage success. id={}", ids[i]);
		}
		addActionMessage("成功还原 " + ids.length + " 条信息");
		return list();
	}

	// 清空
	public String empty() {
		List<ReceiverMessage> receiverMessages = receiverMessageSvc.getList(getWebId(), null, getUserId(), null, null, null, null, null, null);
		for (ReceiverMessage receiverMessage : receiverMessages) {
			if (receiverMessage != null && receiverMessage.getMsgReceiverUser().equals(getUser())) {
				receiverMessageSvc.delete(receiverMessage);
			}
		}
		List<Message> messages = messageSvc.getList(getWebId(), getUserId(), null, null, null, null, null, null, null);
		for (Message message : messages) {
			if (message != null && message.getMsgSendUser().equals(getUser())) {
				messageSvc.delete(message);
			}
		}
		addActionMessage("已清空站内信");
		return list();
	}

	// 查找未读信息条数
	public String findUnreadMessagesByUser() {
		User user = getUser();
		Site site = getWeb();
		if (user == null) {
			getJsonRoot().put("result", false);
		} else {
			List<ReceiverMessage> receiverMessages = receiverMessageSvc.getList(site.getId(), null, user.getId(), null, null, null, false, 0, false);
			getJsonRoot().put("result", true);
			if (receiverMessages != null && receiverMessages.size() > 0) {
				getJsonRoot().put("count", receiverMessages.size());
			} else {
				getJsonRoot().put("count", 0);
			}
			getJsonRoot().put("result", true);
		}
		return JSON;
	}

	private void initUsers() {
		users = userSvc.getAdminList(getWebId(), null, false, null);
		users.add(0, null);
	}

	private void initGroups() {
		groups = groupSvc.findAll();
		groups.add(0, null);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ReceiverMessage getReceiverMessage() {
		return receiverMessage;
	}

	public void setReceiverMessage(ReceiverMessage receiverMessage) {
		this.receiverMessage = receiverMessage;
	}

	public String getQueryTitle() {
		return queryTitle;
	}

	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}

	public Boolean getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(Boolean queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getQueryBeginTime() {
		return queryBeginTime;
	}

	public void setQueryBeginTime(String queryBeginTime) {
		this.queryBeginTime = queryBeginTime;
	}

	public String getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public Integer getMf() {
		return mf;
	}

	public void setMf(Integer mf) {
		this.mf = mf;
	}

	public Integer getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}

	public Integer getNotReadCount() {
		return notReadCount;
	}

	public void setNotReadCount(Integer notReadCount) {
		this.notReadCount = notReadCount;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getDraftCount() {
		return draftCount;
	}

	public void setDraftCount(Integer draftCount) {
		this.draftCount = draftCount;
	}

	public Integer getTrashCount() {
		return trashCount;
	}

	public void setTrashCount(Integer trashCount) {
		this.trashCount = trashCount;
	}
}
