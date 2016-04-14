package com.cpw.xml;


import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 
*
 */
public class JaxbUtil {

	/**
	 * JavaBean转换成xml
	 * 默认编码UTF-8
	 * @param obj
	 * @param writer
	 * @return 
	 */
	public static String convertToXml(Object obj) {
		return convertToXml(obj, "UTF-8");
	}

	/**
	 * JavaBean转换成xml
	 * @param obj
	 * @param encoding 
	 * @return 
	 */
	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * xml转换成JavaBean
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(String xml, Class<T> c) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}
	
	
	public static void main1(String[] args) {
		Task naTask =  new Task("getAppProcessStatus");
		
		TaskContent tc = new TaskContent("appname", "additionalParams");
		List<Application> a = new ArrayList<Application>();
		Application app = new Application();
		app.setRegionId(null);
		app.setType("SD");
		app.setStatus(2);
		tc.setAppList(a);
		a.add(app);
		naTask.setTaskContent(tc);
		System.out.println(convertToXml(naTask));
	}
	
	
	public static void main(String[] args) {
		String response = "<naTaskResp>   <taskCmd>getAppProcessStatus</taskCmd>   <errorCode>0</errorCode><errorDetail></errorDetail>   <appList> 	<app name=\"PSI\" status=\"1\"/> 	<app name=\"CSI\" status=\"1\"/>   </appList> </naTaskResp>";
		TaskResponse re = converyToJavaBean(response, TaskResponse.class);
		System.out.println(re.getAppList().get(0).getType()+re.getAppList().get(0).getStatus());
		System.out.println(re.getAppList().get(1).getType()+re.getAppList().get(1).getStatus());
		System.out.println(re.getTaskCmd());
	}
}
