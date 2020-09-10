package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base1.TestBase;
import com.qa.restclient.KeyMethod;
import com.qa.restclient.RestClient;
import com.qa.util.BodyHead;

public class CountryTests extends TestBase {

//	private final static Logger log = Logger.getLogger(CountryTest.class);

	TestBase testBase;
	String host;
	String excelUrl;
	String Sheet_Post, Sheet_Get;
	RestClient restClient;
	CloseableHttpResponse clos;

	@BeforeClass
	public void setUp() throws Exception {
		testBase = new TestBase();
//		host = prop.getProperty("Host1");   // 线上环境
		host = prop.getProperty("HOST");   // 测试环境
		
		excelUrl = prop.getProperty("ExcelUrl");
		Sheet_Post = prop.getProperty("Sheet_Post");
		Sheet_Get = prop.getProperty("Sheet_Get");
	}

	@Test(groups = "user_post",invocationCount = 1)
	public void loginTest() throws Exception {

		KeyMethod.excel(excelUrl, Sheet_Post, host);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);

	}

	@Test(groups = "user_post" ,enabled = false)
	public void modifyUser() throws ClientProtocolException, IOException {

		restClient = new RestClient();
		// 准备请求头信息

		String token = null;
		HashMap<String, String> headermap = BodyHead.headMap("application/x-www-form-urlencoded", token);
		// 获得body参数
		String st = BodyHead.st("username=15680366537&password=7c4a8d09ca3762af61e59520943dc26494f8941b");
		clos = restClient.post(host+"/gateWay/client/countryApi/user/login", st, headermap);
		// 验证状态码是不是200
		int statusCode = clos.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode,RESPNSE_STATUS_COOE_200, "响应状态码不是200");

		// 把响应内容存储在字符串对象
		String responseString = EntityUtils.toString(clos.getEntity(), "UTF-8");

		// 创建Json对象，把上面字符串序列化成json对象
		JSONObject responseJson = JSON.parseObject(responseString);
		System.out.println("響應正文：" + responseJson);

	}
	
	
	//  --- 不填写则是（priority = 0 --- 数字越小越先执行）,dependsOnGroups 执行后面跟的方法后再执行@Test
	@Test(groups = "user_get",enabled = true ,dependsOnGroups = {"user_post"},invocationCount = 1)  // invocationCount = 5 -- 执行次数
	public void getUserTest() throws Exception {                                

		KeyMethod.excel(excelUrl, Sheet_Get, host);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);
	}

}
