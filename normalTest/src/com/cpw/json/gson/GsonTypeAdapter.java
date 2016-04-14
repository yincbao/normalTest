package com.cpw.json.gson;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
/**
 * 使得子类兼具JsonSerializer、JsonDeserializer两接口功能。
 * ClassName: GsonTypeAdapter
 * @description
 * @author Paul.Yin
 * @Date   Oct 16, 2015
 * 
 * @param <T>
 */
public abstract class GsonTypeAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
	

}
