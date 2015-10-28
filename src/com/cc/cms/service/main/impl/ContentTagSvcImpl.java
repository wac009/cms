
package com.cc.cms.service.main.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.cms.dao.main.IContentTagDao;
import com.cc.cms.entity.main.ContentTag;
import com.cc.cms.service.CmsSvcImpl;
import com.cc.cms.service.main.IContentTagSvc;
import com.cc.common.page.Pagination;

/** @author wangcheng */
@Service
@Transactional
public class ContentTagSvcImpl extends CmsSvcImpl<ContentTag> implements IContentTagSvc {

	@Autowired
	public void setDao(IContentTagDao dao) {
		super.setDao(dao);
	}

	@Override
	protected IContentTagDao getDao() {
		return (IContentTagDao) super.getDao();
	}

	private static final Logger	log	= LoggerFactory.getLogger(ContentTagSvcImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<ContentTag> getListForTag(Integer count) {
		return getDao().getList(count, true);
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPageForTag(int pageNo, int pageSize) {
		Pagination page = getDao().getPage(null, pageNo, pageSize, true);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public Pagination getPage(String name, int pageNo, int pageSize) {
		Pagination page = getDao().getPage(name, pageNo, pageSize, false);
		return page;
	}

	@Override
	@Transactional(readOnly = true)
	public ContentTag findByName(String name) {
		return getDao().findByName(name, false);
	}

	@Override
	@Transactional(readOnly = true)
	public ContentTag findByNameForTag(String name) {
		return getDao().findByName(name, true);
	}

	/** @see ContentTagMng#saveTags(String[]) */
	@Override
	public List<ContentTag> saveTags(String[] tagArr) {
		if (tagArr == null || tagArr.length <= 0) {
			return null;
		}
		List<ContentTag> list = new ArrayList<ContentTag>();
		// 用于检查重复
		Set<String> tagSet = new HashSet<String>();
		ContentTag tag;
		for (String name : tagArr) {
			// 检测重复
			for (String t : tagSet) {
				if (t.equalsIgnoreCase(name)) {
					continue;
				}
			}
			tagSet.add(name);
			tag = saveTag(name);
			list.add(tag);
		}
		return list;
	}

	/** @see ContentTagMng#saveTag(String) */
	@Override
	public ContentTag saveTag(String name) {
		ContentTag tag = findByName(name);
		if (tag != null) {
			tag.setCount(tag.getCount() + 1);
		} else {
			tag = new ContentTag();
			tag.setName(name);
			tag.setCount(1);
			tag = save(tag);
		}
		return tag;
	}

	@Override
	public List<ContentTag> updateTags(List<ContentTag> tags, String[] tagArr) {
		if (tagArr == null) {
			tagArr = new String[0];
		}
		List<ContentTag> list = new ArrayList<ContentTag>();
		ContentTag bean;
		for (String t : tagArr) {
			bean = null;
			for (ContentTag tag : tags) {
				if (t.equalsIgnoreCase(tag.getName())) {
					bean = tag;
					break;
				}
			}
			if (bean == null) {
				bean = saveTag(t);
			}
			list.add(bean);
		}
		Set<ContentTag> toBeRemove = new HashSet<ContentTag>();
		boolean contains;
		for (ContentTag tag : tags) {
			contains = false;
			for (String t : tagArr) {
				if (t.equalsIgnoreCase(tag.getName())) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				toBeRemove.add(tag);
			}
		}
		tags.clear();
		tags.addAll(list);
		removeTags(toBeRemove);
		return tags;
		// // 先save
		// List<ContentTag> newTags = saveTags(tagArr);
		// // 再remove
		// Set<ContentTag> toRemove = new HashSet<ContentTag>();
		// for (ContentTag tag : tags) {
		// tag.setCount(tag.getCount() - 1);
		// if (tag.getCount() <= 0) {
		// toRemove.add(tag);
		// }
		// }
		// tags.clear();
		// if (newTags != null) {
		// tags.addAll(newTags);
		// }
		// for (ContentTag tag : toRemove) {
		// deleteById(tag.getId());
		// }
		// return tags;
	}

	@Override
	public void removeTags(Collection<ContentTag> tags) {
		Set<ContentTag> toRemove = new HashSet<ContentTag>();
		for (ContentTag tag : tags) {
			tag.setCount(tag.getCount() - 1);
			if (tag.getCount() <= 0) {
				toRemove.add(tag);
			}
		}
		for (ContentTag tag : toRemove) {
			// 由于事务真正删除关联的sql语句还没有执行，这个时候jc_contenttag里至少还有一条数据。
			if (getDao().countContentRef(tag.getId()) <= 1) {
				getDao().deleteById(tag.getId());
			} else {
				// 还有引用，不应该出现的情况，此时无法删除。
				log.warn("ContentTag ref to Content > 1," + " while ContentTag.ref_counter <= 0");
			}
		}
	}
	@Override
	public ContentTag save(ContentTag bean) {
		bean.init();
		getDao().save(bean);
		return bean;
	}

	@Override
	public ContentTag deleteById(Integer id) {
		getDao().deleteContentRef(id);
		ContentTag bean = getDao().deleteById(id);
		return bean;
	}

	@Override
	public ContentTag[] deleteByIds(Integer[] ids) {
		ContentTag[] beans = new ContentTag[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
}
