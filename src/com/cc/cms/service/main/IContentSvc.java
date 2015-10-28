
package com.cc.cms.service.main;

import java.util.List;
import java.util.Map;

import com.cc.cms.entity.main.Content;
import com.cc.cms.entity.main.ContentExt;
import com.cc.cms.entity.main.ContentTxt;
import com.cc.cms.entity.main.Group;
import com.cc.cms.entity.main.Topic;
import com.cc.cms.entity.main.User;
import com.cc.cms.service.ICmsSvc;
import com.cc.cms.staticpage.exception.ContentNotCheckedException;
import com.cc.cms.staticpage.exception.GeneratedZeroStaticPageException;
import com.cc.cms.staticpage.exception.StaticPageNotOpenException;
import com.cc.cms.staticpage.exception.TemplateNotFoundException;
import com.cc.cms.staticpage.exception.TemplateParseException;
import com.cc.common.page.Pagination;

/** @author wangcheng */
public interface IContentSvc extends ICmsSvc<Content> {

	/** 获得文章分页。供会员中心使用。
	 * 
	 * @param title 文章标题
	 * @param channelId 栏目ID
	 * @param siteId 站点ID
	 * @param memberId 会员ID
	 * @param pageNo 页码
	 * @param pageSize 每页大小
	 * @return 文章分页对象 */
	public Pagination getPageForMember(String title, Integer channelId, Integer siteId, Integer memberId, int pageNo, int pageSize);

	/** 根据内容ID数组获取文章列表
	 * 
	 * @param ids
	 * @param orderBy
	 * @return */
	public List<Content> getListByIdsForTag(Integer[] ids, int orderBy);

	public Content getSide(Integer id, Integer siteId, Integer channelId, boolean next);

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

	public Content save(Content bean, ContentExt ext, ContentTxt txt, List<Integer> channelIds, List<Topic> topics, List<Group> viewGroupIds,
			String[] tagArr, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
			User user, boolean forMember);

	public Content save(Content bean, ContentExt ext, ContentTxt txt, Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
			String[] tagArr, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
			Integer channelId, Integer typeId, Boolean draft, User user, boolean forMember);

	public Content update(Content bean, ContentExt ext, ContentTxt txt, List<Integer> channelIds, List<Topic> topics, List<Group> viewGroupIds,
			String[] tagArr, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
			User user, boolean forMember);

	public Content update(Content bean, ContentExt ext, ContentTxt txt, String[] tagArr, Integer[] channelIds, Integer[] topicIds,
			Integer[] viewGroupIds, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths,
			String[] picDescs, Map<String, String> attr, Integer channelId, Integer typeId, Boolean draft, User user, boolean forMember);

	public Content check(Integer id, User user);

	public Content[] check(Integer[] ids, User user);

	public Content reject(Integer id, User user, Byte step, String opinion);

	public Content[] reject(Integer[] ids, User user, Byte step, String opinion);

	public Content cycle(Integer id);

	public Content[] cycle(Integer[] ids);

	public Content recycle(Integer id);

	public Content[] recycle(Integer[] ids);

	public Content deleteById(Integer id);

	public Content[] deleteByIds(Integer[] ids);

	public Content[] contentStatic(Integer[] ids) throws TemplateNotFoundException, TemplateParseException, GeneratedZeroStaticPageException,
			StaticPageNotOpenException, ContentNotCheckedException;

	public Pagination getPageForCollection(Integer siteId, Integer memberId, int pageNo, int pageSize);

	public Pagination getRightContent(int pageNo, int pageSize, Integer chnlId, List<String> property, List<String> ops, List<Object> value, int order);

	public Pagination getRightContent(int pageNo, int pageSize, Integer chnlId, Boolean selfChannel, List<String> property, List<String> ops,
			List<Object> value, int order);

	/** 排序 */
	public Content up(Integer id);

	public Content down(Integer id);

	public Content getPrev(Content bean);

	public Content getNext(Content bean);

	/** 检测文章标题是否重复 如果没有返回true,即标题可以使用；如果有重复返回false，标题不可使使用。 */
	public Boolean checkTitle(String title, Content bean);

}
