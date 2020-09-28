package com.qa.base;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
 
//接口请求测试的父类
public class TestBase {
	
	public Properties prop;
	public static int RESPNSE_STATUS_COOE_200 = 200;
	public int RESPNSE_STATUS_COOE_201 = 201;
	public int RESPNSE_STATUS_COOE_404 = 404;
	public int RESPNSE_STATUS_COOE_403 = 403;
	public int RESPNSE_STATUS_COOE_500 = 500;
	
	
	//写一个构造函数
	public TestBase() {
		
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\apidata\\config.properties");
//			FileInputStream fis = new FileInputStream("D:/JenkinsNode/apidata/config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

