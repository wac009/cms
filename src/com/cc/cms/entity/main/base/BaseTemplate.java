
package com.cc.cms.entity.main.base;

import java.util.*;

/**
 * BaseTempleteSolution entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class BaseTemplate implements java.io.Serializable {

	// Fields
	private Integer	id;
	private String	name;
	private String	resPath;
	private String	imgPath;
	private String	version;
	private String	author;
	private String	description;
	private Integer	priority;
	private Boolean	isDisabled;
	private Date	createTime;

	// Constructors
	/** default constructor */
	public BaseTemplate() {}

	/** id constructor */
	public BaseTemplate(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BaseTemplate(String name, String resPath, String imgPath, String version, String author, String description, Integer priority,
			Boolean isDisabled, Date createTime) {
		this.name = name;
		this.resPath = resPath;
		this.imgPath = imgPath;
		this.version = version;
		this.author = author;
		this.description = description;
		this.priority = priority;
		this.isDisabled = isDisabled;
		this.createTime = createTime;
	}

	// Property accessors
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResPath() {
		return resPath;
	}

	public void setResPath(String resPath) {
		this.resPath = resPath;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
