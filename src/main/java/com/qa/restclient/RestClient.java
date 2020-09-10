package com.qa.restclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.utils.fatjson.FastjsonUtils;

public class RestClient {
	
	// 初始化log4g log
	private static Logger Log = Logger.getLogger(RestClient.class.getName());
	// 1.Get 请求方法（不带请求头的get方法封装）
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException{
		//创建一个可关闭的httpClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//创建一个HttpGet的请求对象
		HttpGet httpget = new HttpGet(url);	
		//执行请求，相当于postman上点击发送按钮，然后赋值给HTTPResponse对象接收
		Log.info("开始发送get请求...");
		CloseableHttpResponse httpResponse = httpclient.execute(httpget);
		Log.info("发送请求成功！开始得到响应对象。");
		return httpResponse;
		
	}
	
	//2. Get 请求方法（带请求头信息）
	public CloseableHttpResponse get(String url,HashMap<String, String> headermap) throws ClientProtocolException, IOException{
		//创建一个可关闭的HTTPClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//创建一个HttpGet对象
		HttpGet httpget = new HttpGet(url);
		//加载请求头到httpget对象
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httpget.addHeader(entry.getKey(),entry.getValue());			
			
		}
		//执行请求，相当于postman上点击发送按钮，然后赋值给httpResponse对象接收
		CloseableHttpResponse httpResponse = httpclient.execute(httpget);
		Log.info("开始发送带请求头的get请求...");	
		return httpResponse;
		
	}

	//3. Post方法(带请求头)
	public CloseableHttpResponse post(String url, String entityString,HashMap<String,String> headermap)throws ClientProtocolException, IOException {
		
		//创建一个可关闭的HTTPClient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//创建一个HttpGet对象
		HttpPost httppost = new HttpPost(url);
	    //设置payload
		
		StringEntity stringEntity = new StringEntity(entityString);//entityString参数，可以为"key1=value1&key2=value2"的一串字符串
		stringEntity.setContentType("application/x-www-form-urlencoded");
		httppost.setEntity(stringEntity);

//        httppost.setEntity(new StringEntity(entityString));
        //加载请求头到httpget对象
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httppost.addHeader(entry.getKey(),entry.getValue());			
			
		}
		//发送post请求
        CloseableHttpResponse httpResponse = httpclient.execute(httppost);
        Log.info("开始发送post请求");
        return httpResponse;

		
	}

	/**
	 * 封装put请求方法，参数和post方法一样
	 * @param url
	 * @param entityString,这个主要是设置payload，一般来说就是json串
	 * @param headerMap,带请求的头信息，格式是键值对，所以这里使用hashmap
	 * @return 返回响应对象
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * 
	 */
	//4、put方法
	public CloseableHttpResponse put(String url,String entityString,HashMap<String,String> headerMap) throws ClientProtocolException, IOException{
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPut httpput = new HttpPut(url);
		httpput.setEntity(new StringEntity(entityString));
        //加载请求头到httpput对象
		for (Map.Entry<String, String> entry:headerMap.entrySet()) {
			httpput.addHeader(entry.getKey(),entry.getValue());
			
		}
		//发送put请求
		CloseableHttpResponse httpResponse = httpclient.execute(httpput);
        Log.info("开始发送put请求");
		return httpResponse;
		
	}
	
	/**
	 * 封装 delete请求方法，参数和get方法一样
	 * @param url， 接口url完整地址
	 * @return，返回一个response对象，方便进行得到状态码和json解析动作
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	
	//5、delete方法
	public CloseableHttpResponse delete (String url) throws ClientProtocolException, IOException {
			
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpDelete httpdel = new HttpDelete(url);
		
		//发送delete请求
		CloseableHttpResponse httpResponse = httpclient.execute(httpdel);
		return httpResponse;
	}
	
	/**
	 * 获取响应状态码，常用来和TestBase中定义的状态码常量去测试断言使用
	 * @param response
	 * @return 返回int类型状态码
	 */
	//6、获得响应状态码
	public int getStatusCode(CloseableHttpResponse response){
		
		int statusCode = response.getStatusLine().getStatusCode();
		Log.info("解析，得到响应状态码："+statusCode);
		return statusCode;
	}
	
	/**
	 * @param response,任何请求返回的响应对象
	 * @return responseJson，返回响应体的josn格式对象，方便接下来对json对象内容解析
	 * 接下来，一般会继续调用TestUtil类下的json解析方法得到某一个json对象的值
	 * @throws IOException 
	 * @throws ParseException 
	 */
	//7、获得响应结果
	public JSONObject getResponseJson(CloseableHttpResponse response) throws ParseException, IOException{
		Log.info("得到响应对象的String格式");
		String responseString = EntityUtils.toString(response.getEntity(),"UTF-8");
//		JSONObject responseJson = JSON.parseObject(responseString);
		JSONObject responseJson = FastjsonUtils.toJsonObject(responseString);
		Log.info("返回响应内容的JSON格式");
		return responseJson;
		
	}
	
	/**
	 * 把json字符串转换成指定类型的实体bean
	 * get取出【user.getName()】
	 */
	public static <T> T getResponseJson2Bean(CloseableHttpResponse response, Class<T> clazz)
			throws ParseException, IOException {
		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		Log.info("得到响应对象的String格式,数据为：" + responseString);
		T t = FastjsonUtils.toBean(responseString, clazz);
		Log.info("返回响应的实体bean对象");
		return t;
	}


}

