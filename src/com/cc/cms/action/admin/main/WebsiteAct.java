
package com.cc.cms.action.admin.main;

import static com.cc.cms.Constants.*;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

import com.cc.cms.action.*;
import com.cc.cms.entity.main.*;
import com.cc.cms.entity.main.Role;
import com.cc.cms.service.main.*;
import com.cc.common.util.*;

/** @author wangcheng */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Scope("prototype")
@Controller("web.action.admin.websiteAct")
public class WebsiteAct extends CmsAct {

	private static final long	serialVersionUID	= -2767716732443967777L;
	private static final Logger	log					= LoggerFactory.getLogger(WebsiteAct.class);
	@Autowired
	private ITemplateSvc		templateSvc;
	@Autowired
	private IRoleSvc			roleSvc;
	private Site				website;
	private List<Site>			parentList;
	private List<Template>		templateList;
	private String				domain;
	private List<Role>			roles				= new ArrayList<Role>(0);
	private List<Role>			roleList			= new ArrayList<Role>(0);
	private List<Site>			leftRoots;
	/** 查询条件 */
	private List<String>		property			= new ArrayList<String>();
	private List<Object>		value				= new ArrayList<Object>();

	@Override
	public String left() {
		Site root = new Site();
		root.setName("全部站点");
		leftRoots = websiteSvc.getRoots();
		root.setChild(new LinkedHashSet<Site>(leftRoots));
		setLeftMenu(root);
		setLeftMenu_url("website_edit");
		setLeftMenu_durl("website_list");
		return LEFT;
	}

	@Override
	public String list() {
		initList();
		return LIST;
	}

	public String search() {
		return list();
	}

	@Override
	public String add() {
		initAdd();
		if (id != null) {
			website = new Site();
			website.setParent(websiteSvc.findById(id));
		}
		return ADD;
	}

	public String save() {
		handleRoles();
		handleParent();
		website.setUser(getUser());
		websiteSvc.save(website);
		addUser(website);
		addTemplate(website);
		addActionMessage("成功添加站点 ");
		log.info("成功添加站点 :{}", website.getName());
		websiteSvc.handleWebsiteChange();
		setId(null);
		return redirect("website_list.jspa");
	}

	private void addUser(Site site) {
		User user = new User();
		String username = site.getDomain().substring(0, site.getDomain().indexOf("."));
		String password = "888888";
		user.setUsername(username);
		user.setRegisterTime(ComUtils.now());
		user.setAdmin(true);
		user.setRoles(new HashSet<Role>(roles));
		userSvc.saveAdmin(user, password, getIp(), site.getId(), 1, new Byte("2"), true);
	}

	private void addTemplate(Site site) {
		// 创建模板
		Template t = site.getTemplate();
		if (t != null && t.getId() != null) {
			site.setTemplate(templateSvc.findById(t.getId()));
		}
		String ctg = site.getDomain().substring(0, 1);
		String ctg2 = site.getDomain().substring(0, site.getDomain().indexOf("."));
		String otpl = TPL_BASE + SPT + site.getTemplate().getResPath();
		String ctpl = TPL_BASE + SPT + ctg + SPT + ctg2 + SPT + site.getTemplate().getResPath();
		otpl = contextPvd.getAppRealPath(otpl);
		ctpl = contextPvd.getAppRealPath(ctpl);
		try {
			FileUtils.copyDirectory(new File(otpl), new File(ctpl));
			log.debug("模板拷贝成功！");
		} catch (IOException e) {
			log.error("模板拷贝出错:", e);
		}
	}

	@Override
	public String edit() {
		website = websiteSvc.findById(id);
		initEdit();
		return EDIT;
	}

	public String update() {
		handleRoles();
		handleParent();
		websiteSvc.updateDefault(website);
		addActionMessage("成功更新站点 ");
		log.info("成功更新站点 :{}", website.getName());
		websiteSvc.handleWebsiteChange();
		setId(null);
		return redirect("website_list.jspa");
	}

	public String delete() {
		try {
			for (Integer webId : ids) {
				websiteSvc.delete(webId);
				// 删除用户
				userSvc.deleteBySite(webId);
				log.info("删除成功", webId);
			}
			websiteSvc.handleWebsiteChange();
			addActionMessage("成功删除站点 ");
		} catch (DataIntegrityViolationException e) {
			addActionError("记录已被引用，不能删除!");
			log.error("删除站点失败,记录被引用");
		}
		setId(null);
		return list();
	}

	public String disable() {
		website = websiteSvc.findById(id);
		website.setClose(true);
		websiteSvc.update(website);
		addActionMessage("成功关闭站点");
		websiteSvc.handleWebsiteChange();
		setId(null);
		return list();
	}

