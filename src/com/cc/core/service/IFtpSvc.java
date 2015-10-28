package com.cc.core.service;

import java.util.List;

import com.cc.core.entity.*;

public interface IFtpSvc {
	public List<Ftp> getList();

	public Ftp findById(Integer id);

	public Ftp save(Ftp bean);

	public Ftp update(Ftp bean);

	public Ftp deleteById(Integer id);

	public Ftp[] deleteByIds(Integer[] ids);
}