package com.cpw.test.file.xplay;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class Rename {
	
	private static final Log logger = LogFactory.getLog(Rename.class);

	private static String srcFilePath = "D:/xfmovie/";
	
	private static String foler = "D:/tmp/";
	private static String dstFilePath_m = foler+"m/";
	private static String dstFilePath_r = foler+"r/";
	private static String dstFilePath_a = foler+"a/";
	private static String dstFilePath_o = foler+"o/";
	private static String dstFilePath_i = foler+"i/";
	
	
	public static void rename(File file,Map<String,String> namecode){
		if(file.exists()){
			String newName = DateFormatUtils.format(new Date(), "HHmmssSSS")+RandomStringUtils.randomNumeric(4)+".block";
			
			String oldName = file.getName();
			oldName = oldName.substring(oldName.lastIndexOf(".")+1).toUpperCase();
			if(oldName.indexOf("AVI")>-1){
				File foler = new File(dstFilePath_a);
				foler.mkdirs();
				if(!foler.exists())
					try {
						foler.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				logger.debug("file: "+file.getName()+" has been renamed to: "+newName);
				file.renameTo(new File(dstFilePath_a+newName));
			}else if(oldName.indexOf("MP4")>-1){
				File foler = new File(dstFilePath_m);
				foler.mkdirs();
				if(!foler.exists())
					try {
						foler.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				file.renameTo(new File(dstFilePath_m+newName));
				logger.debug("file: "+file.getName()+" has been renamed to: "+newName);
			}else if(oldName.indexOf("RMVB")>-1){
				File foler = new File(dstFilePath_r);
				foler.mkdirs();
				if(!foler.exists())
					try {
						foler.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				file.renameTo(new File(dstFilePath_r+newName));
				logger.debug("file: "+file.getName()+" has been renamed to: "+newName);
			}else if(oldName.indexOf("PNG")>-1||oldName.indexOf("JPG")>-1){
				newName = newName.replace(".block", ".index");
				File foler = new File(dstFilePath_i);
				foler.mkdirs();
				if(!foler.exists())
					try {
						foler.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				file.renameTo(new File(dstFilePath_i+newName));
				logger.debug("file: "+file.getName()+" has been renamed to: "+newName);
			}else{
				if(!oldName.endsWith(".xls")){
					newName = newName.replace(".block", ".index");
					File foler = new File(dstFilePath_o);
					foler.mkdirs();
					if(!foler.exists())
						try {
							foler.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					file.renameTo(new File(dstFilePath_o+newName));
					logger.debug("file: "+file.getName()+" has been renamed to: "+newName);
				}
				
			}
				
			namecode.put(file.getName(), newName);
			
		}
	
	}
	
	public static void prepare(File dir ,Map<String,String> namecode ){
		File files[] = dir.listFiles();
		
		for(File file:files){
			if(!file.isDirectory()){
				rename(file,namecode);
			}else{
				prepare(file,namecode);
			}
		}
	}
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("etc/properties/log4j.properties");
		long stime = System.currentTimeMillis();
		File dir = new File(srcFilePath);
		Map<String,String> namecode = new HashMap<String,String>();
		prepare(dir,namecode);
		com.cpw.test.file.Util.recordtoJxl(foler, namecode,"r");
		logger.debug("all job done, "+namecode.size()+" files renamed in "+(System.currentTimeMillis()-stime)/1000+" seconds");
	}
}
