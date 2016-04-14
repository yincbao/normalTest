package com.cpw.desginpatten.flyweight;

public class ArtistImpl implements Artist{
	public String aName;



	@Override
	public void setAname(String aName) {
		this.aName = aName;
		
	}

	@Override
	public String getAname() {
		return this.aName;
	}
}
