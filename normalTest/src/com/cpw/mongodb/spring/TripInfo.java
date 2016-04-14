package com.cpw.mongodb.spring;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="bt_trip_info")
public class TripInfo {
	@Id
	private long id;
	
	private String end_address;
	
	private Double end_latitude;

	private String end_longitude;
	
	private Date end_time;
	
	private Float fuel_consumption;
	
	private Date last_update_time;
	
	private Long milleage;
	
	private String request_type;
	
	private Float socre;
	
	private String start_address;
	
	private Double start_latitude;
	
	private Double start_longtitude;
	
	private Date start_time;
	
	private String trip_id;
	
	private Long account;
	
	private Long driver_info;
	
	private Long vehicle_info;
	
	private Integer off_line;
	
	private Long device_id;
	
	private String vin;
	
	private List<Location> trip_location;
	
	public class Location{
		private Long id;
		private String address;
		private Double fuel;
		private Long idling_time;
		private Date last_update_time;
		private Double latitude;
		private Double longitude;
		private Double mileage;
		private Float score;
		private Date time;
		private Integer vehicle_status;
		private Float battery_voltage;
		private Float engine_coolant_temperature;
		private Float enginerpm;
		private Float fuel_level;
		private Float fuel_pressure;
		private Float intake_air_emperature;
		private Float throttle_position;
		private Float vehicle_maf;
		private Float vehicle_speed;
		private Long vehicle_info;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public Double getFuel() {
			return fuel;
		}
		public void setFuel(Double fuel) {
			this.fuel = fuel;
		}
		public Long getIdling_time() {
			return idling_time;
		}
		public void setIdling_time(Long idling_time) {
			this.idling_time = idling_time;
		}
		public Date getLast_update_time() {
			return last_update_time;
		}
		public void setLast_update_time(Date last_update_time) {
			this.last_update_time = last_update_time;
		}
		public Double getLatitude() {
			return latitude;
		}
		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}
		public Double getLongitude() {
			return longitude;
		}
		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}
		public Double getMileage() {
			return mileage;
		}
		public void setMileage(Double mileage) {
			this.mileage = mileage;
		}
		public Float getScore() {
			return score;
		}
		public void setScore(Float score) {
			this.score = score;
		}
		public Date getTime() {
			return time;
		}
		public void setTime(Date time) {
			this.time = time;
		}
		public Integer getVehicle_status() {
			return vehicle_status;
		}
		public void setVehicle_status(Integer vehicle_status) {
			this.vehicle_status = vehicle_status;
		}
		public Float getBattery_voltage() {
			return battery_voltage;
		}
		public void setBattery_voltage(Float battery_voltage) {
			this.battery_voltage = battery_voltage;
		}
		public Float getEngine_coolant_temperature() {
			return engine_coolant_temperature;
		}
		public void setEngine_coolant_temperature(Float engine_coolant_temperature) {
			this.engine_coolant_temperature = engine_coolant_temperature;
		}
		public Float getEnginerpm() {
			return enginerpm;
		}
		public void setEnginerpm(Float enginerpm) {
			this.enginerpm = enginerpm;
		}
		public Float getFuel_level() {
			return fuel_level;
		}
		public void setFuel_level(Float fuel_level) {
			this.fuel_level = fuel_level;
		}
		public Float getFuel_pressure() {
			return fuel_pressure;
		}
		public void setFuel_pressure(Float fuel_pressure) {
			this.fuel_pressure = fuel_pressure;
		}
		public Float getIntake_air_emperature() {
			return intake_air_emperature;
		}
		public void setIntake_air_emperature(Float intake_air_emperature) {
			this.intake_air_emperature = intake_air_emperature;
		}
		public Float getThrottle_position() {
			return throttle_position;
		}
		public void setThrottle_position(Float throttle_position) {
			this.throttle_position = throttle_position;
		}
		public Float getVehicle_maf() {
			return vehicle_maf;
		}
		public void setVehicle_maf(Float vehicle_maf) {
			this.vehicle_maf = vehicle_maf;
		}
		public Float getVehicle_speed() {
			return vehicle_speed;
		}
		public void setVehicle_speed(Float vehicle_speed) {
			this.vehicle_speed = vehicle_speed;
		}
		public Long getVehicle_info() {
			return vehicle_info;
		}
		public void setVehicle_info(Long vehicle_info) {
			this.vehicle_info = vehicle_info;
		}
		
	}
	
	private List<Incident> trip_incident;
	
	public class Incident{
		private Long id;
		private Integer incident_type;
		private Date last_update_time;
		private Double latitude;
		private Double longitude;
		private Date time;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Integer getIncident_type() {
			return incident_type;
		}
		public void setIncident_type(Integer incident_type) {
			this.incident_type = incident_type;
		}
		public Date getLast_update_time() {
			return last_update_time;
		}
		public void setLast_update_time(Date last_update_time) {
			this.last_update_time = last_update_time;
		}
		public Double getLatitude() {
			return latitude;
		}
		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}
		public Double getLongitude() {
			return longitude;
		}
		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}
		public Date getTime() {
			return time;
		}
		public void setTime(Date time) {
			this.time = time;
		}
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEnd_address() {
		return end_address;
	}

	public void setEnd_address(String end_address) {
		this.end_address = end_address;
	}

	public Double getEnd_latitude() {
		return end_latitude;
	}

	public void setEnd_latitude(Double end_latitude) {
		this.end_latitude = end_latitude;
	}

	public String getEnd_longitude() {
		return end_longitude;
	}

	public void setEnd_longitude(String end_longitude) {
		this.end_longitude = end_longitude;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Float getFuel_consumption() {
		return fuel_consumption;
	}

	public void setFuel_consumption(Float fuel_consumption) {
		this.fuel_consumption = fuel_consumption;
	}

	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}

	public Long getMilleage() {
		return milleage;
	}

	public void setMilleage(Long milleage) {
		this.milleage = milleage;
	}

	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public Float getSocre() {
		return socre;
	}

	public void setSocre(Float socre) {
		this.socre = socre;
	}

	public String getStart_address() {
		return start_address;
	}

	public void setStart_address(String start_address) {
		this.start_address = start_address;
	}

	public Double getStart_latitude() {
		return start_latitude;
	}

	public void setStart_latitude(Double start_latitude) {
		this.start_latitude = start_latitude;
	}

	public Double getStart_longtitude() {
		return start_longtitude;
	}

	public void setStart_longtitude(Double start_longtitude) {
		this.start_longtitude = start_longtitude;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public String getTrip_id() {
		return trip_id;
	}

	public void setTrip_id(String trip_id) {
		this.trip_id = trip_id;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

	public Long getDriver_info() {
		return driver_info;
	}

	public void setDriver_info(Long driver_info) {
		this.driver_info = driver_info;
	}

	public Long getVehicle_info() {
		return vehicle_info;
	}

	public void setVehicle_info(Long vehicle_info) {
		this.vehicle_info = vehicle_info;
	}

	public Integer getOff_line() {
		return off_line;
	}

	public void setOff_line(Integer off_line) {
		this.off_line = off_line;
	}

	public Long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(Long device_id) {
		this.device_id = device_id;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public List<Location> getTrip_location() {
		return trip_location;
	}

	public void setTrip_location(List<Location> trip_location) {
		this.trip_location = trip_location;
	}

	public List<Incident> getTrip_incident() {
		return trip_incident;
	}

	public void setTrip_incident(List<Incident> trip_incident) {
		this.trip_incident = trip_incident;
	}
	
}
