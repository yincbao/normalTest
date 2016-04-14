package com.cpw.xml;

import java.io.Serializable;

public class NetworkCard implements Serializable{

	   private Long id;
	   private String equipmentCode;
	   private String name;
	   private String code;
	   private String ip;
	   private String gateway;
	   private String mask;
	   private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public NetworkCard(String equipmentCode, String name, String code,
			String ip, String gateway, String mask, String description) {
		super();
		this.equipmentCode = equipmentCode;
		this.name = name;
		this.code = code;
		this.ip = ip;
		this.gateway = gateway;
		this.mask = mask;
		this.description = description;
	}
	public NetworkCard() {
		super();
	}
	
	
}
