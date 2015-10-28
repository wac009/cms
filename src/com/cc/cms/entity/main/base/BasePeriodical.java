
package com.cc.cms.entity.main.base;

import java.util.*;

import com.cc.cms.entity.main.*;

public class BasePeriodical implements java.io.Serializable {

	private static final long		serialVersionUID	= 3506433154792950924L;
	// Fields
	private Integer					id;
	private String					year;
	private Integer					yearPeriod;
	private Integer					totalPeriod;
	private String					imgPath;
	private String					description;
	private Boolean					disabled;
	private Boolean					current;
	private Boolean					lock;
	private Date					addTime;
	private Integer					priority;
	// many to one
	private Publication				publication;
	private PeriodicalAttachment	attachment;
	// collection
	private Set<PeriodicalCatalog>	catalogs;

	// private Set attachments;
	// Constructors
	/** default constructor */
	public BasePeriodical() {}

	/** id constructor */
	public BasePeriodical(Integer id) {
		this.id = id;
	}

	/** minimal constructor */
	public BasePeriodical(Publication publication) {
		this.publication = publication;
	}

	/** full constructor */
	public BasePeriodical(Publication publication, String year, Integer yearPeriod, Integer totalPeriod, String imgPath, String description,
			Boolean isDisabled, Boolean isCurrent, Boolean isLock, Date addTime, Integer priority) {
		this.publication = publication;
		this.year = year;
		this.yearPeriod = yearPeriod;
		this.totalPeriod = totalPeriod;
		this.imgPath = imgPath;
		this.description = description;
		this.disabled = isDisabled;
		this.current = isCurrent;
		this.lock = isLock;
		this.addTime = addTime;
		this.priority = priority;
	}

	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public PeriodicalAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(PeriodicalAttachment attachment) {
		this.attachment = attachment;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getYearPeriod() {
		return yearPeriod;
	}

	public void setYearPeriod(Integer yearPeriod) {
		this.yearPeriod = yearPeriod;
	}

	public Integer getTotalPeriod() {
		return totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean isDisabled) {
		this.disabled = isDisabled;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean isCurrent) {
		this.current = isCurrent;
	}

	public Boolean getLock() {
		return lock;
	}

	public void setLock(Boolean isLock) {
		this.lock = isLock;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Set<PeriodicalCatalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Set<PeriodicalCatalog> catalogs) {
		this.catalogs = catalogs;
	}
}
