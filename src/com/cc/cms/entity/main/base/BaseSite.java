
package com.cc.cms.entity.main.base;

import java.io.*;

/**
 * This is an object that contains data related to the cms_site table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="cms_site"
 */
public abstract class BaseSite implements Serializable {

	private static final long	serialVersionUID		= 8685379823268511636L;
	public static String		REF						= "Site";
	public static String		PROP_INDEX_TO_ROOT		= "indexToRoot";
	public static String		PROP_DOMAIN				= "domain";
	public static String		PROP_PROTOCOL			= "protocol";
	public static String		PROP_LOCALE_ADMIN		= "localeAdmin";
	public static String		PROP_DOMAIN_REDIRECT	= "domainRedirect";
	public static String		PROP_UPLOAD_FTP			= "uploadFtp";
	public static String		PROP_RESYCLE_ON			= "resycleOn";
	public static String		PROP_TPL_SOLUTION		= "tplSolution";
	public static String		PROP_STATIC_SUFFIX		= "staticSuffix";
	public static String		PROP_CONFIG				= "config";
	public static String		PROP_STATIC_INDEX		= "staticIndex";
	public static String		PROP_DYNAMIC_SUFFIX		= "dynamicSuffix";
	public static String		PROP_FINAL_STEP			= "finalStep";
	public static String		PROP_SHORT_NAME			= "shortName";
	public static String		PROP_STATIC_DIR			= "staticDir";
	public static String		PROP_DOMAIN_ALIAS		= "domainAlias";
	public static String		PROP_PATH				= "path";
	public static String		PROP_AFTER_CHECK		= "afterCheck";
	public static String		PROP_LOCALE_FRONT		= "localeFront";
	public static String		PROP_NAME				= "name";
	public static String		PROP_ID					= "id";
	public static String		PROP_RELATIVE_PATH		= "relativePath";
	public static String		PROP_RES_PATH			= "resPath";
	public static String		PROP_LFT				= "left";
	public static String		PROP_RGT				= "right";
	public static String		PROP_CREATTIME			= "createTime";
	public static String		PROP_CLOSE				= "close";
	public static String		PROP_RANK				= "rank";

	// constructors
	public BaseSite() {
		initialize();
	}

