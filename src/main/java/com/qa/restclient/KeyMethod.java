package com.qa.restclient;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.base.TestBase;
import com.qa.util.BodyHead;
import com.qa.utils.fatjson.FastjsonUtils;


public class KeyMethod {

	public static boolean bResult;  
	public static String sTestCaseID;  //用例id
	public static String sRunMode;  // 用例RunMode
	public static int iTestStep, iTestLastStep,iTestCase;
	public static int statusCode;  // 响应状态码
	public static String url,parameter; // 取表中的三个参数值
	public static String authorization; // token
	public static String cl ; // 接口响应正文
	public static String dataCode ; //响应结果中json的code值
	public static String interfaceName; // 用例描述
	public static String st; // post请求参数
	public static String result; // Result响应正文期望值
	public static long startTime, endTime;  
	public static String responseTime;

	static RestClient restClient;
	static CloseableHttpResponse clos;
	
	//读取Excel表中数据进行接口测试
	public static void excel(String excelUrl,String tableName,String dns) throws Exception {

		// 获得表路径
		ExcelUtils.setExcelFile(excelUrl);
		// 获取测试用例数量
		int iTotalTestCases = ExcelUtils.getRowCount(tableName);
		// System.out.println(iTotalTestCases);
		// 外层for循环，有多少个测试用例就执行多少次循环
		for (iTestCase = 0; iTestCase < iTotalTestCases; iTestCase++) {
			// 从post表获取测试ID
//			bResult = true;
			sTestCaseID = ExcelUtils.getCellData(iTestCase,Constants.Col_TestCaseID, tableName);
			// 获取当前测试用例的RunMode的值
			sRunMode = ExcelUtils.getCellData(iTestCase, Constants.Col_RunMode,tableName);
			
			// Run Mode的值控制用例是否被执行
			if (sRunMode.equals("Yes")) {

				// 获得测试步骤
				iTestStep = ExcelUtils.getRowContains(sTestCaseID,Constants.Col_TestCaseID, tableName);
				iTestLastStep = ExcelUtils.getTestStepsCount(tableName, sTestCaseID, iTestStep);
//				bResult = true;
				// 下面这个for循环的次数就等于测试用例的步骤数
				for (; iTestStep < iTestLastStep; iTestStep++) {

					parameter = ExcelUtils.getCellData(iTestStep,Constants.Col_Parameter, tableName);

//					Excel中url列
					url = ExcelUtils.getCellData(iTestStep, Constants.Col_url,tableName);

					//Excel中接口名称列
					interfaceName = ExcelUtils.getCellData(iTestStep,Constants.Col_Name, tableName);
					
					//Excel中Result列
					result = ExcelUtils.getCellData(iTestStep, Constants.Col_TestStepResult, tableName);
					//发送请求
					if (tableName.equals("post") && !interfaceName.equals("获取积分等级Get") ) {
						postActions(tableName,dns+url);
						
					}else{
						getActions(tableName, dns+url+parameter);
					}

				}
				if (bResult == true) {

					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestCase,Constants.Col_Result, tableName);
				}else if (bResult == false) {

					ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestCase, Constants.Col_Result,tableName);
					
				}
			}
		}
	}

	//post请求
	public static void postActions(String tableName,String InterfaceUrl) throws ClientProtocolException, IOException{
		
		// 建立一个新连接
		restClient = new RestClient();
		// 准备请求头信息

		String token = authorization;
		HashMap<String, String> headermap = BodyHead.headMap("application/x-www-form-urlencoded", token);

		// 获得body参数
		try {
			st = BodyHead.st(parameter);
		} catch (Exception e) {
			st = parameter;
		}
		
		//接口请求开始时间
		long currentTimeMillis = System.currentTimeMillis();
		startTime = currentTimeMillis;
		
		// 发送请求
		clos = restClient.post(InterfaceUrl, st, headermap);
		//把响应内容存储在字符串对象
		String responseString = EntityUtils.toString(clos.getEntity(),"UTF-8");
		//接口请求开始时间
		long currentTimeMillis1 = System.currentTimeMillis();
		endTime = currentTimeMillis1;
		//接口响应时长
		responseTime = String.valueOf(endTime - startTime);
		
//		判断响应结果是否包含期望结果
		boolean re = responseString.contains(result);
//		System.out.println(re);
		if (re) {
			bResult = true;
		}else {
			bResult = false;
		}
		//创建Json对象，把上面字符串序列化成json对象
		JSONObject responseJson = JSON.parseObject(responseString);
		if (interfaceName.equals("用户登录")) {
			authorization = getDataJosn(responseJson,"token");
		}
//		System.out.println("token:"+authorization);
		System.out.println("接口响应时间为："+responseTime+"--"+sTestCaseID+":"+bResult+"--响应正文：" + responseJson);

//		Reporter.log("响应字符串***********："+responseString);

		// 将响应正文填入dataEngine.xlsx中
		cl = String.valueOf(responseJson);	
		ExcelUtils.setCellData(cl, iTestCase, Constants.Col_Clos, tableName);
		
		//将响应时长填入表中
		ExcelUtils.setCellData(responseTime, iTestCase, Constants.Col_ResponseTime, tableName);

		// 将响应状态码填入dataEngine.xlsx中
		statusCode = clos.getStatusLine().getStatusCode();
		String code = String.valueOf(statusCode);
		ExcelUtils.setCellData(code, iTestCase, Constants.Col_statusCode,tableName);
//		Assert.assertEquals(statusCode, TestBase.RESPNSE_STATUS_COOE_200,"响应状态码不是200");

	}

	//传入json对象，根据参数获得josn数组对象
	public static  String getDataJosn(JSONObject responseJson,String js){
		try {
			authorization = FastjsonUtils.toMap(responseJson.getString("data")).get(js);	
			return authorization;
		} catch (Exception e) {
			authorization = null;
			return  authorization;
		}

	}
	
	//传入json对象，根据参数获得josn数组对象
	public static  String getJosn(JSONObject responseJson,String js){
		try {
			dataCode = responseJson.getString(js);	
			return dataCode;
		} catch (Exception e) {
			dataCode = null;
			return dataCode;
		}
	
	}
	
