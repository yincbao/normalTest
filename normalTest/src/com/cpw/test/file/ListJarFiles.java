package com.cpw.test.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ListJarFiles {
	
	public static  Map<String,URL> map = Collections.synchronizedMap(new HashMap<String,URL>());
	public static File[] listFiles(String path){
		
		File fileDir = new File(path);
		if(!fileDir.exists()){
			System.err.println("no such director or file found!");
			if(fileDir.mkdirs()){
				System.out.println("create a new");
			}else{
				System.err.println("error");
			}
		}
		return fileDir.listFiles();
	}
	
	public static void getJarINF(File[] files){
		for(File file:files){
			if(file.isFile()&&file.getName().endsWith(".jar")){
				System.out.println("found an new plug-in: "+file.getName());
				try {
					parseJar(file);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
//	public static void listJarFiles1(String path) throws DocumentException{
//		
//		File fileDir = new File(path);
//		if(!fileDir.exists()){
//			System.err.println("no such director or file found!");
//			if(fileDir.mkdirs()){
//				System.out.println("create a new");
//			}else{
//				System.err.println("error");
//			}
//		}
//		File[] files = fileDir.listFiles();
////		System.out.println(files.length);
//		for(File file:files){
//			if(file.isFile()&&file.getName().endsWith(".jar")){
//				System.out.println("found an new plug-in: "+file.getName());
//				parseJar(file);
//			}
//		}
//		
//	}
	
	public static void parseJar(File JFile) throws DocumentException{
		init(JFile);
//		Iterator ite = map.keySet().iterator();
//		while(ite.hasNext()){
//			System.out.println(ite.next().toString());
//			System.out.println(map.get(ite.next().toString()));
//		}
		byte[] jr = getJarResource("nmm.xml");
//		System.out.println(new String(jr));
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");
		Document doc = reader.read(new ByteArrayInputStream(jr));
		Element root = doc.getRootElement();
		List<Element> list = root.elements("bean");
		for(Element element:list){
			String id = element.attributeValue("id");
			String clazz = element.attributeValue("class");
			String initMethod= element.attributeValue("init-method");
			String destroyMethod=element.attributeValue("destroy-method");
			System.out.println("<?xml version=1.0 encoding=\"UTF-8\" ?>"+"\r\n"+"<beans> \r\n <bean id="+id+" class ="+clazz+" init-method=\""+initMethod+"\" destroy-method=\""+destroyMethod+"\"/>\r\n<beans>");
		}
		
		
		
	}
	
	
	private static byte[] getJarResource(String resourceName){
		
		URL jarUrl = map.get(resourceName);
		if(jarUrl ==null){
			System.err.println("not such file existed");
			return null;
		}
		
		JarURLConnection conn = null;
		InputStream is = null;
	    byte[] buffer = null;
		try {
			conn = (JarURLConnection) jarUrl.openConnection();
			is = conn.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] abyData = new byte[4096]; 
	        int nRead = 0; 
			while( (nRead = is.read( abyData )) > 0 ){
				bos.write(abyData, 0, nRead);
			}
			buffer = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{

	         if( is != null )
	         {
	            try
	            {
	               is.close();
	            }
	            catch( IOException ioe )
	            {}
	            is = null;
	            
	            if( conn == null )
	            {
	            }
	            
	            try
	            {
	               conn.getJarFile().close();
	            }
	            catch( Exception ex )
	            {}  
	            conn = null;
	         }
			
		}
		return buffer;
		
		
		
	}
	
	private static void init(File jarFile){
		String currentJarURL = "jar:"+jarFile.toURI().toString()+"!/";
		JarFile jarFileObject = null; 
		try {
			jarFileObject = new JarFile(jarFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Enumeration<JarEntry> enumer = jarFileObject.entries();
		String entryName = null;
		while(enumer.hasMoreElements()){
			JarEntry jarentry = enumer.nextElement();
			entryName = jarentry.getName();
//			Certificate[] ce =  jarentry.getCertificates();
//			for(Certificate certificate:ce){
//				System.out.println("certificated public key: "+certificate.getPublicKey());
//			}
			try {
				String key = entryName.substring(entryName.lastIndexOf("/")+1);
				map.put(key, new URL(currentJarURL+entryName));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
			
			getJarINF(listFiles("D:/plug-in"));
	}

}
