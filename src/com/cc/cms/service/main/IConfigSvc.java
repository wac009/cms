
package com.cc.cms.service.main;

import java.util.Date;

import com.cc.cms.entity.main.Config;
import com.cc.cms.entity.main.MarkConfig;
import com.cc.cms.entity.main.MemberConfig;
import com.cc.cms.service.ICmsSvc;

public interface IConfigSvc extends ICmsSvc<Config> {

	public Config get();

	public void updateCountCopyTime(Date d);

	public void updateCountClearTime(Date d);

	public MarkConfig updateMarkConfig(MarkConfig mark);

	public void updateMemberConfig(MemberConfig memberConfig);
}