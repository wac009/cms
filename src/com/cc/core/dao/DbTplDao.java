package com.cc.core.dao;

import java.util.List;

import com.cc.core.entity.*;

public interface DbTplDao {
	public List<DbTpl> getStartWith(String prefix);

	public List<DbTpl> getChild(String path, boolean isDirectory);

	public DbTpl findById(String id);

	public DbTpl save(DbTpl bean);

	public DbTpl deleteById(String id);
}