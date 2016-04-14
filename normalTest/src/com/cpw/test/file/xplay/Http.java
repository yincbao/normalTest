package com.cpw.test.file.xplay;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Http {

	
	public static String getRespInStr(String url) {
		String responds = "";
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.getParams().setContentCharset("utf-8");
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, true));
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,30000);
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
			}
			InputStream in = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String tempbf;
			StringBuffer html = new StringBuffer(100);
			while ((tempbf = br.readLine()) != null) {
				html.append(tempbf +"\n");
			}
			responds = html.toString();
		} catch (HttpException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return responds;
	}
	public static String getRespInImg(String url,String filename) throws Exception {
		String responds = "";
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, true));
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,30000);
		int statusCode = client.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception();
		}
		File storeFile = new File(filename);  
		FileOutputStream output = new FileOutputStream(storeFile);  
		output.write(method.getResponseBody());  
		output.close();
		return responds;
	}
	public static String getRespInImgAsStream(String url,String filename) throws Exception {
		String responds = "";
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(5, true));
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,60000);
		int statusCode = client.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception();
		}
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
		InputStream inStream = method.getResponseBodyAsStream();
		 byte[] buffer = new byte[128];  
		 int len = 0;  
		 while( (len=inStream.read(buffer)) != -1 ){  
			 outStream.write(buffer, 0, len);  
		 }
		inStream.close();  
		File storeFile = new File(filename);  
		FileOutputStream output = new FileOutputStream(storeFile);  
		output.write(outStream.toByteArray());  
		output.close();
		return responds;
	}
}
