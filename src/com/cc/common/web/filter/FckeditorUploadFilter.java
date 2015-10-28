
package com.cc.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class FckeditorUploadFilter implements Filter {
	private String	DispatcherUrl;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		if (httprequest.getParameter("Command") != null && httprequest.getParameter("Command").equals("FileUpload")) {
			httprequest.getRequestDispatcher(DispatcherUrl).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	/** Initialization of the servlet. <br>
	 * 
	 * @throws ServletException if an error occure */
	@Override
	public void init(FilterConfig config) {
		// Put your code here
		DispatcherUrl = config.getInitParameter("DispatcherUrl");
	}

	@Override
	public void destroy() {

	}

}