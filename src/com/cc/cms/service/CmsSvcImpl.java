
package com.cc.cms.service;

import java.io.*;
import java.util.*;

import com.cc.cms.statistic.*;
import com.cc.core.service.impl.*;

public class CmsSvcImpl<T extends Serializable> extends CoreSvcImpl<T> implements ICmsSvc<T> {
	@Override
	public Map<String, List<Statistic>> getWelcomeData(Integer siteId) {
		return null;
	}
}
