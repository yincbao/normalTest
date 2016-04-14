package com.cpw.test.file.xplay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Util {

	/**
	 * DEFAULT_PATH + File.separator + DateFormatUtils.format(new Date(), "yyyyMMDDHHmmss")+".xls"
	 * @param xlspath
	 * @param xplayResourceSet
	 * @param whole
	 */
	public static void write2Xls(String xlspath,Set<Nurl> xplayResourceSet,List<Nurl> whole ){
		WritableWorkbook book = null;
		try{
			book = Workbook.createWorkbook(new File(xlspath));
			WritableSheet ws = book.createSheet("report", 0);
			ws.setName("ȥ�غ�");
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

            ws.addCell(new Label(0, 0, "xplay", wc));
            ws.addCell(new Label(1, 0, "name", wc));
            ws.addCell(new Label(2, 0, "poster", wc));
            
            feilContent(ws,wcon,xplayResourceSet);
            
            
            WritableSheet ws2 = book.createSheet("report", 0);
            ws2.setName("ȥ��ǰ");
            ws2.addCell(new Label(0, 0, "xplay", wc));
            ws2.addCell(new Label(1, 0, "name", wc));
            ws2.addCell(new Label(2, 0, "poster", wc));
            feilContent(ws2,wcon,whole);
            book.write();
            book.close();
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 
	 * @param ws
	 * @param wc
	 * @param whole
	 * @param posterPath DEFAULT_PATH+name2Code.get(nurl.getName())+".png"
	 */
	private static void feilContent(WritableSheet ws, WritableCellFormat wc,
			List<Nurl> whole) {
		
		int row = 1;
		for(Nurl nurl:whole){
			 try {
				ws.addCell(new Label(0, row,nurl.getUrl(), wc));
				ws.addCell(new Label(1, row,nurl.getName(), wc));
				ws.addCell(new Label(3, row,nurl.getImg(), wc));
				//ͼƬ���������ļ��ˣ�̫��
				//row+=addPictureToExcel( ws, new File(nurl.getImg()) , row, 2);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	         row++;
		}
		
	}

	/**
	 * DEFAULT_PATH+name2Code.get(saxPre.getName())+".png"
	 */
	private static void feilContent(WritableSheet ws, WritableCellFormat wc,
			Set<Nurl> xplayResourceSet) {
		Iterator<Nurl> ite = xplayResourceSet.iterator();
		int row = 1;
		while(ite.hasNext()){
			Nurl saxPre = ite.next();
			 try {
				//row+=addPictureToExcel( ws, new File(saxPre.getImg()) , row, 2);
				ws.addCell(new Label(0, row,saxPre.getUrl(), wc));
				ws.addCell(new Label(1, row,saxPre.getName(), wc));
				ws.addCell(new Label(3, row,saxPre.getImg(), wc));
				//ws.mergeCells(0, row, 1, rowstaa);
				
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	         row++;
		}
		
	}
    /**
    * ����ͼƬ��EXCEL
    * 
    * @param picSheet sheet
    * @param pictureFile ͼƬfile����
    * @param cellRow ����
    * @param cellCol ����
     * @return 
    * @throws Exception ����
    */
   private static double addPictureToExcel(WritableSheet picSheet, File pictureFile, int cellRow, int cellCol)
       throws Exception {
       // ��ʼλ��
       double picBeginCol = cellCol;
       double picBeginRow = cellRow;
       // ͼƬʱ��ĸ߶ȣ����(��xls�и߶�һ��1���һ�и߻���һ�п�)
       double picCellWidth = 0.0;
       double picCellHeight = 0.0;
       // ����ͼƬ
       BufferedImage picImage = ImageIO.read(pictureFile);
       // ȡ��ͼƬ�����ظ߶ȣ����
       int picWidth = picImage.getWidth();
       int picHeight = picImage.getHeight();
       
       // ����ͼƬ��ʵ�ʿ��
       int picWidth_t = picWidth * 32;  //�����ʵ��ֵ��ԭ�?�����
       for (int x = 0; x < 1234; x++) {
           int bc = (int) Math.floor(picBeginCol + x);
           // �õ���Ԫ��Ŀ��
           int v = picSheet.getColumnView(bc).getSize();
           double offset0_t = 0.0;
           if (0 == x)
               offset0_t = (picBeginCol - bc) * v;
           if (0.0 + offset0_t + picWidth_t > v) {
               // ʣ���ȳ���һ����Ԫ��Ŀ��
               double ratio_t = 1.0;
               if (0 == x) {
                   ratio_t = (0.0 + v - offset0_t) / v;
               }
              picCellWidth += ratio_t;
               picWidth_t -= (int) (0.0 + v - offset0_t);
           } else { //ʣ���Ȳ���һ����Ԫ��Ŀ��
               double ratio_r = 0.0;
               if (v != 0)
                   ratio_r = (0.0 + picWidth_t) / v;
               picCellWidth += ratio_r;
               break;
           }
       }        
       // ����ͼƬ��ʵ�ʸ߶�
       int picHeight_t = picHeight * 15;
       for (int x = 0; x < 1234; x++) {
           int bc = (int) Math.floor(picBeginRow + x);
           // �õ���Ԫ��ĸ߶�
           int v = picSheet.getRowView(bc).getSize();
           double offset0_r = 0.0;
           if (0 == x)
               offset0_r = (picBeginRow - bc) * v;
           if (0.0 + offset0_r + picHeight_t > v) {
               // ʣ��߶ȳ���һ����Ԫ��ĸ߶�
               double ratio_q = 1.0;
               if (0 == x)
                   ratio_q = (0.0 + v - offset0_r) / v;
               picCellHeight += ratio_q;
               picHeight_t -= (int) (0.0 + v - offset0_r);
           } else {//ʣ��߶Ȳ���һ����Ԫ��ĸ߶�
               double ratio_m = 0.0;
               if (v != 0)
                   ratio_m = (0.0 + picHeight_t) / v;
               picCellHeight += ratio_m;
               break;
           }
       }
       //���һ��ͼƬ����
       WritableImage image = new WritableImage(picBeginCol, picBeginRow,
               picCellWidth, picCellHeight, pictureFile);
       // ��ͼƬ���뵽sheet
       picSheet.addImage(image);
       return picCellHeight;
   }
   
}
