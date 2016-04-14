package com.cpw.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class QvodAdp {
	
	private static final Log logger = LogFactory.getLog(QvodAdp.class);
	private static Pattern QVOD_PATTERN = Pattern.compile("(.*)_\\d+\\.!mv");
	private static final String OUTPUT_FOLDER = "I:/Media/201411208";
	
	/**
	 * 
	 * @param path
	 * @param outputName
	 * @param segments
	 * @param segmentstamp1  _
	 * @param segmentstamp2  .!mv
	 * @throws Exception 
	 */
	public void  bonding(String outputFolder,Map<String,Integer> map ) throws Exception{
		
		Iterator<String> ite = map.keySet().iterator();
		while(ite.hasNext()){
			String outputName = ite.next();
			String our = outputName.substring(outputName.lastIndexOf("/")+1);
			/*String suffix = outputName.substring(outputName.lastIndexOf(".")+1);
			Map<String,String> nameTrans = new HashMap<String, String>();
			if(isChineseChar(our)){
				String transferedName = DateFormatUtils.format(new Date(), "HHmmssSSS")+RandomStringUtils.randomNumeric(4)+"."+suffix;
				nameTrans.put(our, transferedName);
				our = transferedName;
			}else{
				nameTrans.put(our, our);
			}
			if(!nameTrans.isEmpty()){
				recordtoJxl( OUTPUT_FOLDER, nameTrans);
			}*/
			int segments = map.get(outputName);
			File dir=new File(OUTPUT_FOLDER+"/"+our);
			if(!dir.exists()){
				dir.createNewFile();
			}
			dir.setReadable(true);
			dir.setWritable(true);
			OutputStream os = new FileOutputStream(dir);
			
			for(int i=0;i<segments;i++){
				File file = new File(outputName+"_"+i+".!mv");
				file.setWritable(true);
				file.setReadable(true);
				
				InputStream inputStream = new FileInputStream(file);
				byte[] byt = new byte[4096];
				int n = 0;
				while(-1!=(n=inputStream.read(byt))){
					os.write(byt, 0, n);        
				}
				inputStream.close();
				logger.debug("merging segment["+i+"/"+segments+"]: "+file.getAbsolutePath()+" into==> "+dir.getAbsolutePath());
			}
			os.close();
			logger.debug("merging succeed, file absolutePath: "+dir.getAbsolutePath());
		}
	}
	
	/**
	 * 
	 * @param outputFolder
	 * @param map  <"d:/uuu/sss.rmvb",90>
	 * @throws Exception
	 */
	public void  newBonding(String outputFolder,Map<String,Integer> map ) throws Exception{
		//record
		
		Iterator<String> ite = map.keySet().iterator();
		Map<String,String> recordMap = new HashMap<String, String>();
		while(ite.hasNext()){
			String key = ite.next();
			String our = key.substring(key.lastIndexOf("/")+1);
			recordMap.put(our, DateFormatUtils.format(new Date(), "HHmmssSSS")+RandomStringUtils.randomNumeric(4)+".block");
		}
		recordtoJxl(outputFolder,recordMap);
		AtomicInteger atomCounter = new AtomicInteger();
		int loopTimes = getCpuCoreAmonut();
		int increment = map.size()/loopTimes;
		int count = 1;
		for(int j=0;j<map.size();j+=increment){
			Thread th = null;
			if(count==loopTimes){
				th = new Thread(new MergeJob(atomCounter, map, j, j+increment+(map.size()%loopTimes),OUTPUT_FOLDER,count,recordMap));
				th.start();
				logger.debug("Sub-tasks,"+count+"/"+loopTimes+" launched!");
				break;
			}else{
				th = new Thread(new MergeJob(atomCounter, map, j, j+increment,OUTPUT_FOLDER,count,recordMap));
				th.start();
			}
			
			count++;
		}
		while(true){
			if(atomCounter.get() == loopTimes){
				logger.debug("Job done, all "+map.size()+" files merged into "+OUTPUT_FOLDER);
				break;
			}else {
				logger.debug(atomCounter.get()+" of"+ loopTimes+" sub taks done.");
				Thread.sleep(10000);
			}
		}
		
		
	}


	public void recursion(File dir,Map<String,Integer> map){
		File files[] = dir.listFiles();
		
		List<String> li = new ArrayList<String>();
		for(File file:files){
			if(file.isDirectory()){
				recursion(file,map);
			}else{
				String tmp = getOutputName(file.getName());
				if(!StringUtils.isEmpty(tmp)){
					if(!li.contains(file.getName())){
						tmp = file.getParentFile().getAbsolutePath()+"/"+tmp;
						logger.debug("get segments file named: "+file.getParentFile().getAbsolutePath()+"/"+file.getName());
						int c = map.get(tmp)==null?0:map.get(tmp);
						map.put(tmp, c+1);
						li.add(file.getName());
					}
				}
			}
			
		}
	}

	private String getOutputName(String name) {
		Matcher matcher = QVOD_PATTERN.matcher(name);
		if(matcher.find()){
			return matcher.group(1);
		}
		return null;
	}
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("etc/properties/log4j.properties");
		QvodAdp qvod = new QvodAdp();
		//qvod.clearOutput();
		Map<String,Integer> map = new HashMap<String,Integer>();
		qvod.recursion(new File("F:/p2pcache"),map);
		try {
			qvod.newBonding(OUTPUT_FOLDER,map );
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public boolean clearOutput(){
		try{
			File dir=new File(OUTPUT_FOLDER);
			return dir.delete();
		}catch(Exception e){
			return false;
		}
		
	}
	
	public static boolean isChineseChar(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
       Matcher m=p.matcher(str); 
       if(m.find()){ 
           temp =  true;
        }
        return temp;
    }
	
	public void recordtoJxl(String path,Map<String,String> conMap){
		WritableWorkbook book = null;
		try{
			book = Workbook.createWorkbook(new File(path + File.separator + DateFormatUtils.format(new Date(), "yyyyMMDDHHmmss")+".xls"));
			WritableSheet ws = book.createSheet("report", 0);
			ws.setName("差异比对报告");
            WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE);
            WritableCellFormat wc = new WritableCellFormat();
            wc.setAlignment(Alignment.CENTRE);
            wc.setBackground(Colour.SEA_GREEN);
            wc.setBorder(Border.ALL, BorderLineStyle.THIN);
            wc.setFont(font);
            
            WritableCellFormat wcon = new WritableCellFormat();
            wc.setAlignment(Alignment.LEFT);
            wc.setBorder(Border.ALL, BorderLineStyle.THIN);
            wc.setFont(font);

            ws.addCell(new Label(0, 0, "原名", wc));
            ws.addCell(new Label(1, 0, "转化后名称", wc));
            feilContent(ws,wcon,conMap);
            book.write();
            book.close();
		}catch(Exception e){
			
		}
	}

	private void feilContent(WritableSheet ws, WritableCellFormat wc,
			Map<String, String> conMap) throws RowsExceededException, WriteException {
		Iterator<String> ite = conMap.keySet().iterator();
		int row = 1;
		while(ite.hasNext()){
			String saxPre = ite.next();
			 ws.addCell(new Label(0, row,saxPre, wc));
	         ws.addCell(new Label(1, row,conMap.get(saxPre), wc));
	         row++;
		}
		
	}
	
	public int getCpuCoreAmonut(){
		return Runtime.getRuntime().availableProcessors()<=2?2:Runtime.getRuntime().availableProcessors();
	}
	
}
