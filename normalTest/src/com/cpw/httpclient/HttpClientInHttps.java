package com.cpw.httpclient;


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;


public class HttpClientInHttps {
    public static void main(String[] args) throws Exception{
    	normalGet("https://www.atlassian.com/");
//    	normalGet("https://localhost:8443/InfosysSSO/login.do?TARGET=https://localhost:8443/SyngentaSite/");
//    	normalGet("https://daimler.portal.stg.covisint.com/web/portal/home");
//    	ssl("https://localhost/InfosysSSO");

    }
    
    public static HttpClient getHttpClient(){
    	HttpClient client = new HttpClient();
        //client.getHostConfiguration().setProxy("172.28.51.173", 7000);
        /**
         * 当代理需要用户名密码时
         */
//        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("ID", "PSW"); 
//        client.getState().setProxyCredentials(AuthScope.ANY, creds);
        return client;
    }

	public static void ssl(String url) throws Exception {
		HttpClient client = getHttpClient();
		ProtocolSocketFactory fcty = new MySSLProtocolSocketFactory();
		Protocol.registerProtocol("https", new Protocol("https", fcty, 8443));
		PostMethod post = new PostMethod(url);
		client.executeMethod(post);
		int statusCode = client.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + post.getStatusLine());
		}
		byte[] responseBody = post.getResponseBody();
		System.out.println(new String(responseBody));
		post.releaseConnection();

	}

    public static void normalGet(String url) throws Exception{
    	
    	HttpClient client = getHttpClient();
        
     
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
                    new DefaultHttpMethodRetryHandler()); 
        int statusCode = client.executeMethod(getMethod);
        if (statusCode != HttpStatus.SC_OK) {
          System.err.println("Method failed: " + getMethod.getStatusLine());
        }
        byte[] responseBody = getMethod.getResponseBody();
        System.out.println(new String(responseBody));
        getMethod.releaseConnection();
    }
   
}
