package com.cc.core.dao;

import com.cc.core.entity.*;
import com.cc.common.orm.Updater;

public interface DbFileDao {
	public DbFile findById(String id);

	public DbFile save(DbFile bean);

	public DbFile updateByUpdater(Updater<DbFile> updater);

	public DbFile deleteById(String id);
}