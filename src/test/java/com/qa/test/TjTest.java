package com.qa.test;

import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.restclient.KeyMethod;
import com.qa.restclient.RestClient;
import com.qa.util.BodyHead;
import com.qa.util.TJDecode;


public class TjTest extends TestBase {
	
//	private final static Logger log=Logger.getLogger(CountryTest.class);

	TestBase testBase;
	String host;
	String excelUrl;
	String Sheet_Post,Sheet_Get;
	String authorization;
	RestClient restClient;
	CloseableHttpResponse clos;

	@BeforeClass
	public void setUp() throws Exception {
		testBase = new TestBase();
		host = prop.getProperty("TJHost");    //线上环境
//		host = prop.getProperty("TjHostTest");  //test环境
		
		excelUrl = prop.getProperty("ExcelTJUrl");
		Sheet_Post = prop.getProperty("Sheet_Post");
		Sheet_Get = prop.getProperty("Sheet_Get");

	}
	
	@Test 
	public void loginOut() throws Exception{
		restClient = new RestClient();
		// 准备请求头信息

		String token = null;
		HashMap<String, String> headermap = BodyHead.headMap("application/x-www-form-urlencoded", token);
		// 获得body参数
		String st = BodyHead.st("username=64010030&password=c8758556729fdbf86befe648779c0413ec38f10c");
		clos = restClient.post(host+"/gateWay/user/userApi/login", st, headermap);
		// 验证状态码是不是200
		int statusCode = clos.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPNSE_STATUS_COOE_200, "响应状态码不是200");
		
		// 把响应内容存储在字符串对象 (加密后的密文)
		String responseString = EntityUtils.toString(clos.getEntity(), "UTF-8");
//		System.out.println("结果是："+responseString);
		//调用解密方法进行解密
		String responseString1 = TJDecode.decryptStr(responseString);
//		System.out.println(responseString1);
		// 创建Json对象，把上面字符串序列化成json对象
		JSONObject responseJson = JSON.parseObject(responseString1);
		System.out.println("響應正文：" + responseJson);
		authorization = KeyMethod.getDataJosn(responseJson,"token");
		System.out.println("token是:"+authorization);
	}
	

}
