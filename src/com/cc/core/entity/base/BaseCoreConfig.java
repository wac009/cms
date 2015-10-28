
package com.cc.core.entity.base;

import java.io.Serializable;

/**
 * This is an object that contains data related to the jo_config table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 * 
 * @hibernate.class table="jo_config"
 */
public abstract class BaseCoreConfig implements Serializable {

	private static final long	serialVersionUID	= -4091900046213790361L;
	public static String		REF					= "CoreConfig";
	public static String		PROP_VALUE			= "value";
	public static String		PROP_ID				= "id";

	// constructors
	public BaseCoreConfig() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCoreConfig(java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {}

	private int					hashCode	= Integer.MIN_VALUE;
	// primary key
	private java.lang.String	id;
	// fields
	private java.lang.String	value;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="cfg_key"
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: cfg_value
	 */
	public java.lang.String getValue() {
		return value;
	}

	/**
	 * Set the value related to the column: cfg_value
	 * 
	 * @param value
	 *            the cfg_value value
	 */
	public void setValue(java.lang.String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.cc.core.entity.CoreConfig))
			return false;
		else {
			com.cc.core.entity.CoreConfig coreConfig = (com.cc.core.entity.CoreConfig) obj;
			if (null == this.getId() || null == coreConfig.getId())
				return false;
			else
				return (this.getId().equals(coreConfig.getId()));
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