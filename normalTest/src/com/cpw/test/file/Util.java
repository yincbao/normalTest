package com.cpw.test.file;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

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

public class Util {
	
	public static void recordtoJxl(String path,Map<String,String> conMap,String xlsName){
		WritableWorkbook book = null;
		try{
			book = Workbook.createWorkbook(new File(path + File.separator +xlsName+"-"+ DateFormatUtils.format(new Date(), "yyyyMMDDHHmmss")+".xls"));
			WritableSheet ws = book.createSheet("report", 0);
			ws.setName("报告");
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

	public static void feilContent(WritableSheet ws, WritableCellFormat wc,
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
