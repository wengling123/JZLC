package com.jzlc.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jzlc.pojo.Consumer;

/**
 * 封装获取session,并往HttpSession中存放数据
 * @author Ivy
 *
 */
public class UserContext {
	
	private static String USER_IN_SESSION = "currentUser";

	private static HttpSession getHttpSession(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
	/**将当前登录用户存到session域*/
	public static void setLogininfo(Consumer consumer){
		getHttpSession().setAttribute(USER_IN_SESSION,consumer);
	}
	/**从session域中获取当前登录用户*/
	public static Consumer getLogininfo(){
		return (Consumer) getHttpSession().getAttribute(USER_IN_SESSION);
	}
	
	
}