	public String enable() {
		website = websiteSvc.findById(id);
		website.setClose(false);
		websiteSvc.update(website);
		addActionMessage("成功开启站点");
		websiteSvc.handleWebsiteChange();
		setId(null);
		return list();
	}

	private void initAdd() {
		initRoleList();
		initParentList();
		templateList = templateSvc.findAll();
	}

	private void initEdit() {
		initRoleList();
		initParentList();
		templateList = templateSvc.findAll();
	}

	private void initList() {
		if (id != null) {
			// list = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(new
			// ArrayList<ISelectTree>(websiteSvc.getWebsite(id).getChild())));
			list = new ArrayList<Site>(websiteSvc.getWebsite(id).getChild());
		} else {
			property.add("domain");
			value.add(domain);
			if (isSearch(value)) {
				// list =
				// SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(websiteSvc.findByProperty(property,
				// value)));
				list = websiteSvc.findByProperty(property, value);
			} else {
				// list =
				// SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(websiteSvc.getAllWebsites()));
				list = websiteSvc.getRoots();
			}
		}
	}

	/** 角色列表， */
	private void initRoleList() {
		roleList = roleSvc.findAll();
	}

	private void handleRoles() {
		if (roles != null && roles.size() > 0) {
			website.setRoles(new HashSet<Role>(roles));
		}
	}

	private void initParentList() {
		parentList = SelectTreeUtils.webTree(SelectTreeUtils.handleTreeChild(websiteSvc.getAllWebsites()));
		parentList.add(0, null);
	}

	private void handleParent() {
		if (website.getParent() != null && website.getParent().getId() == null) {
			website.setParent(null);
		}
	}

	public boolean validateSave() {
		if (hasErrors()) {
			initAdd();
			log.error("发生action/field错误");
			return true;
		}
		if (vldDomain(website.getDomain(), null)) {
			initAdd();
			log.error("添加时域名错误");
			return true;
		}
		if (vldResPath(website.getResPath(), null)) {
			initAdd();
			log.error("添加时资源地址错误");
			return true;
		}
		return false;
	}

	public boolean validateUpdate() {
		if (hasErrors()) {
			initEdit();
			log.error("发生action/field错误");
			return true;
		}
		if (website.getId().equals(website.getParentId())) {
			initEdit();
			addActionError("不能将自身设置成父站点");
			log.error("不能将自身设置成父站点");
			return true;
		}
		if (vldExist(website.getId())) {
			initEdit();
			log.error("不存在要更新的站点");
			return true;
		}
		if (vldDomain(website.getDomain(), website.getId())) {
			initEdit();
			log.error("域名已经存在");
			return true;
		}
		if (vldResPath(website.getResPath(), website.getId())) {
			initEdit();
			log.error("资源地址已经存在");
			return true;
		}
		return false;
	}

	public boolean validateDelete() {
		if (hasErrors()) {
			log.error("发生action/field错误");
			return true;
		}
		if (vldBatch()) {
			log.error("删除ID不能为空");
			return true;
		}
		for (Integer id : ids) {
			if (vldExist(id)) {
				return true;
			}
		}
		return false;
	}

	private boolean vldExist(Integer id) {
		Site entity = websiteSvc.getWebsite(id);
		if (entity == null) {
			addActionError("记录不存在：" + id);
			log.error("记录不存在{}", id);
			return true;
		}
		return false;
	}

	private boolean vldDomain(String domain, Integer id) {
		if (id != null) {
			Site w = websiteSvc.getWebsite(id);
			if (w.getDomain().equals(domain)) {
				return false;
			}
		}
		if (!websiteSvc.checkDomain(domain)) {
			addActionError("域名已经存在：" + domain);
			log.error("域名已经存在：{}", domain);
			return true;
		}
		return false;
	}

	private boolean vldResPath(String resPath, Integer id) {
		if (id != null) {
			Site w = websiteSvc.getWebsite(id);
			if (w.getResPath().equals(resPath)) {
				return false;
			}
		}
		if (!websiteSvc.checkResPath(resPath)) {
			addActionError("资源路径已经存在：" + resPath);
			return true;
		}
		return false;
	}

	public Site getWebsite() {
		return website;
	}

	public void setWebsite(Site website) {
		this.website = website;
	}

	public List<Site> getParentList() {
		return parentList;
	}

	public void setParentList(List<Site> parentList) {
		this.parentList = parentList;
	}

	public List<Template> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<Template> templateList) {
		this.templateList = templateList;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Site> getLeftRoots() {
		return leftRoots;
	}

	public void setLeftRoots(List<Site> leftRoots) {
		this.leftRoots = leftRoots;
	}
}