	/** Constructor for primary key */
	public BaseSite(java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/** Constructor for required fields */
	public BaseSite(java.lang.Integer id, com.cc.cms.entity.main.Config config, java.lang.String domain, java.lang.String name, java.lang.String protocol,
			java.lang.String dynamicSuffix, java.lang.String staticSuffix, java.lang.Boolean indexToRoot, java.lang.Boolean staticIndex,
			java.lang.String localeAdmin, java.lang.String localeFront, java.lang.Byte finalStep, java.lang.Byte afterCheck, java.lang.Boolean relativePath,
			java.lang.Boolean resycleOn, java.lang.Boolean close, java.lang.Integer rank, java.lang.Integer left, java.lang.Integer right,
			java.lang.String resPath, java.util.Date createTime) {
		this.setId(id);
		this.setConfig(config);
		this.setDomain(domain);
		this.setName(name);
		this.setProtocol(protocol);
		this.setDynamicSuffix(dynamicSuffix);
		this.setStaticSuffix(staticSuffix);
		this.setIndexToRoot(indexToRoot);
		this.setStaticIndex(staticIndex);
		this.setLocaleAdmin(localeAdmin);
		this.setLocaleFront(localeFront);
		this.setFinalStep(finalStep);
		this.setAfterCheck(afterCheck);
		this.setRelativePath(relativePath);
		this.setResycleOn(resycleOn);
		this.setClose(close);
		this.setRank(rank);
		this.setLft(left);
		this.setRgt(right);
		this.setResPath(resPath);
		this.setCreateTime(createTime);
		initialize();
	}

	protected void initialize() {}

	private int													hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.Integer									id;
	// fields
	private java.lang.String									domain;
	private java.lang.String									name;
	private java.lang.String									shortName;
	private java.lang.String									protocol;
	private java.lang.String									dynamicSuffix;
	private java.lang.String									staticSuffix;
	private java.lang.String									staticDir;
	private java.lang.Boolean									indexToRoot;
	private java.lang.Boolean									staticIndex;
	private java.lang.String									localeAdmin;
	private java.lang.String									localeFront;
	private java.lang.Byte										finalStep;
	private java.lang.Byte										afterCheck;
	private java.lang.Boolean									relativePath;
	private java.lang.Boolean									resycleOn;
	private java.lang.String									domainAlias;
	private java.lang.String									domainRedirect;
	private java.lang.String									cookieKey;
	private java.lang.Boolean									close;
	private java.lang.Integer									rank;
	private java.lang.Integer									lft;
	private java.lang.Integer									rgt;
	private java.lang.Integer									priority;
	private java.lang.String									resPath;
	private java.lang.String									copyright;
	private java.lang.String									recordCode;
	private java.lang.String									ownerName;
	private java.lang.String									ownerEmail;
	private java.lang.String									ownerTel;
	private java.lang.String									ownerMobile;
	private java.util.Date										createTime;
	// many to one
	private com.cc.core.entity.Ftp								uploadFtp;
	private com.cc.cms.entity.main.Config						config;
	private com.cc.cms.entity.main.Site							parent;
	private com.cc.cms.entity.main.User							user;
	private com.cc.cms.entity.main.Area							area;
	private com.cc.cms.entity.main.Template						template;
	// set
	private java.util.Set<com.cc.cms.entity.main.Site>			child;
	private java.util.Set<com.cc.cms.entity.main.Role>			roles;
	// collections
	private java.util.Map<java.lang.String, java.lang.String>	attr;
	private java.util.Map<java.lang.String, java.lang.String>	txt;
	private java.util.Map<java.lang.String, java.lang.String>	cfg;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="identity" column="site_id"
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/** Return the value associated with the column: domain */
	public java.lang.String getDomain() {
		return domain;
	}

	/**
	 * Set the value related to the column: domain
	 * 
	 * @param domain
	 *            the domain value
	 */
	public void setDomain(java.lang.String domain) {
		this.domain = domain;
	}

	/** Return the value associated with the column: site_name */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Set the value related to the column: site_name
	 * 
	 * @param name
	 *            the site_name value
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/** Return the value associated with the column: short_name */
	public java.lang.String getShortName() {
		return shortName;
	}

	/**
	 * Set the value related to the column: short_name
	 * 
	 * @param shortName
	 *            the short_name value
	 */
	public void setShortName(java.lang.String shortName) {
		this.shortName = shortName;
	}

	/** Return the value associated with the column: protocol */
	public java.lang.String getProtocol() {
		return protocol;
	}

	/**
	 * Set the value related to the column: protocol
	 * 
	 * @param protocol
	 *            the protocol value
	 */
	public void setProtocol(java.lang.String protocol) {
		this.protocol = protocol;
	}

	/** Return the value associated with the column: dynamic_suffix */
	public java.lang.String getDynamicSuffix() {
		return dynamicSuffix;
	}

	/**
	 * Set the value related to the column: dynamic_suffix
	 * 
	 * @param dynamicSuffix
	 *            the dynamic_suffix value
	 */
	public void setDynamicSuffix(java.lang.String dynamicSuffix) {
		this.dynamicSuffix = dynamicSuffix;
	}

	/** Return the value associated with the column: static_suffix */
	public java.lang.String getStaticSuffix() {
		return staticSuffix;
	}

	/**
	 * Set the value related to the column: static_suffix
	 * 
	 * @param staticSuffix
	 *            the static_suffix value
	 */
	public void setStaticSuffix(java.lang.String staticSuffix) {
		this.staticSuffix = staticSuffix;
	}

	/** Return the value associated with the column: static_dir */
	public java.lang.String getStaticDir() {
		return staticDir;
	}

	/**
	 * Set the value related to the column: static_dir
	 * 
	 * @param staticDir
	 *            the static_dir value
	 */
	public void setStaticDir(java.lang.String staticDir) {
		this.staticDir = staticDir;
	}

	/** Return the value associated with the column: is_index_to_root */
	public java.lang.Boolean getIndexToRoot() {
		return indexToRoot;
	}

	/**
	 * Set the value related to the column: is_index_to_root
	 * 
	 * @param indexToRoot
	 *            the is_index_to_root value
	 */
	public void setIndexToRoot(java.lang.Boolean indexToRoot) {
		this.indexToRoot = indexToRoot;
	}

	/** Return the value associated with the column: is_static_index */
	public java.lang.Boolean getStaticIndex() {
		return staticIndex;
	}

	/**
	 * Set the value related to the column: is_static_index
	 * 
	 * @param staticIndex
	 *            the is_static_index value
	 */
	public void setStaticIndex(java.lang.Boolean staticIndex) {
		this.staticIndex = staticIndex;
	}

	/** Return the value associated with the column: locale_admin */
	public java.lang.String getLocaleAdmin() {
		return localeAdmin;
	}

	/**
	 * Set the value related to the column: locale_admin
	 * 
	 * @param localeAdmin
	 *            the locale_admin value
	 */
	public void setLocaleAdmin(java.lang.String localeAdmin) {
		this.localeAdmin = localeAdmin;
	}

	/** Return the value associated with the column: locale_front */
	public java.lang.String getLocaleFront() {
		return localeFront;
	}

	/**
	 * Set the value related to the column: locale_front
	 * 
	 * @param localeFront
	 *            the locale_front value
	 */
	public void setLocaleFront(java.lang.String localeFront) {
		this.localeFront = localeFront;
	}

	public com.cc.cms.entity.main.Template getTemplate() {
		return template;
	}

	public void setTemplate(com.cc.cms.entity.main.Template template) {
		this.template = template;
	}

	/** Return the value associated with the column: final_step */
	public java.lang.Byte getFinalStep() {
		return finalStep;
	}

	/**
	 * Set the value related to the column: final_step
	 * 
	 * @param finalStep
	 *            the final_step value
	 */
	public void setFinalStep(java.lang.Byte finalStep) {
		this.finalStep = finalStep;
	}

	/** Return the value associated with the column: after_check */
	public java.lang.Byte getAfterCheck() {
		return afterCheck;
	}

	/**
	 * Set the value related to the column: after_check
	 * 
	 * @param afterCheck
	 *            the after_check value
	 */
	public void setAfterCheck(java.lang.Byte afterCheck) {
		this.afterCheck = afterCheck;
	}

	/** Return the value associated with the column: is_relative_path */
	public java.lang.Boolean getRelativePath() {
		return relativePath;
	}

	/**
	 * Set the value related to the column: is_relative_path
	 * 
	 * @param relativePath
	 *            the is_relative_path value
	 */
	public void setRelativePath(java.lang.Boolean relativePath) {
		this.relativePath = relativePath;
	}

	/** Return the value associated with the column: is_recycle_on */
	public java.lang.Boolean getResycleOn() {
		return resycleOn;
	}

	/**
	 * Set the value related to the column: is_recycle_on
	 * 
	 * @param resycleOn
	 *            the is_recycle_on value
	 */
	public void setResycleOn(java.lang.Boolean resycleOn) {
		this.resycleOn = resycleOn;
	}

	/** Return the value associated with the column: domain_alias */
	public java.lang.String getDomainAlias() {
		return domainAlias;
	}

	/**
	 * Set the value related to the column: domain_alias
	 * 
	 * @param domainAlias
	 *            the domain_alias value
	 */
	public void setDomainAlias(java.lang.String domainAlias) {
		this.domainAlias = domainAlias;
	}

	/** Return the value associated with the column: domain_redirect */
	public java.lang.String getDomainRedirect() {
		return domainRedirect;
	}

	/**
	 * Set the value related to the column: domain_redirect
	 * 
	 * @param domainRedirect
	 *            the domain_redirect value
	 */
	public void setDomainRedirect(java.lang.String domainRedirect) {
		this.domainRedirect = domainRedirect;
	}

	public java.lang.String getCookieKey() {
		return cookieKey;
	}

	public void setCookieKey(java.lang.String cookieKey) {
		this.cookieKey = cookieKey;
	}

	public java.lang.Boolean getClose() {
		return close;
	}

	public void setClose(java.lang.Boolean close) {
		this.close = close;
	}

	public java.lang.Integer getRank() {
		return rank;
	}

	public void setRank(java.lang.Integer rank) {
		this.rank = rank;
	}

	public java.lang.Integer getLft() {
		return lft;
	}

	public void setLft(java.lang.Integer lft) {
		this.lft = lft;
	}

	public java.lang.Integer getRgt() {
		return rgt;
	}

	public void setRgt(java.lang.Integer rgt) {
		this.rgt = rgt;
	}

	public java.lang.Integer getPriority() {
		return priority;
	}

	public void setPriority(java.lang.Integer priority) {
		this.priority = priority;
	}

	public java.lang.String getResPath() {
		return resPath;
	}

	public void setResPath(java.lang.String resPath) {
		this.resPath = resPath;
	}

	public java.lang.String getCopyright() {
		return copyright;
	}

	public void setCopyright(java.lang.String copyright) {
		this.copyright = copyright;
	}

	public java.lang.String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(java.lang.String recordCode) {
		this.recordCode = recordCode;
	}

	public java.lang.String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(java.lang.String ownerName) {
		this.ownerName = ownerName;
	}

	public java.lang.String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(java.lang.String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public java.lang.String getOwnerTel() {
		return ownerTel;
	}

	public void setOwnerTel(java.lang.String ownerTel) {
		this.ownerTel = ownerTel;
	}

	public java.lang.String getOwnerMobile() {
		return ownerMobile;
	}

	public void setOwnerMobile(java.lang.String ownerMobile) {
		this.ownerMobile = ownerMobile;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public com.cc.cms.entity.main.Site getParent() {
		return parent;
	}

	public void setParent(com.cc.cms.entity.main.Site parent) {
		this.parent = parent;
	}

	public com.cc.cms.entity.main.User getUser() {
		return user;
	}

	public void setUser(com.cc.cms.entity.main.User user) {
		this.user = user;
	}

	public com.cc.cms.entity.main.Area getArea() {
		return area;
	}

	public void setArea(com.cc.cms.entity.main.Area area) {
		this.area = area;
	}

	/** Return the value associated with the column: ftp_upload_id */
	public com.cc.core.entity.Ftp getUploadFtp() {
		return uploadFtp;
	}

	/**
	 * Set the value related to the column: ftp_upload_id
	 * 
	 * @param uploadFtp
	 *            the ftp_upload_id value
	 */
	public void setUploadFtp(com.cc.core.entity.Ftp uploadFtp) {
		this.uploadFtp = uploadFtp;
	}

	/** Return the value associated with the column: config_id */
	public com.cc.cms.entity.main.Config getConfig() {
		return config;
	}

	/**
	 * Set the value related to the column: config_id
	 * 
	 * @param config
	 *            the config_id value
	 */
	public void setConfig(com.cc.cms.entity.main.Config config) {
		this.config = config;
	}

	/** Return the value associated with the column: attr */
	public java.util.Map<java.lang.String, java.lang.String> getAttr() {
		return attr;
	}

	/**
	 * Set the value related to the column: attr
	 * 
	 * @param attr
	 *            the attr value
	 */
	public void setAttr(java.util.Map<java.lang.String, java.lang.String> attr) {
		this.attr = attr;
	}

	/** Return the value associated with the column: txt */
	public java.util.Map<java.lang.String, java.lang.String> getTxt() {
		return txt;
	}

	/**
	 * Set the value related to the column: txt
	 * 
	 * @param txt
	 *            the txt value
	 */
	public void setTxt(java.util.Map<java.lang.String, java.lang.String> txt) {
		this.txt = txt;
	}

	/** Return the value associated with the column: cfg */
	public java.util.Map<java.lang.String, java.lang.String> getCfg() {
		return cfg;
	}

	public java.util.Set<com.cc.cms.entity.main.Site> getChild() {
		return child;
	}

	public void setChild(java.util.Set<com.cc.cms.entity.main.Site> child) {
		this.child = child;
	}

	public java.util.Set<com.cc.cms.entity.main.Role> getRoles() {
		return roles;
	}

	public void setRoles(java.util.Set<com.cc.cms.entity.main.Role> roles) {
		this.roles = roles;
	}

	/**
	 * Set the value related to the column: cfg
	 * 
	 * @param cfg
	 *            the cfg value
	 */
	public void setCfg(java.util.Map<java.lang.String, java.lang.String> cfg) {
		this.cfg = cfg;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.cms.entity.main.Site))
			return false;
		else {
			com.cc.cms.entity.main.Site site = (com.cc.cms.entity.main.Site) obj;
			if (null == this.getId() || null == site.getId())
				return false;
			else
				return (this.getId().equals(site.getId()));
		}
	}

	@Override
	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}