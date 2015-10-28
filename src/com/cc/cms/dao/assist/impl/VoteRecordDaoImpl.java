/** @author wangcheng */

package com.cc.cms.dao.assist.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.cc.cms.dao.CmsDaoImpl;
import com.cc.cms.dao.assist.IVoteRecordDao;
import com.cc.cms.entity.assist.VoteRecord;

@Repository
public class VoteRecordDaoImpl extends CmsDaoImpl<VoteRecord> implements IVoteRecordDao {

	@Override
	public int deleteByTopic(Integer topicId) {
		String hql = "delete from VoteRecord bean" + " where bean.topic.id=:topicId";
		return getSession().createQuery(hql).setParameter("topicId", topicId).executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public VoteRecord findByUserId(Integer userId, Integer topicId) {
		String hql = "from VoteRecord bean where bean.user.id=:userId" + " and bean.topic.id=:topicId order by bean.time desc";
		List<VoteRecord> list = getSession().createQuery(hql).setParameter("userId", userId).setParameter("topicId", topicId).setMaxResults(1).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public VoteRecord findByIp(String ip, Integer topicId) {
		String hql = "from VoteRecord bean where bean.ip=:ip" + " and bean.topic.id=:topicId order by bean.time desc";
		List<VoteRecord> list = getSession().createQuery(hql).setParameter("ip", ip).setParameter("topicId", topicId).setMaxResults(1).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public VoteRecord findByCookie(String cookie, Integer topicId) {
		String hql = "from VoteRecord bean where bean.cookie=:cookie" + " and bean.topic.id=:topicId order by bean.time desc";
		List<VoteRecord> list = getSession().createQuery(hql).setParameter("cookie", cookie).setParameter("topicId", topicId).setMaxResults(1).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public Date getTimeByIp(String ip, Integer topicId) {
		String hql = "select max(vr.time) as mt from VoteRecord vr where vr.ip=? and vr.topic.id=?";
		return (Date) findUnique(hql, ip, topicId);
	}

	@Override
	public Date getTimeByCookie(String cookie, Integer topicId) {
		String hql = "select max(vr.time) as mt from VoteRecord vr where vr.cookie=? and vr.topic.id=?";
		return (Date) findUnique(hql, cookie, topicId);
	}

	@Override
	public VoteRecord getVoteRecord(String ip, String cookie, Integer topicId) {
		String hql = "select vr from VoteRecord vr where vr.topic.id = ? and (vr.ip=? or vr.cookie=?)";
		Object[] param;
		param = new Object[] { topicId, ip, cookie };
		return (VoteRecord) findUnique(hql, param);
	}
}