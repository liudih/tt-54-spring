package com.tomtop.management.authority.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

@WebFilter(urlPatterns="/login",filterName="vfilter")
public class VFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest htpprequest =(HttpServletRequest) request;
		if (htpprequest.getMethod().equals("post")) {
			String sv = (String) htpprequest.getSession().getAttribute("v");
			String v = (String) htpprequest.getParameter("v");
			if(!((StringUtils.isNotBlank(sv) && StringUtils.isNotBlank(v) && sv.equals(v)))){
				request.getRequestDispatcher("/log").forward(request, response);
			}
		}
		else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}
}
