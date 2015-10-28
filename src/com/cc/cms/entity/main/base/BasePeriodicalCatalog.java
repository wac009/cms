
package com.cc.cms.entity.main.base;

import com.cc.cms.entity.main.*;

public class BasePeriodicalCatalog implements java.io.Serializable {

	private static final long	serialVersionUID	= -3060324967457506119L;
	// Fields
	private Integer				id;
	private Periodical			periodical;
	private Publication			publication;
	private Site				website;
	private String				name;
	private String				description;
	private Integer				priority;
	private Boolean				hasContent;
	private Boolean				common;
	private Boolean				disabled;

	// Constructors
	/** default constructor */
	public BasePeriodicalCatalog() {}

	/** id constructor */
	public BasePeriodicalCatalog(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BasePeriodicalCatalog(Periodical periodical, Publication publication, Site website, String name, String description, Integer priority,
			Boolean hasContent, Boolean isCommon, Boolean isDisabled) {
		this.periodical = periodical;
		this.publication = publication;
		this.website = website;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.hasContent = hasContent;
		this.common = isCommon;
		this.disabled = isDisabled;
	}

	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Periodical getPeriodical() {
		return periodical;
	}

	public void setPeriodical(Periodical periodical) {
		this.periodical = periodical;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getCommon() {
		return common;
	}

	public void setCommon(Boolean isCommon) {
		this.common = isCommon;
	}

	public Boolean getDisabled() {
		return disabled;
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

	public Boolean getHasContent() {
		return hasContent;
	}

	public void setHasContent(Boolean hasContent) {
		this.hasContent = hasContent;
	}
}
