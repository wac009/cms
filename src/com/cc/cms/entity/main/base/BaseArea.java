
package com.cc.cms.entity.main.base;

import com.cc.cms.entity.main.*;

/**
 * BaseArea entity. @author MyEclipse Persistence Tools
 */
public class BaseArea implements java.io.Serializable {

	private static final long	serialVersionUID	= 8720622326042355733L;
	// Fields
	private Integer				id;
	private String				name;
	private String				full;
	private int					rank;
	private int					underlingflag;
	private Integer				priority;
	private Site				site;
	private Area				parent;

	// Constructors
	/** default constructor */
	public BaseArea() {}

	/** full constructor */
	public BaseArea(String name, Area parent, Integer priority, Site site) {
		this.name = name;
		this.priority = priority;
		this.site = site;
		this.parent = parent;
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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getFull() {
		return full;
	}

	public void setFull(String full) {
		this.full = full;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getUnderlingflag() {
		return underlingflag;
	}

	public void setUnderlingflag(int underlingflag) {
		this.underlingflag = underlingflag;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}
}