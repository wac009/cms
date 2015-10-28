package com.cc.core.dao;

import java.util.List;

import com.cc.core.entity.*;

public interface ICoreConfigDao extends ICoreDao<CoreConfig> {
	public List<CoreConfig> getList();

	public CoreConfig findById(String id);

	public CoreConfig deleteById(String id);
}