package com.cpw.json;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonTest {
	
	
	public static void sendMsg(Object content) {
		String str = JSON.toJSONString(content, SerializerFeature.WriteDateUseDateFormat);
		System.out.println(str);
	}

	
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("abd", "abc");
		map.put("1234", "1234");
		map.put("54321", "54321");
		
		sendMsg(map) ;
	}
}