//	get请求方法发送
	public static void getActions(String tableName,String InterfaceUrl) throws ClientProtocolException, IOException{
		
		//建立一个新连接
		restClient = new RestClient();
		// 准备请求头信息
		String token = authorization;
		HashMap<String, String> headermap = BodyHead.headMap("application/x-www-form-urlencoded", token);

		//接口请求开始时间
		long currentTimeMillis = System.currentTimeMillis();
		startTime = currentTimeMillis;
		// 发送请求
		clos = restClient.get(InterfaceUrl, headermap);

		//把响应内容存储在字符串对象
		String responseString = EntityUtils.toString(clos.getEntity(),"UTF-8");	
		//接口请求结束时间
		long currentTimeMillis1 = System.currentTimeMillis();
		endTime = currentTimeMillis1;
		//接口响应时长
		responseTime = String.valueOf(endTime - startTime);
//		判断响应结果是否包含期望结果
		boolean re = responseString.contains(result);
//		System.out.println(re);
		if (re) {
			bResult = true;
		}else {
			bResult = false;
		}
		//创建Json对象，把上面字符串序列化成json对象
		JSONObject responseJson = JSON.parseObject(responseString);
//		Reporter.log("响应字符串***********："+responseString);
		System.out.println("接口响应时间为："+responseTime+"--"+sTestCaseID+":"+bResult+"--响应正文：" + responseJson);
		try {
			 dataCode = getJosn(responseJson, "code");

		} catch (Exception e) {
			dataCode = null;
					
		}
//		System.out.println("code = "+dataCode);
		// 将响应正文填入dataEngine.xlsx中
		cl = String.valueOf(responseJson);		
		ExcelUtils.setCellData(cl, iTestCase, Constants.Col_Clos, tableName);
		
		//将响应时长填入表中
		ExcelUtils.setCellData(responseTime, iTestCase, Constants.Col_ResponseTime, tableName);

		// 将响应状态码填入dataEngine.xlsx中
		statusCode = clos.getStatusLine().getStatusCode();
		String code = String.valueOf(statusCode);
		ExcelUtils.setCellData(code, iTestCase, Constants.Col_statusCode,tableName);
//		Assert.assertEquals(statusCode,TestBase.RESPNSE_STATUS_COOE_200,"响应状态码不是200");

	}
}
