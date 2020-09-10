package com.qa.utils.fatjson;
 
import java.util.List;
import java.util.Map;
 
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

 
/**
 * json 解析工具类
 * @author Administrator
 *
 */
public class FastjsonUtils {
 
	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(String json) {
		return JSON.parseObject(json, Map.class);
	}
	
	private static SerializerFeature[] features = null;
 
	// ==================================toJson========================
 
	/**
	 * 最简单的json转换方法
	 * 
	 * @param obj
	 *            需要序列化的对象
	 * @return json字符串
	 */
	public static String toJson(Object obj) {
		return toJson(false, null, obj, null);
	}
 
	/**
	 * 默认配置的json转换方法
	 * 
	 * @param obj
	 *            需要序列化的对象
	 * @param cf
	 *            过滤器
	 * @return json字符串
	 */
	public static String toJson(Object obj, CustomFilter cf) {
		return toJson(false, null, obj, cf);
	}
	
	/**
	 * 将属性值为null的属性不进行序列化显示
	 * @param obj
	 * 			  需要序列化的对象
	 * @return
	 */
	public static String toJsonWithOutNull(Object obj) {
		return toJsonForRemove(new SerializerFeature[]{SerializerFeature.WriteMapNullValue}, obj, null);
	}
 
	/**
	 * 移除格式化参数配置后的json转换方法
	 * 
	 * @param rmFeatures
	 *            需要移除的格式化参数配置
	 * @param obj
	 *            需要序列化的对象
	 * @param cf
	 *            过滤器
	 * @return json字符串
	 */
	public static String toJsonForRemove(SerializerFeature[] rmFeatures,Object obj, CustomFilter cf) {
		return toJson(false, rmFeatures, obj, cf);
	}
 
	/**
	 * 加入格式化参数配置后的json转换方法
	 * 
	 * @param addFeatures
	 *            需要添加的格式化参数配置
	 * @param obj
	 *            需要序列化的对象
	 * @param cf
	 *            过滤器
	 * @return json字符串
	 */
	public static String toJsonForAdd(SerializerFeature[] addFeatures,Object obj, CustomFilter cf) {
		return toJson(true, addFeatures, obj, cf);
	}
 
	/**
	 * 具体的json转换方法
	 * 
	 * @param isAdd
	 *            选择添加还是移除操作
	 * @param myFeatures
	 *            需要操作的格式化参数配置
	 * @param obj
	 *            需要序列化的对象
	 * @param cf
	 *            过滤器
	 * @return json字符串
	 */
	public static String toJson(boolean isAdd,SerializerFeature[] myFeatures, Object obj, CustomFilter cf) {
		isAdd(isAdd, myFeatures);
		features = FeaturesUtil.getFeatureArray();
		return JSON.toJSONString(obj, cf, features);
	}
	
	public static JSONObject toJsonObject(String json) {
		return JSON.parseObject(json);
	}
 
	// parse to java object
	public static Object parseObject(String jsonString) {
		return toBean(jsonString, Object.class);
	}
 
	// parse to java bean
	public static <T> T toBean(String jsonString, Class<T> clazz) {
		return JSON.parseObject(jsonString, clazz);
	}
 
	// parse to java bean list
	public static <T> List<T> toBeanList(String jsonString, Class<T> clazz) {
		return JSON.parseArray(jsonString, clazz);
	}
 
	// -----------------------------------私有方法部分---------------------------------------
 
	/**
	 * 本类的私有的方法
	 * 
	 * @param isAdd
	 *            是否调用add方法
	 * @param _features
	 *            格式化参数配置数组
	 */
	private static void isAdd(boolean isAdd, SerializerFeature[] _features) {
		if (_features != null && _features.length > 0) {
			for (SerializerFeature sf : _features) {
				if (isAdd) {
					FeaturesUtil.add(sf);
				} else {
					FeaturesUtil.remove(sf);
				}
			}
		}
	}
 
	public static void main(String[] args) {
		String Json="{\"name\":\"jffhy\",\"job\":\"tester\"}";
		//Users user=toBean(Json, Users.class);
		//System.out.println(user.getJob());
		
		JSONObject json=toJsonObject(Json);
		System.out.println(json.getString("name"));
	}
 
}