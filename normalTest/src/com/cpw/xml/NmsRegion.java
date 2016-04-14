package com.cpw.xml;

import java.io.Serializable;
import java.util.Set;

public class NmsRegion implements Serializable{
	
	private Long id;
	private String code;
	private String name;
	private String description;
	private Long   parentId;
	private Set<Application> appSet;
	
	private String parentName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public NmsRegion() {
		super();
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public NmsRegion(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}
	public Set<Application> getAppSet() {
		return appSet;
	}
	public void setAppSet(Set<Application> appSet) {
		this.appSet = appSet;
	}
	
	
}
