package com.cc.common.struts2;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AjaxEncoderFilter implements Filter {
	private final String	AJAX_ENCODING	= "UTF-8";
	private final String	AJAX_HEADER		= "isAjax";
	protected Logger		log				= LoggerFactory.getLogger(AjaxEncoderFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getHeader(AJAX_HEADER) != null) {
			req.setCharacterEncoding(AJAX_ENCODING);
			// 确保不被struts更改
			req.getParameter(AJAX_HEADER);
			log.debug("ajax request");
		}
		chain.doFilter(request, response);
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	@Override
	public void destroy() {}
}
