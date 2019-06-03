package com.jzlc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jzlc.util.UserContext;

/**
 * 拦截器  验证用户是否已登录
 * @author Ivy
 *
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//判断handler使用时Controller中一个处理请求的方法
		if(handler instanceof HandlerMethod){
			//如果用户信息为空,则拦截请求,重定向到登录页面
			if(UserContext.getLogininfo() == null){
				request.setAttribute("msg", "请先登录!");
				request.getRequestDispatcher("/login.html").forward(request, response);
				return false;
			}
		}
		//否则放行
		return super.preHandle(request, response, handler);
	}
	
}
