
package com.cc.cms.dao.main;

import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.Content.ContentStatus;
import com.cc.common.page.Pagination;

/** @author wangcheng */
public interface IContentDao extends ICmsDao<Content> {

	/** 获得有权限的文章
	 * 
	 * @param webId
	 * @param chnlId
	 * @param adminId
	 * @param inputAdminId
	 * @param contentCtgId
	 * @param disabled
	 * @param topTime
	 * @param topLevel
	 * @param status
	 * @param title
	 * @param order
	 * @param pageNo
	 * @param pageSize
	 * @return */
	public Pagination getRightContent(int pageNo, int pageSize, Integer chnlId, List<String> property, List<String> ops, List<Object> value, int order);
	public Pagination getRightContent(int pageNo, int pageSize, Integer chnlId,Boolean selfChannel, List<String> property, List<String> ops, List<Object> value, int order);

	/** 获得内容列表
	 * 
	 * @param title 标题。支持模糊搜索，可以为null。
	 * @param typeId 内容类型ID。可以为null。
	 * @param inputUserId 内容录入员。可以为null。
	 * @param topLevel 是否固顶。
	 * @param recommend 是否推荐。
	 * @param status 状态。
	 * @param checkStep 审核步骤。当状态为prepared、passed、rejected时，不能为null。
	 * @param siteId 站点ID。可以为null。
	 * @param channelId 栏目ID。可以为null。
	 * @param orderBy 排序方式
	 * @param pageNo
	 * @param pageSize
	 * @return */
	public Pagination getPage(String title, Integer typeId, Integer inputUserId, boolean topLevel, boolean recommend, ContentStatus status,
			Byte checkStep, Integer siteId, Integer channelId, int orderBy, int pageNo, int pageSize);

	/** 获得自己发布的内容列表
	 * 
	 * @param title 标题。支持模糊搜索，可以为null。
	 * @param typeId 内容类型ID。可以为null。
	 * @param inputUserId 内容录入员。可以为null。
	 * @param topLevel 是否固顶。
	 * @param recommend 是否推荐。
	 * @param status 状态。
	 * @param checkStep 审核步骤。当状态为prepared、passed、rejected时，不能为null。
	 * @param siteId 站点ID。可以为null。
	 * @param channelId 站点ID。可以为null。
	 * @param userId 用户ID
	 * @param orderBy 排序方式
	 * @param pageNo
	 * @param pageSize
	 * @return */
	public Pagination getPageBySelf(String title, Integer typeId, Integer inputUserId, boolean topLevel, boolean recommend, ContentStatus status,
			Byte checkStep, Integer siteId, Integer channelId, Integer userId, int orderBy, int pageNo, int pageSize);

	/** 获得有权限的内容列表
	 * 
	 * @param title 标题。支持模糊搜索，可以为null。
	 * @param typeId 内容类型ID。可以为null。
	 * @param inputUserId 内容录入员。可以为null。
	 * @param topLevel 是否固顶。
	 * @param recommend 是否推荐。
	 * @param status 状态。
	 * @param checkStep 审核步骤。当状态为prepared、passed、rejected时，不能为null。
	 * @param siteId 站点ID。可以为null。
	 * @param channelId 栏目ID。可以为null。
	 * @param userId 用户ID。
	 * @param selfData 是否只获取自己发表的数据。
	 * @param orderBy 排序方式
	 * @param pageNo
	 * @param pageSize
	 * @return */
	public Pagination getPageByRight(String title, Integer typeId, Integer inputUserId, boolean topLevel, boolean recommend, ContentStatus status,
			Byte checkStep, Integer siteId, Integer channelId, Integer userId, boolean selfData, int orderBy, int pageNo, int pageSize);

	/** 获得一篇内容的上一篇或下一篇内容
	 * 
	 * @param id 文章ID。
	 * @param siteId 站点ID。可以为null。
	 * @param channelId 栏目ID。可以为null。
	 * @param next 根据文章ID，大者为下一篇，小者为上一篇。true：下一篇；fasle：上一篇。
	 * @param cacheable 是否使用缓存。
	 * @return */
	public Content getSide(Integer id, Integer siteId, Integer channelId, boolean next, boolean cacheable);

	/** 根据内容ID数组获取内容列表
	 * 
	 * @param ids
	 * @param orderBy
	 * @return */
	public List<Content> getListByIdsForTag(Integer[] ids, int orderBy);

	public Pagination getPageBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, int orderBy,
			int pageNo, int pageSize);

	public List<Content> getListBySiteIdsForTag(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, int orderBy,
			Integer first, Integer count);

	public Pagination getPageByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
			int orderBy, int option, int pageNo, int pageSize);

	public List<Content> getListByChannelIdsForTag(Integer[] channelIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
			int orderBy, int option, Integer first, Integer count);

	public Pagination getPageByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int pageNo, int pageSize);

	public List<Content> getListByChannelPathsForTag(String[] paths, Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, Integer first, Integer count);

	public Pagination getPageByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title, int orderBy, int pageNo, int pageSize);

	public List<Content> getListByTopicIdForTag(Integer topicId, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title, int orderBy, Integer first, Integer count);

	public Pagination getPageByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId,
			Boolean titleImg, Boolean recommend, String title, int orderBy, int pageNo, int pageSize);

	public List<Content> getListByTagIdsForTag(Integer[] tagIds, Integer[] siteIds, Integer[] channelIds, Integer[] typeIds, Integer excludeId,
			Boolean titleImg, Boolean recommend, String title, int orderBy, Integer first, Integer count);

	public Pagination getPageForCollection(Integer siteId, Integer memberId, int pageNo, int pageSize);

	public int countByChannelId(int channelId);

	public Integer getMaxPriority();

	public Content getPrev(Content bean);

	public Content getNext(Content bean);
	
}
