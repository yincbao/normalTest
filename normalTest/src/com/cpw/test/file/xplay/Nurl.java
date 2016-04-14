package com.cpw.test.file.xplay;


public class Nurl {

	
	private String name;
	private String url;
	private String img="D:/tmp/timg.png";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	@Override
	public int hashCode(){
		int hashcode = 17;
		hashcode = 31*hashcode+this.url.hashCode();
		hashcode = 31*hashcode + this.url.hashCode();
		return hashcode;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if(this==obj)
			return true;
		if(this.hashCode()!=obj.hashCode()){
			return false;
		}
		if(obj instanceof Nurl){
			Nurl tmp = (Nurl)obj;
			if(tmp.url == this.url||tmp.url.equals(this.url))
				return true;
		}
		return false;
	}
}
