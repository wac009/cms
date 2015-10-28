
package com.cc.cms.service.main.impl;

import net.sf.ehcache.*;

import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.cc.cms.entity.main.*;
import com.cc.cms.service.main.*;

/** 内容计数器缓存实现
 * 
 * @author wangcheng */
@Service
public class ContentCountCacheImpl implements IContentCountCache, DisposableBean {
	private Logger				log			= LoggerFactory.getLogger(ContentCountCacheImpl.class);

	// 间隔时间
	private int					interval	= 10 * 60 * 1000;										// 10分钟
	// 最后刷新时间
	private long				refreshTime	= System.currentTimeMillis();

	@Autowired
	private IContentCountSvc	contentCountMng;
	@Autowired
	@Qualifier("contentCount")
	private Cache				contentCountCache;

	/** 刷新间隔时间
	 * 
	 * @param interval 单位分钟 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
	}

	/** @see IContentCountCache#viewAndGet(Integer) */
	@Override
	public int[] viewAndGet(Integer id) {
		ContentCount count = contentCountMng.findById(id);
		if (count == null) {
			return null;
		}
		Element e = contentCountCache.get(id);
		Integer views;
		if (e != null) {
			views = (Integer) e.getValue() + 1;
		} else {
			views = 1;
		}
		contentCountCache.put(new Element(id, views));
		refreshToDB();
		return new int[] { views + count.getViews(), count.getComments(), count.getDownloads(), count.getUps(), count.getDowns() };
	}

	private void refreshToDB() {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			int count = contentCountMng.freshCacheToDB(contentCountCache);
			// 清除缓存
			contentCountCache.removeAll();
			log.info("refresh cache views to DB: {}", count);
		}
	}

	/** 销毁BEAN时，缓存入库。 */
	@Override
	public void destroy() throws Exception {
		int count = contentCountMng.freshCacheToDB(contentCountCache);
		log.info("Bean destroy.refresh cache views to DB: {}", count);
	}

}
