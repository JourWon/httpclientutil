package com.jourwon.httpclient.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.jourwon.httpclient.pojo.HttpClientResult;

/**
 * Description: httpClient工具类
 * 
 * @author JourWon
 * @date Created on 2018年4月19日
 */
public class HttpClientUtils {

	// 编码格式。发送编码格式统一用UTF-8
	private static final String ENCODING = "UTF-8";
	
	// 设置连接超时时间，单位毫秒。
	private static final int CONNECT_TIMEOUT = 6000;
	
	// 请求获取数据的超时时间(即响应时间)，单位毫秒。
	private static final int SOCKET_TIMEOUT = 6000;
	
	

	/**
	 * 发送get请求；不带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url) throws Exception {
		return doGet(url, null);
	}

	/**
	 * 发送get请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url, Map<String, String> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建访问的地址
		URIBuilder uriBuilder = new URIBuilder(url);
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue());
			}
		}

		// 创建http对象
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。
		 * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
		 * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpGet.setConfig(requestConfig);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpGet);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送post请求；不带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url) throws Exception {
		return doPost(url, null);
	}

	/**
	 * 发送post请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Map<String, String> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建http对象
		HttpPost httpPost = new HttpPost(url);
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。
		 * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
		 * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPost.setConfig(requestConfig);
		// 设置请求头
		/*httpPost.setHeader("Cookie", "");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");*/

		// 封装请求参数
		packageParam(params, httpPost);

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult(httpResponse, httpClient, httpPost);
		} finally {
			// 释放资源
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送put请求；不带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url) throws Exception {
		return doPut(url);
	}

	/**
	 * 发送put请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url, Map<String, String> params) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpPut.setConfig(requestConfig);
		
		packageParam(params, httpPut);

		CloseableHttpResponse httpResponse = null;

		try {
			return getHttpClientResult(httpResponse, httpClient, httpPut);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送delete请求；不带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpDelete.setConfig(requestConfig);

		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult(httpResponse, httpClient, httpDelete);
		} finally {
			release(httpResponse, httpClient);
		}
	}

	/**
	 * 发送delete请求；带请求参数
	 * 
	 * @param url 请求地址
	 * @param params 参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url, Map<String, String> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, String>();
		}

		params.put("_method", "delete");
		return doPost(url, params);
	}

	/**
	 * Description: 封装请求参数
	 * 
	 * @param params
	 * @param httpMethod
	 * @throws UnsupportedEncodingException
	 */
	public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
			throws UnsupportedEncodingException {
		// 封装请求参数
		if (params != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			// 设置到请求的http对象中
			httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
		}
	}

	/**
	 * Description: 获得响应结果
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @param httpMethod
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
			CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws Exception {
		// 执行请求
		httpResponse = httpClient.execute(httpMethod);

		// 获取返回结果
		if (httpResponse != null && httpResponse.getStatusLine() != null) {
			String content = "";
			if (httpResponse.getEntity() != null) {
				content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
			}
			return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
		}
		return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	/**
	 * Description: 释放资源
	 * 
	 * @param httpResponse
	 * @param httpClient
	 * @throws IOException
	 */
	public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
		// 释放资源
		if (httpResponse != null) {
			httpResponse.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}

}