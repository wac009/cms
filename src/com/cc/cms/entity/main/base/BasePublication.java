
package com.cc.cms.entity.main.base;

import java.util.*;

import com.cc.cms.entity.main.*;

@SuppressWarnings({ "rawtypes" })
public class BasePublication implements java.io.Serializable {

	private static final long	serialVersionUID	= 8433322348496099641L;
	// Fields
	private Integer				id;
	private PublicationType		type;
	private Site				website;
	private String				name;
	private String				enName;
	private String				path;
	private String				description;
	private String				imgPath;
	private Integer				priority;
	private Date				createTime;
	private Date				addTime;
	private Boolean				disabled;
	private Set					periodicals;

	// Constructors
	/** default constructor */
	public BasePublication() {}

	/** id constructor */
	public BasePublication(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BasePublication(PublicationType type, Site website, String name, String enName, String description, String imgPath, Integer priority,
			Date createTime, Date addTime, Boolean isDisabled, Set periodicals) {
		this.type = type;
		this.website = website;
		this.name = name;
		this.enName = enName;
		this.description = description;
		this.imgPath = imgPath;
		this.priority = priority;
		this.createTime = createTime;
		this.addTime = addTime;
		this.disabled = isDisabled;
		this.periodicals = periodicals;
	}

	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PublicationType getType() {
		return type;
	}

	public void setType(PublicationType type) {
		this.type = type;
	}

	public Site getWebsite() {
		return website;
	}

	public void setWebsite(Site website) {
		this.website = website;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean isDisabled) {
		this.disabled = isDisabled;
	}

	public Set getPeriodicals() {
		return periodicals;
	}

	public void setPeriodicals(Set periodicals) {
		this.periodicals = periodicals;
	}
}
