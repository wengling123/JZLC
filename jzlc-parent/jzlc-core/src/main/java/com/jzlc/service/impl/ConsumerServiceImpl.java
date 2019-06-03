package com.jzlc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzlc.mapper.ConsumerMapper;
import com.jzlc.pojo.Consumer;
import com.jzlc.service.IConsumerService;
import com.jzlc.util.UserContext;

@Service
public class ConsumerServiceImpl implements IConsumerService {
	@Autowired
	private ConsumerMapper consumerMapper;
	
	@Override
	public boolean personalLogin(String consumerName, String password){
		System.out.println("参数consumerName:"+consumerName+"参数password:"+password.length());
		Consumer consumer = consumerMapper.personalLogin(consumerName,password);
		UserContext.setLogininfo(consumer);
		System.out.println("login:"+consumer);
		if(consumer != null) {
			return true;
		}
		return false;
	}

}
