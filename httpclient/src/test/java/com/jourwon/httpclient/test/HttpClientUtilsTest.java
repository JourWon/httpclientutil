package com.jourwon.httpclient.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.jourwon.httpclient.pojo.HttpClientResult;
import com.jourwon.httpclient.util.HttpClientUtils;

/**
 * Description: HttpClientUtils工具类测试
 * 
 * @author JourWon
 * @date Created on 2018年4月19日
 */
public class HttpClientUtilsTest {

	/**
	 * Description: 测试get无参请求
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGet() throws Exception {
		HttpClientResult result = HttpClientUtils.doGet("http://127.0.0.1:8080/hello/get");
		System.out.println(result);
	}

	/**
	 * Description: 测试get带参请求
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetWithParam() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("message", "helloworld");
		HttpClientResult result = HttpClientUtils.doGet("http://127.0.0.1:8080/hello/getWithParam", params);
		System.out.println(result);
	}

	/**
	 * Description: 测试post无参请求
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPost() throws Exception {
		HttpClientResult result = HttpClientUtils.doPost("http://127.0.0.1:8080/hello/post");
		System.out.println(result);
	}

	/**
	 * Description: 测试post带参请求
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPostWithParam() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("code", "0");
		params.put("message", "helloworld");
		HttpClientResult result = HttpClientUtils.doPost("http://127.0.0.1:8080/hello/postWithParam", params);
		System.out.println(result);
	}

}
