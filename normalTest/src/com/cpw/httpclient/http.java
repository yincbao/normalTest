package com.cpw.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.omg.CORBA.portable.ApplicationException;

public class http {
	public static String sentRequest(String url, String requestXml) throws ApplicationException {

        int statusCode = -1;
        String responseXMl = null;
        PostMethod post = null;
        HttpClient httpClient = null;
        try {
            byte[] request = null;
            request = requestXml.getBytes("utf-8");
            RequestEntity entity = new ByteArrayRequestEntity(request, "text/xml; charset=utf-8");

            httpClient = new HttpClient();
            post = new PostMethod(url);
            post.setRequestEntity(entity);

            HttpMethodParams params = new HttpMethodParams();
            params.setSoTimeout(3000);
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
           
            params.setHttpElementCharset("utf-8");
            post.setParams(params);
            
            statusCode = httpClient.executeMethod(post);

            if (statusCode == 200) {
                byte responseByte[] = post.getResponseBody();
                responseXMl = new String(responseByte);
                }

        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
            if (httpClient != null) {
                httpClient.getHttpConnectionManager().closeIdleConnections(0);
            }
        }

        return responseXMl;
    }
	
	public static void main(String[] args) throws ApplicationException {
		String str = sentRequest("http://www.avmayi.info", "abc"); 
	}
}
