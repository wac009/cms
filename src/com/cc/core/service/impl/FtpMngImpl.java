package com.cc.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.core.entity.*;
import com.cc.common.orm.Updater;
import com.cc.core.dao.FtpDao;
import com.cc.core.service.IFtpSvc;

@Service
@Transactional
public class FtpMngImpl implements IFtpSvc {
	@Override
	@Transactional(readOnly = true)
	public List<Ftp> getList() {
		return dao.getList();
	}

	@Override
	@Transactional(readOnly = true)
	public Ftp findById(Integer id) {
		Ftp entity = dao.findById(id);
		return entity;
	}

	@Override
	public Ftp save(Ftp bean) {
		dao.save(bean);
		return bean;
	}

	@Override
	public Ftp update(Ftp bean) {
		Updater<Ftp> updater = new Updater<Ftp>(bean);
		Ftp entity = dao.updateByUpdater(updater);
		return entity;
	}

	@Override
	public Ftp deleteById(Integer id) {
		Ftp bean = dao.deleteById(id);
		return bean;
	}

	@Override
	public Ftp[] deleteByIds(Integer[] ids) {
		Ftp[] beans = new Ftp[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private FtpDao dao;

	@Autowired
	public void setDao(FtpDao dao) {
		this.dao = dao;
	}
}