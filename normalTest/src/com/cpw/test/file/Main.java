package com.cpw.test.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class Main {

	public static void recursion(File dir,Map<String,String> names){
		File files[] = dir.listFiles();
		
		for(File file:files){
			if(file.isDirectory()){
				recursion(file,names);
			}else{
				String path = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(File.separator));
				//String newName = DateFormatUtils.format(new Date(), "yyyymmddHHmmssSSS");
				String oldName = file.getName();
				String newname = oldName.replaceAll(".rmvb", "\\s");
				String suffix = oldName.substring(0,oldName.lastIndexOf("."));
				renameFile( path, oldName,newname );
				//names.put(oldName, newName);
				
			}
			
		}
	}

	public static void main(String[] args) {
		String root = "J:/p2pcache";
		Map<String,String> map = new HashMap<String,String>();
		recursion(new File(root),map);
	}
    private static void wirte2Txt(String root, Map<String, String> map) throws IOException {
		File recoder = new File(root+File.separator+DateFormatUtils.format(new Date(), "HHmmssSSS")+".txt");
		recoder.createNewFile();
		recoder.setWritable(true);
		recoder.setReadable(true);
		FileWriter fw=new FileWriter(recoder);
		BufferedWriter buffw=new BufferedWriter(fw);
		PrintWriter pw=new PrintWriter(buffw);
		Iterator<String> ite = map.keySet().iterator();
		int i = 0;
		StringBuffer sb = new StringBuffer();
		while(ite.hasNext()) {
			String key = ite.next();
			sb.append( key+"----"+map.get(key)+" | ") ;
			if(i==4){
				sb.append("\r\n");
				i = 0;
			}
			
			i++;
		}
		pw.println(sb.toString());
		fw.write(sb.toString()); 
		fw.flush();//
		pw.close();
		buffw.close();
		fw.close();
	}

	public static void renameFile(String path,String oldname,String newname){ 
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
            File oldfile=new File(path+"/"+oldname); 
            File newfile=new File(path+"/"+newname); 
            if(!oldfile.exists()){
                return;//重命名文件不存在
            }
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
            	newfile = new File(path+"/"+RandomStringUtils.randomNumeric(2)+newname); 
            else{ 
                oldfile.renameTo(newfile); 
            } 
        }else{
            System.out.println("新文件名和旧文件名相同...");
        }
    }
	
}
