package com.cc.common.orm.hibernate3;

import org.hibernate.usertype.UserType;
import org.hibernate.HibernateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.Serializable;
import java.sql.Types;
import java.util.List;

import org.hibernate.Hibernate;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SplitStringList implements UserType {
	private static final String	SPLITTER	= ",";
	private static final int[]	TYPES		= new int[] { Types.VARCHAR };

	public SplitStringList() {}
	/**
	 * sqlTypes
	 * 
	 * @return int[]
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public int[] sqlTypes() {
		return TYPES;
	}
	/**
	 * returnedClass
	 * 
	 * @return Class
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public Class returnedClass() {
		return List.class;
	}
	/**
	 * equals
	 * 
	 * @param object
	 *        Object
	 * @param object1
	 *        Object
	 * @return boolean
	 * @throws HibernateException
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public boolean equals(Object object, Object object1) throws HibernateException {
		if (object == object1) {
			return true;
		}
		if (object != null && object1 != null) {
			List set0 = (List) object;
			List set1 = (List) object1;
			if (set0.size() != set1.size()) {
				return false;
			}
			Object[] s0 = set0.toArray();
			Object[] s1 = set1.toArray();
			if (s0.length != s1.length) {
				return false;
			}
			for (int i = 0; i < s0.length; i++) {
				String id0 = (String) s0[i];
				String id1 = (String) s1[i];
				if (!id0.equals(id1)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	/**
	 * hashCode
	 * 
	 * @param object
	 *        Object
	 * @return int
	 * @throws HibernateException
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public int hashCode(Object object) throws HibernateException {
		List s = (List) object;
		return s.hashCode();
	}
	/**
	 * nullSafeGet
	 * 
	 * @param resultSet
	 *        ResultSet
	 * @param stringArray
	 *        String[]
	 * @param object
	 *        Object
	 * @return Object
	 * @throws HibernateException
	 * @throws SQLException
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] stringArray, Object object) throws HibernateException, SQLException {
		String value = (String) Hibernate.STRING.nullSafeGet(resultSet, stringArray[0]);
		if (value != null) {
			return parse(value);
		} else {
			return new ArrayList();
		}
	}
	/**
	 * nullSafeSet
	 * 
	 * @param preparedStatement
	 *        PreparedStatement
	 * @param object
	 *        Object
	 * @param _int
	 *        int
	 * @throws HibernateException
	 * @throws SQLException
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object object, int _int) throws HibernateException, SQLException {
		if (object != null) {
			String str = assemble((List) object);
			Hibernate.STRING.nullSafeSet(preparedStatement, str, _int);
		} else {
			Hibernate.STRING.nullSafeSet(preparedStatement, "", _int);
		}
	}
	/**
	 * deepCopy
	 * 
	 * @param object
	 *        Object
	 * @return Object
	 * @throws HibernateException
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public Object deepCopy(Object object) throws HibernateException {
		List sourceSet = (List) object;
		List targetSet = new ArrayList();
		if (sourceSet != null) {
			targetSet.addAll(sourceSet);
		}
		return targetSet;
	}
	/**
	 * isMutable
	 * 
	 * @return boolean
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public boolean isMutable() {
		return false;
	}
	/**
	 * disassemble
	 * 
	 * @param object
	 *        Object
	 * @return Serializable
	 * @throws HibernateException
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public Serializable disassemble(Object object) throws HibernateException {
		return (Serializable) deepCopy(object);
	}
	/**
	 * assemble
	 * 
	 * @param serializable
	 *        Serializable
	 * @param object
	 *        Object
	 * @return Object
	 * @throws HibernateException
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public Object assemble(Serializable serializable, Object object) throws HibernateException {
		return deepCopy(serializable);
	}
	/**
	 * replace
	 * 
	 * @param object
	 *        Object
	 * @param object1
	 *        Object
	 * @param object2
	 *        Object
	 * @return Object
	 * @throws HibernateException
	 * @todo Implement this org.hibernate.usertype.UserType method
	 */
	@Override
	public Object replace(Object object, Object object1, Object object2) throws HibernateException {
		return object;
	}
	private String assemble(List l) {
		StringBuffer sb = new StringBuffer();
		// Iterator it = set.iterator();
		// while (it.hasNext()) {
		// sb.append(it.next());
		// sb.append(SPLITTER);
		// }
		for (int i = 0; i < l.size(); i++) {
			sb.append((String) l.get(i));
			sb.append(SPLITTER);
		}
		String fs = sb.toString();
		if (fs != null && fs.length() > 0 && fs.endsWith(SPLITTER)) {
			fs = fs.substring(0, fs.length() - 1);
		}
		return fs;
	}
	private List parse(String value) {
		String[] strs = StringUtils.split(value, SPLITTER);
		List set = new ArrayList();
		for (int i = 0; i < strs.length; i++) {
			if (!StringUtils.isBlank(strs[i])) {
				set.add(strs[i]);
			}
		}
		return set;
	}
}
