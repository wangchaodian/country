package com.qa.tests;
 
import java.io.IOException;
 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base1.TestBase;
import com.qa.restclient.RestClient;
import com.qa.util.BodyHead;

 
public class PostApiCounrty extends TestBase{
	TestBase testBase;
	String host;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	String timestamp;
	
	
	@BeforeClass
	public void setUp() {
		testBase = new TestBase();
		host = prop.getProperty("Host1");
		url = host + "/gateWay/client/countryApi/user/login";
		
	}
	
	@Test
	public void postAPITest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		//准备请求头信息

		String token = null;
		HashMap<String,String> headermap = BodyHead.headMap("application/x-www-form-urlencoded",token);

		//获得sign加密参数
		String sign = BodyHead.sign("15680366537","7c4a8d09ca3762af61e59520943dc26494f8941b");
		//获得body参数
		String st = BodyHead.body("15680366537","7c4a8d09ca3762af61e59520943dc26494f8941b",sign);
		//发送请求
		closeableHttpResponse = restClient.post(url,st,headermap);
		//验证状态码是不是200
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPNSE_STATUS_COOE_200,"响应状态码不是200");

		//断言响应json内容中siteName是不是期待结果
		
		
		
		//把响应内容存储在字符串对象
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		
		//创建Json对象，把上面字符串序列化成json对象
		JSONObject responseJson = JSON.parseObject(responseString);
		System.out.println("響應正文：" + responseJson);
	
		//json内容解析
//		String s = TestUtil.getValueByJPath(responseJson, "data[0]/relName");
//		System.out.println(s);
//		if (s.equals("马红")) {
//			
//			System.out.println("解析json内容正确");
//			
//		}
		
	}
	
}
