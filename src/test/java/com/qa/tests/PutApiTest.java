package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

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

public class  PutApiTest extends TestBase {
	
	TestBase testBase;
	String host,url;
	RestClient restClient;
	CloseableHttpResponse clos;
	
	@BeforeClass
	public void setUp(){
		testBase = new TestBase();
		host = prop.getProperty("HOST");
		url = host+"/api/users/2";
		
	}
	
	@Test
	public void putTest() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		String token = null;
		HashMap<String,String> headermap = BodyHead.headMap("application/x-www-form-urlencoded",token);
		//获得body参数
		String st = BodyHead.body("15680366537 ","7c4a8d09ca3762af61e59520943dc26494f8941b",null);
		clos = restClient.put(url, st, headermap);
		
		//验证状态码是不是200
		int statusCode = clos.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		
		Assert.assertEquals(statusCode, RESPNSE_STATUS_COOE_200,"response status code is not 200");
		String responseString = EntityUtils.toString(clos.getEntity());
		JSONObject responseJson = JSON.parseObject(responseString);
		System.out.println(responseString);

	}
	
	
	

}
