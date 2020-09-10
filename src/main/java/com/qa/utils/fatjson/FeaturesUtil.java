package com.qa.utils.fatjson;
 
import java.util.ArrayList;
import java.util.List;
 
import com.alibaba.fastjson.serializer.SerializerFeature;
 
/**
 * 序列化格式设置参数配置
 * 
 * @author Mr.chen <crsfyc-9@163.com>
 * @since 1.0.0
 */
public class FeaturesUtil {
	
	private static List<SerializerFeature> features = new ArrayList<SerializerFeature>();
	
	// 默认参数配置
	static {
		features.add(SerializerFeature.DisableCircularReferenceDetect);  // 取消引用代替配置
		features.add(SerializerFeature.WriteDateUseDateFormat);  // 使用日期格式化配置
		features.add(SerializerFeature.WriteMapNullValue);  // map中键对应值为null时用""代替的配置
//		features.add(SerializerFeature.WriteNullNumberAsZero); //将空数值用"0"来代替的配置
		features.add(SerializerFeature.WriteNullListAsEmpty); // 将空集合用"[]"来代替的配置
		features.add(SerializerFeature.WriteNullStringAsEmpty); // 将空字符串用""代替的配置
	}
	
	/**
	 * 添加序列化格式参数
	 * 
	 * @param feature
	 */
	public static void add(SerializerFeature feature){
		if(feature !=null){
			features.add(feature);
		}
	}
	
	/**
	 * 移除序列化格式参数
	 * 
	 * @param feature
	 */
	public static void remove(SerializerFeature feature){
		if(feature !=null){
			if(features.contains(feature)){
				features.remove(feature);
			};
		}
	}
	
	/**
	 * 获取配置参数集合对象
	 * 
	 * @return list of SerializerFeature
	 */
	public static List<SerializerFeature> getFeatures(){
		return features;
	}
	
	/**
	 * 获取配置参数数组对象
	 * 
	 * @return array of SerializerFeature
	 */
	public static SerializerFeature[] getFeatureArray(){
		return features.toArray(new SerializerFeature[]{});
	}
 
}