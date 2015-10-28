
package com.cc.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MyBeanUtils {
	protected static Logger	logger	= LoggerFactory.getLogger(MyBeanUtils.class);

	/** 获得同时有get和set的field和value。
	 * 
	 * @param bean */
	public static Map describe(Object bean) {
		Map des = new HashMap();
		PropertyDescriptor desor[] = PropertyUtils.getPropertyDescriptors(bean);
		String name = null;
		for (int i = 0; i < desor.length; i++) {
			if (desor[i].getReadMethod() != null && desor[i].getWriteMethod() != null) {
				name = desor[i].getName();
				try {
					des.put(name, PropertyUtils.getProperty(bean, name));
				} catch (Exception e) {
					throw new RuntimeException("属性不存在：" + name);
				}
			}
		}
		return des;
	}

	public static void setSimpleProperty(Object bean, String name, Object value) {
		try {
			PropertyUtils.setSimpleProperty(bean, name, value);
		} catch (Exception e) {
			throw new RuntimeException("属性不存在：" + name);
		}
	}

	public static Object setSimpleProperty(Object bean, String name) {
		try {
			return PropertyUtils.getSimpleProperty(bean, name);
		} catch (Exception e) {
			throw new RuntimeException("属性不存在：" + name);
		}
	}

	public static Object getSimpleProperty(Object bean, String propName) throws IllegalArgumentException, SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return bean.getClass().getMethod(getReadMethod(propName)).invoke(bean);
	}

	/** 直接读取对象属性值,无视private/protected修饰符,不经过getter函数. */
	public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/** 直接设置对象属性值,无视private/protected修饰符,不经过setter函数. */
	public static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/** 循环向上转型,获取对象的DeclaredField. */
	public static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}

	/** 循环向上转型,获取类的DeclaredField. */
	public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
	}

	/** 强制转换fileld可访问. */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	private static String getReadMethod(String name) {
		return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
	}
}
