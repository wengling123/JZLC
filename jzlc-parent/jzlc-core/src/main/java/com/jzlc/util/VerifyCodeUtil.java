package com.jzlc.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
/*import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;*/
public class VerifyCodeUtil {
	private static int[] codes = {0,1,2,3,4,5,6,7,8,9};
	/**
	 * 发送短信验证码
	 * @param phoneNum 接收验证码的手机号
	 */
	public static void sendMessage(String phoneNum) {
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 生成验证码
		String code = getRandomCode(6);
		// 创建post请求
		URI uri = null;
		try {
			// 将参数放入键值对类NameValuePair中,再放入集合中
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("mobile", phoneNum));
			params.add(new BasicNameValuePair("tpl_id", "158039"));

			params.add(new BasicNameValuePair("tpl_value", encode(code)));
			params.add(new BasicNameValuePair("key",
					"0b2dfc0529705e13af4616d6c889076f"));
			// 设置uri信息,并将参数集合放入uri;
			// 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
			uri = new URIBuilder().setPath("http://v.juhe.cn/sms/send")
					.setParameters(params).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpPost httpPost = new HttpPost(uri); 
		//响应模型
		CloseableHttpResponse response = null;
		
		//由客户端执行(发送)Post请求
		try {
			response = httpClient.execute(httpPost);
			//从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if(responseEntity != null){
				//响应状态码为200表示发送成功
				if(response.getStatusLine().getStatusCode() == 200){
					/*VerifyCodeInfo verifyCodeInfo = JSONObject.parseObject(EntityUtils.toString(responseEntity),VerifyCodeInfo.class);
					if(verifyCodeInfo.getError_code() == 0){
						verifyCodeInfo.setPhoneNumber(phoneNum);
						verifyCodeInfo.setVerifyCode(code);
						verifyCodeInfo.setLastTime(new Date());
						UserContext.setVerifyCodeInSession(verifyCodeInfo);
					}else{
						throw new RuntimeException("发送失败!");
					}*/
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * 随机生成验证码
	 * @param length 验证码长度
	 * @return
	 */
	public static String getRandomCode(int length){
		if(length <= 0){
			throw new IndexOutOfBoundsException("length:"+length);
		}
		
		StringBuffer code = new StringBuffer();
		for (int i=0;i<length;i++) {
			int index = (int)(Math.random()*codes.length);
			code = code.append(codes[index]);
		}
		return code.toString();
	}
	
	/**
	 * 对验证码进行url编码
	 * @param code 需要编码的内容
	 */
	private static String encode(String code){
		StringBuffer  sb = new StringBuffer("#code#=");
		sb.append(code);
		try {
			return URLEncoder.encode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
