
package com.cc.cms.entity.main.base;

import com.cc.cms.entity.main.*;

public class BasePublicationType implements java.io.Serializable {

	private static final long	serialVersionUID	= -2655565340717886190L;
	// Fields
	private Integer				id;
	private String				name;
	private String				description;
	private Integer				priority;
	private Boolean				disabled;
	private Site				website;

	// Constructors
	/** default constructor */
	public BasePublicationType() {}

	/** full constructor */
	public BasePublicationType(String name, String description, Integer priority, Boolean isDisabled, Site website) {
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.disabled = isDisabled;
		this.website = website;
	}

	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean isDisabled) {
		this.disabled = isDisabled;
	}

	public Site getWebsite() {
		return website;
	}

	public void setWebsite(Site website) {
		this.website = website;
	}
}
