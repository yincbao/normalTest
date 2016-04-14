package com.cpw.json.gson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cpw.cache.util.StringUtil;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

/**
 * 
 * ClassName: GsonDateFotmatUtil
 * @description
 * @author yin_changbao
 * @Date   Oct 16, 2015
 *
 */
public class GsonDateFotmatUtil {

	
	
	public static <T> Map<Class<?>, Object> processSerializable(Class<T> clazz) {
		if(clazz==null)
			return null;
		if (!clazz.isAnnotationPresent(com.cpw.json.gson.GsonDataFotmat.class))
			return null;
		Map<Class<?>, Object> adapterMap = new HashMap<Class<?>, Object> ();
		GsonDataFotmat format = clazz.getAnnotation(com.cpw.json.gson.GsonDataFotmat.class);
		if(format!=null&&!StringUtil.isEmpty(format.dateFormat()))
			addDateFormat(format.dateFormat(),adapterMap);
		if(format!=null&&!StringUtil.isEmpty(format.stringFormat()))
			addStringFormat(format.dateFormat(),adapterMap);
		return adapterMap;
	}
	
	private static <T> void addStringFormat(String pattern,Map<Class<?>, Object> adapterMap){
		//TODO
	}
	
	private static void addDateFormat(String pattern,Map<Class<?>, Object> adapterMap){
		final SimpleDateFormat df=new SimpleDateFormat(pattern);
		GsonTypeAdapter<Date> dateFormater =  new GsonTypeAdapter<Date>(){
			@Override
			public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
				String result = "";
				if(src!=null){
					
					result = df.format(src);
				}
				return new JsonPrimitive(result);  
			}

			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				if(json == null)
					return null;
				else{
					try {
						return df.parse(json.getAsString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				return null;
				
				}
			};
			adapterMap.put(Date.class,dateFormater);
	}
}
