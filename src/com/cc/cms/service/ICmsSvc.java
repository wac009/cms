package com.cc.cms.service;

import java.io.*;
import java.util.*;

import com.cc.cms.statistic.*;
import com.cc.core.service.*;

public interface ICmsSvc<T extends Serializable> extends ICoreSvc<T> {
	public Map<String, List<Statistic>> getWelcomeData(Integer siteId);
}
