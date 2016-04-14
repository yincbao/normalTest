package com.cpw.test.file.xplay;

public class ItemJob implements Runnable{

	
	private String itemName;
	private String homePage;
	private int pageCount;
	
	public ItemJob(String itemName, String homePage, int pageCount) {
		super();
		this.itemName = itemName;
		this.homePage = homePage;
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}



	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	@Override
	public void run() {
		try {
			int idx = 0;
			if(itemName.indexOf("/")>-1){
				String lastpare = itemName.substring(itemName.lastIndexOf("/"));
				if(lastpare.indexOf("index")>-1&&lastpare.indexOf(".html")>-1){
					idx = Integer.parseInt(lastpare.substring(lastpare.lastIndexOf("index")+5,lastpare.lastIndexOf(".html")));
				}
			}
			
			XPlayUrl.superMultiThreadGetXplayUrl(homePage,itemName,idx,pageCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
