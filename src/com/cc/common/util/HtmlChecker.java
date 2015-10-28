package com.cc.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class HtmlChecker {
	public static final Pattern	SCRIPT	= Pattern.compile("<script", Pattern.CASE_INSENSITIVE);
	public static final Pattern	IFRAME	= Pattern.compile("<iframe", Pattern.CASE_INSENSITIVE);
	public static final Pattern	FRAME	= Pattern.compile("<frame", Pattern.CASE_INSENSITIVE);
	public static final Pattern	EVENT	= Pattern.compile("<[^>]*['\" ]on[^>]*>", Pattern.CASE_INSENSITIVE);

	/**
	 * 检查html是否合法
	 * 
	 * @param html
	 * @return true：合法；false：不合法
	 */
	public static boolean check(String html) {
		if (StringUtils.isBlank(html)) {
			return true;
		}
		Matcher m;
		boolean b;
		m = SCRIPT.matcher(html);
		b = m.find();
		if (!b) {
			m = IFRAME.matcher(html);
			b = m.find();
		}
		if (!b) {
			m = FRAME.matcher(html);
			b = m.find();
		}
		if (!b) {
			m = EVENT.matcher(html);
			b = m.find();
		}
		return !b;
	}
	public static final void main(String[] args) {
		String s = "";
		System.out.println(check(s));
		s = "<p styleOnsubmit=''>Onsubmit='' sdfs</p><p>sdfs</p>\r\n<p>sdfs</p><p>sdfs</p>";
		System.out.println(check(s));
		s = "<p>sdfs</p><p>sdfs</p>\r\n<p>sdfs</p><Script fdf></script><p>sdfs</p>";
		System.out.println(check(s));
	}
}
