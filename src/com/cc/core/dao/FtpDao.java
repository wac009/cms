package com.cc.core.dao;

import java.util.List;

import com.cc.core.entity.*;
import com.cc.common.orm.Updater;

public interface FtpDao {
	public List<Ftp> getList();

	public Ftp findById(Integer id);

	public Ftp save(Ftp bean);

	public Ftp updateByUpdater(Updater<Ftp> updater);

	public Ftp deleteById(Integer id);
}