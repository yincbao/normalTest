package com.jdk8.lamdba;

/**
 * ClassName:User.java
 * @description
 * @author: yin_changbao
 * @Date  : Oct 27, 2015
 *
 */
public class User {
	
	public String id = "xxxxxx-default";

	/**
	 * @param id
	 */
	public User(String id) {
		super();
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}

	
	
}
