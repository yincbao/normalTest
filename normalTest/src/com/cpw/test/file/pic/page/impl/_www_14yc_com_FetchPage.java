package com.cpw.test.file.pic.page.impl;

import java.util.regex.Matcher;

import com.cpw.test.file.pic.Constant_www_14yc_com;
import com.cpw.test.file.pic.page.IFetchPage;

public class _www_14yc_com_FetchPage implements IFetchPage {

	@Override
	public int fetchTotalPage(String lv1) {
		Matcher matcher = Constant_www_14yc_com.getPgAr.matcher(lv1);
		int pageNum = 0;
		if(matcher.find()){
			pageNum =Integer.parseInt(matcher.group(1)) ;
		}
		return pageNum;
	}

}
