package com.cpw.xml;

import java.io.Serializable;
import java.util.Set;

public class Equipment implements Serializable{

	private Long id;
	private String name;
	private String code;
	private String type;
	private String manufacturer;
	private String loginName;
	private String loginPassword;
	private String ip;
	private int status =-1;
	private String description;
	
	private Set<Application> appSet;
	
	private Set<NetworkCard> networkCardSet;
	
	//private EquipPerformance performance;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/*public EquipPerformance getPerformance() {
		return performance;
	}
	public void setPerformance(EquipPerformance performance) {
		this.performance = performance;
	}*/
	public Equipment(String name, String code, String type,
			String manufacturer, String loginName, String loginPassword,
			String ip, int status, String description) {
		super();
		this.name = name;
		this.code = code;
		this.type = type;
		this.manufacturer = manufacturer;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.ip = ip;
		this.status = status;
		this.description = description;
	}
	public Equipment() {
		super();
	}
	public Equipment(Long id, String code) {
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
	public Set<NetworkCard> getNetworkCardSet() {
		return networkCardSet;
	}
	public void setNetworkCardSet(Set<NetworkCard> networkCardSet) {
		this.networkCardSet = networkCardSet;
	}
	
}
