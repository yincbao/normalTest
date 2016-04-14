package com.cpw.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.txw2.annotation.XmlElement;

/**
 * NmsApplication entity. @author MyEclipse Persistence Tools
 */
@XmlElement
public class Application implements java.io.Serializable {

    // Fields

    private Long id;

    private String name;

    private String code;
   @Deprecated
    private String equipmentCode;
   @Deprecated
    private String equipmentName;
   @Deprecated
    private Long regionId = -1L;
    @Deprecated
    private String regionName;
    @XmlAttribute(name="status")
    private Integer status = -1;

    private String description;
    
    private Equipment equipment;
    
    private NmsRegion region;
    
    /**应用类型:SD SE VCM LEC RTAPA CEA QRM GCM**/
    @XmlAttribute(name="name")
    private String type;
    
    // Constructors
    @XmlTransient
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public NmsRegion getRegion() {
		return region;
	}

	public void setRegion(NmsRegion region) {
		this.region = region;
	}

	/** default constructor */
    public Application() {
    }

    /** full constructor */
    public Application(String name, String code, String equipmentCode, Long regionId, Integer status,
            String description) {
        this.name = name;
        this.code = code;
        this.equipmentCode = equipmentCode;
        this.regionId = regionId;
        this.status = status;
        this.description = description;
    }

    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Deprecated
    public String getEquipmentCode() {
        return this.equipmentCode;
    }
    @Deprecated
    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }
    @Deprecated
    public Long getRegionId() {
        return this.regionId;
    }
    @Deprecated
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @XmlTransient
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Deprecated
	public String getEquipmentName() {
		return equipmentName;
	}
    @Deprecated
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
    @Deprecated
	public String getRegionName() {
		return regionName;
	}
    @Deprecated
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Application(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}

}