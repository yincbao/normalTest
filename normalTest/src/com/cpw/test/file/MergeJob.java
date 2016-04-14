package com.cpw.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MergeJob implements Runnable {
	private static final Log logger = LogFactory.getLog(MergeJob.class);

	private AtomicInteger atomCounter = null;
	private Map<String,Integer> content = null;
	private int startPoint;
	private int endPoint;
	private String OUTPUT_FOLDER;
	private int count;
	private Map<String,String> recordsMap = null;
	
	public MergeJob(AtomicInteger atomCounter, Map<String, Integer> content,
			int startPoint, int endPoint,String OUTPUT_FOLDER,int count,Map<String,String> recordsMap) {
		super();
		this.atomCounter = atomCounter;
		this.content = content;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.OUTPUT_FOLDER = OUTPUT_FOLDER;
		this.count = count;
		this.recordsMap = recordsMap;
	}

	@Override
	public void run() {
		try{
			Iterator<String> ite = content.keySet().iterator();
			int idx = 0;
			logger.debug("task sequence: "+count+"  .Thread No."+Thread.currentThread().getId()+" subTask entered, starts from: "+startPoint+" ,ends at: "+endPoint);
			while(ite.hasNext()){
				//logger.debug("task sequence: "+count+"  .Thread No."+Thread.currentThread().getId()+" idx:"+idx);
				if(idx>=startPoint&&idx<endPoint){
					String outputName = ite.next();
					//�ļ����
					String our = recordsMap.get(outputName.substring(outputName.lastIndexOf("/")+1));
					int segments = content.get(outputName);
					File dir=new File(OUTPUT_FOLDER+"/"+our);
					if(!dir.exists()){
						dir.createNewFile();
						dir.setReadable(true);
						dir.setWritable(true);
						OutputStream os = new FileOutputStream(dir);
						
						for(int i=0;i<segments;i++){
							File file = new File(outputName+"_"+i+".!mv");
							if(!file.exists())
								continue;
							file.setWritable(true);
							file.setReadable(true);
							
							InputStream inputStream = new FileInputStream(file);
							byte[] byt = new byte[4096];
							int n = 0;
							while(-1!=(n=inputStream.read(byt))){
								os.write(byt, 0, n);        
							}
							inputStream.close();
							logger.debug("task sequence: "+count+" .Thread No."+Thread.currentThread().getId()+" merging segment["+i+"/"+segments+"]: "+file.getAbsolutePath()+" into==> "+dir.getAbsolutePath());
						}
						os.close();
						logger.debug("task sequence: "+count+" .Thread No."+Thread.currentThread().getId()+" idx:"+idx+" merging succeed, file absolutePath: "+dir.getAbsolutePath());
					}else{
						logger.debug("task sequence: "+count+" .Thread No."+Thread.currentThread().getId()+" idx:"+idx+" merging abandoned, file already exists: "+dir.getAbsolutePath());
					}
				}else{
					ite.next();
				}
				idx++;
			}
		}catch(Exception e){
			
		}finally{
			atomCounter.incrementAndGet();
		}

	}

	public void recordtoJxl(String path,Map<String,String> conMap){
		WritableWorkbook book = null;
		try{
			book = Workbook.createWorkbook(new File(path + File.separator +Thread.currentThread().getId()+"_"+DateFormatUtils.format(new Date(), "yyyyMMDDHHmmss")+".xls"));
			WritableSheet ws = book.createSheet("report", 0);
			ws.setName("����ȶԱ���");
            WritableFont font = new WritableFont(WritableFont.createFont("����"), 10, WritableFont.BOLD, false,
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

            ws.addCell(new Label(0, 0, "ԭ��", wc));
            ws.addCell(new Label(1, 0, "ת�������", wc));
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
}
