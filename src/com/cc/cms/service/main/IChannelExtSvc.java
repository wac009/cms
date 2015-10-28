
package com.cc.cms.service.main;

import com.cc.cms.entity.main.Channel;
import com.cc.cms.entity.main.ChannelExt;
import com.cc.cms.service.ICmsSvc;

public interface IChannelExtSvc extends ICmsSvc<ChannelExt>{
	public ChannelExt save(ChannelExt ext, Channel channel);
}