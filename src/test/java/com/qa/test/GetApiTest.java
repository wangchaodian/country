package com.qa.test;
 
import java.io.IOException;
 




import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.restclient.RestClient;
 
public class GetApiTest extends TestBase{
	TestBase testBase;
	String host;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	final static Logger Log = Logger.getLogger(GetApiTest.class);
	
	
	@BeforeClass
	public void setUp() {
		testBase = new TestBase();
		//Log.info("测试服务器地址为："+ host.toString());
		host = prop.getProperty("HOST");
		//Log.info("当前测试接口的完整地址为："+url.toString())
		url ="https://image.ycdatas.com/001b3b0bd0d24f93a071936a02322b3c.jpg";
		
	}
	
	@Test
	public void getAPITest() throws ClientProtocolException, IOException {
		Log.info("开始执行用例...");
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);
		
		//断言状态码是不是200
		Log.info("测试响应状态码是否是200");
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPNSE_STATUS_COOE_200,"响应状态码不是200");
		
		//把响应内容存储在字符串对象
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		System.out.println(responseString);
		
		//创建Json对象，把上面字符串序列化成json对象
//		JSONObject responseJson = JSON.parseObject(responseString);
//		System.out.println("respon json from API-->" + responseJson);
		
		//json内容解析
//		String s = TestUtil.getValueByJPath(responseJson, "data[0]/first_name");
//		System.out.println(s);
//		if (s.equals("George")) {
//			
//			System.out.println("解析json内容正确");
//			
//		}
		
	}
//	@Test
//	public void getAPITest1() throws ClientProtocolException, IOException {
//		restClient = new RestClient();
//		closeableHttpResponse = restClient.get(url);
//		
//		//断言状态码是不是200
//		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
//		Assert.assertEquals(statusCode, RESPNSE_STATUS_COOE_200,"响应状态码不是200");
//		
//		//把响应内容存储在字符串对象
//		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
//		
//		//创建Json对象，把上面字符串序列化成json对象
//		JSONObject responseJson = JSON.parseObject(responseString);
//		//System.out.println("respon json from API-->" + responseJson);
//		
//		//json内容解析
//		String s = TestUtil.getValueByJPath(responseJson, "data[1]/id");
//		System.out.println(s);
//		
//		//json内容解析
//		String s1 = TestUtil.getValueByJPath(responseJson, "per_page");
//		System.out.println("per_page ="+s1);
//		//json内容解析
//		String s2 = TestUtil.getValueByJPath(responseJson, "total");
//		System.out.println("total ="+s2);
//	
//	}
}
