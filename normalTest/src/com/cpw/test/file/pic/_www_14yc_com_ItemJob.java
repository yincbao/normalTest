package com.cpw.test.file.pic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cpw.test.file.pic.item.impl._www_14yc_com_item;
import com.cpw.test.file.pic.page.IFetchPage;
import com.cpw.test.file.xplay.Http;

public class _www_14yc_com_ItemJob implements IitemDividerJob {

	private static final Log logger = LogFactory.getLog(_www_14yc_com_ItemJob.class);
	
	/** ÿ��item Thread��ദ��page����*/
	public static int MAX_WORKLOAD_PRE_ITEM_THREAD = 30;
	
	private IFetchPage fetchPage;
	
	
	public IFetchPage getFetchPage() {
		return fetchPage;
	}

	public void setFetchPage(IFetchPage fetchPage) {
		this.fetchPage = fetchPage;
	}
	
	@Override
	public void preLaunch(String homepage, String[] items) {
		for(String item:items){
			String lv1 =  Http.getRespInStr(homepage+"/"+item);
			int pageCount = fetchPage.fetchTotalPage(lv1);
			if(pageCount>MAX_WORKLOAD_PRE_ITEM_THREAD){
				int subCount = pageCount/MAX_WORKLOAD_PRE_ITEM_THREAD;
				subCount = pageCount%MAX_WORKLOAD_PRE_ITEM_THREAD==0?subCount:(subCount+1);
				logger.info("item: "+item+" is too large: "+pageCount+", divided into: "+subCount+" items");
				for(int j=0;j<subCount;j++){
					logger.info(item+"/index"+(j*MAX_WORKLOAD_PRE_ITEM_THREAD)+".html"+" should start as anther lv1 Thread");
					new Thread(new _www_14yc_com_item(item+"/index_"+(j*MAX_WORKLOAD_PRE_ITEM_THREAD)+".html",homepage,MAX_WORKLOAD_PRE_ITEM_THREAD)).start();
				}
			}else{
				//itmeThread.add(item);
				logger.info("item: "+item+" :"+pageCount+" should start only lv1 Thread ");
				new Thread(new _www_14yc_com_item(item,homepage,pageCount)).start();
			}
		}
	}

}
