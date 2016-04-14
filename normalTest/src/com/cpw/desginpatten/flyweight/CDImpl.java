package com.cpw.desginpatten.flyweight;

import java.util.Date;

public class CDImpl implements CD{
	
	private String name;
	private Date date;
	public Artist artist;
	@Override
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist){
		this.artist = artist;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
