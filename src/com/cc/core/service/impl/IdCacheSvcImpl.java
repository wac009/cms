package com.cc.core.service.impl;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cc.core.service.IdCacheSvc;

@Service
public class IdCacheSvcImpl implements IdCacheSvc {
	@Override
	public void put(Long id, Object key, Object... otherKeys) {
		commonIdCache.put(new Element(getKey(key, otherKeys), id));
	}

	@Override
	public Long get(Object key, Object... otherKeys) {
		Element e = commonIdCache.get(getKey(key, otherKeys));
		if (e != null) {
			return (Long) e.getValue();
		} else {
			return null;
		}
	}

	@Override
	public boolean remove(Object key, Object... otherKeys) {
		return commonIdCache.remove(getKey(key, otherKeys));
	}

	private String getKey(Object key, Object... otherKeys) {
		if (ArrayUtils.isEmpty(otherKeys)) {
			return String.valueOf(key);
		} else {
			StringBuilder buf = new StringBuilder();
			buf.append(key).append(SPLIT);
			for (Object s : otherKeys) {
				buf.append(s).append(SPLIT);
			}
			return buf.toString();
		}
	}

	private Ehcache commonIdCache;

	@Autowired
	public void setCommonIdCache(@Qualifier("commonId") Ehcache commonIdCache) {
		this.commonIdCache = commonIdCache;
	}
}
