package com.qa.restclient;

public class Constants {

	// 这里定义为public static的类型，方便其他任何类进行访问和调用
	public static final String URL = "http://192.168.1.222/tj/";;

	// dataEngine.xlsx中一些单元格的索引值(post)
	
	public static final int Col_Name = 0;   //用例描述
	public static final int Col_TestCaseID = 1;   //用例id
	public static final int Col_url = 2;  //接口url
	public static final int Col_Parameter= 3; //parameter
	public static final int Col_Code = 4; //code
	public static final int Col_statusCode = 5; //RealCode，填入响应状态码 
	public static final int Col_TestStepResult = 6; //Result，结果是否包含
	public static final int Col_Clos = 7; //RealResult，填入响应正文
	public static final int Col_RunMode = 8;    //RunMode，是否执行该条用例
	public static final int Col_Result = 9;    //Successful，填入是否成功
	public static final int Col_ResponseTime = 10;    //ResponseTime，接口响应时间

	// 结果状态标记
	public static final String KEYWORD_FAIL = "Fail";
	public static final String KEYWORD_PASS = "Pass";



}
