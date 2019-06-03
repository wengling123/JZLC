package com.jzlc.config;

import java.lang.Override;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jzlc.interceptor.CheckLoginInterceptor;

@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// super.addInterceptors(registry);
		registry.addInterceptor(new CheckLoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login.html",
				 "/", "/index.html");
	}

}
