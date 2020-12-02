package com.qa.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.restclient.KeyMethod;

public class GrowthValueCheck extends TestBase {
	
	TestBase testBase;
	String host;
	String host1;
	String excelUrl;
	String Sheet_Post, Sheet_Get;

	@BeforeClass
	public void setUp() throws Exception {
		testBase = new TestBase();
		host = prop.getProperty("CountryHost");   // 测试环境地址
		host1 = prop.getProperty("Host1");   // 线上环境
		excelUrl = prop.getProperty("ExcelScoreUrl");  //excel路径
		Sheet_Post = prop.getProperty("Sheet_Post");
		Sheet_Get = prop.getProperty("Sheet_Get");
	}
	
	@Test(groups = "post",invocationCount = 1,enabled = true)
	public void loginTest() throws Exception {

		KeyMethod.excel(excelUrl, Sheet_Post, host);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);

	}
	
	//  --- 不填写则是（priority = 0 --- 数字越小越先执行）,dependsOnGroups 执行后面跟的方法后再执行@Test)
	@Test(groups = "get",enabled = true ,dependsOnGroups = {"post"},invocationCount = 1)  // invocationCount = 5 -- 执行次数
	public void getUserTest() throws Exception {                                

		KeyMethod.excel(excelUrl, Sheet_Get, host);
//		Reporter.log("状态码***********：" + KeyMethod.statusCode, true);
	
	}

}
