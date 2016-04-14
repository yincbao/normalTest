package com.cpw.json.gson;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * ClassName: GsonDataFotmat
 * @description
 * @author yin_changbao
 * @Date   Oct 16, 2015
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GsonDataFotmat {
	
	String dateFormat() default "yyyy-MM-dd HH:mm:ss";
	
	String stringFormat() default "";

}