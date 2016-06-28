package com.tomtop.image.controllers;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.alibaba.fastjson.JSONObject;


public class MultipartExceptionHandler extends OncePerRequestFilter  {

	

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
            filterChain.doFilter(request, response);
        } catch (MaxUploadSizeExceededException e) {
        	handle(request, response, e);
        } catch (ServletException e) {
            if(e.getRootCause() instanceof MaxUploadSizeExceededException) {
                handle(request, response, (MaxUploadSizeExceededException) e.getRootCause());
            } else {
                throw e;
            }
        }
		
	}
	
	 private void handle(HttpServletRequest request,
	            HttpServletResponse response, MaxUploadSizeExceededException e) throws ServletException, IOException {

		 JSONObject feedback = new JSONObject();
 		feedback.put("succeed", false);
 		feedback.put("error", "your file size greater than maximum permitted size:" + 2);
 		ServletOutputStream out = response.getOutputStream();
 		out.write(feedback.toJSONString().getBytes());
	    }
	 
}
