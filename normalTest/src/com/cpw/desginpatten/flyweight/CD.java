package com.cpw.desginpatten.flyweight;

import java.util.Date;

public interface CD {
	public Artist getArtist();
	public void setArtist(Artist artist);
	public String getName() ;
	public void setName(String name) ;
	public Date getDate() ;
	public void setDate(Date date);
}
