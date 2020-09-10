package com.qa.utils.fatjson;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;


@SuppressWarnings("unchecked")
public class CustomFilter implements  PropertyPreFilter  {
	
	//需要序列化的属性
	private  Map <Class<?>,String[]> includes = new HashMap<Class<?>,String[]>();
	//不需要序列化的属性
	private  Map <Class<?>,String[]> excludes = new HashMap<Class<?>,String[]>();
	
	public CustomFilter(){
		
	}
	
	public CustomFilter(Map<Class<?>,String[]> excludes){
		
		this(Collections.EMPTY_MAP,excludes);
		
	}

	public CustomFilter(Map<Class<?>, String[]> includes,Map<Class<?>, String[]> excludes) {
		this.includes = includes;
		this.excludes = excludes;
	}
	
	

	
	@Override
	public boolean apply(JSONSerializer serializer, Object source, String name) {
		
		// 对象为空，直接放行
		if (source == null) {
			return true;
		}
		// 获取当前需要序列化的对象的类类型
				Class<?> clazz = source.getClass();
				if(!this.excludes.isEmpty()){
					return isNeed(clazz,this.excludes,name);
				}else if(!this.includes.isEmpty()){
					return isNeed(clazz,this.includes,name);
				}else{
					return true;
				}

	}
	
	/**
	 * @Description 判断是否需要序列化
	 * @param clazz 类类型
	 * @param map 需要序列化或不需要序列化的属性集合
	 * @param name 放射得到的javaBean中的属性
	 * @return 布尔值
	 */
	private boolean isNeed(Class<?> clazz,Map<Class<?>,String[]> map,String name){
		boolean isNeed = true;
		for (Map.Entry<Class<?>, String[]> item : map.entrySet()) {
			/*
			 *  isAssignableFrom():
			 *  	判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，
			 *  	或是否是其超类或超接口。
			 */
			if (item.getKey().isAssignableFrom(clazz)) {
				String[] strs = item.getValue();
				// 该类型下 此 name 值无需序列化
				if (isHave(strs, name)) {
					if(map == this.excludes){
						isNeed = false;
					}
				}
			}
		}
		return isNeed;
	}

	/**
	 * @Description 判断数组中是否包含指定的字符或字符串
	 * @param strs 字符串数组
	 * @param str 要查找的字符或字符串
	 * @return 布尔值
	 */
	private boolean isHave(String[] strs, String str) {
		for (int i = 0; i < strs.length; i++) {
			// 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
			if (strs[i].equals(str)) {
				// 查找到了就返回真，不在继续查询
				return true;
			}
		}
		// 没找到返回false
		return false;
	}
	public Map<Class<?>, String[]> getIncludes() {
		return includes;
	}
 
	public void setIncludes(Map<Class<?>, String[]> includes) {
		this.includes = includes;
	}
 
	public Map<Class<?>, String[]> getExcludes() {
		return excludes;
	}
 
	public void setExcludes(Map<Class<?>, String[]> excludes) {
		this.excludes = excludes;
	}
	
}
