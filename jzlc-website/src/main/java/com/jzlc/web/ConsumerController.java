package com.jzlc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzlc.service.IConsumerService;
import com.jzlc.util.AjaxJson;
import com.jzlc.util.MD5;

@Controller
public class ConsumerController {
	@Autowired
	private IConsumerService consumerService;
	
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("personalLogin")
	@ResponseBody
	public AjaxJson PersonalLogin(String consumerName, String password) {
		AjaxJson json = new AjaxJson();
		//json.setMsg("登录成功!");
		boolean login = consumerService.personalLogin(consumerName, MD5.encode(password));
		System.out.println(consumerName);
		System.out.println(MD5.encode(password));
		System.out.println(login);
		if (!login) {
			json.setSuccess(false);
			json.setMsg("账号或密码错误");
		}
		return json;
	}
	
}
