package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.restclient.KeyMethod;
import com.qa.restclient.RestClient;
import com.qa.util.BodyHead;

public class CountryTest extends TestBase {

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
//		host = prop.getProperty("Host1");   // 
		host = prop.getProperty("CountryHost");   // 
		
		excelUrl = prop.getProperty("ExcelCountryUrl");
		Sheet_Post = prop.getProperty("Sheet_Post");
		Sheet_Get = prop.getProperty("Sheet_Get");
	}

	@Test(groups = "user_post",invocationCount = 1,enabled = true)
	public void loginTest() throws Exception {

		KeyMethod.excel(excelUrl, Sheet_Post, host);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);

	}

	@Test(groups = "user_post" ,enabled = false)
	public void modifyUser() throws ClientProtocolException, IOException {

		restClient = new RestClient();
		// 准备请求头信息

		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzeXN0ZW1UeXBlIjoiMSIsInNpdGVJZCI6MzYwOTY4LCJ1c2VydHlwZSI6IlVTRVIiLCJ1c2VySWQiOjIxNywiY3JlYXRlZCI6MTYwMTQ1MTQyNzEyNSwidXNlcm5hbWUiOiIxNTY4MDM2NjUzNyJ9.PzNdPdZxtzd-W1UxNsi7PvRYqiSH04nUoQRRIiNcZCpl5tTWXr-s6rBh9tiPr60e8lTF7Kc5TDmphhxXv3dEuA";
		HashMap<String, String> headermap = BodyHead.headMap("application/x-www-form-urlencoded", token);
		// 获得body参数
		String st = BodyHead.st("content=haha111&topicIds=22&imgs=,,https%3A%2F%2Fimage.ycdatas.com%2Fa6d052f0aa4a47a2947cdb1959d9de94.jpg%2C%2C,,");
		clos = restClient.post(host+"/gateWay/client/countryApi/emotionPost/addEmotionPost", st, headermap);
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
	
	@AfterClass
	public void loginOut(){
		System.out.println("----------------");
		System.out.println("接口测试完成!!!");
	}


}
