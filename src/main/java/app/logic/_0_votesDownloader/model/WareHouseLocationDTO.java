package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public class WareHouseLocationDTO implements Serializable{
	

	private static final long serialVersionUID = -4530986187829972403L;
	private String street;
	private String number;
	private String cap;
	private String province;
	private String city;
	
	private String lat;
	private String lon;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "WareHouseLocationDTO [street=" + street + ", number=" + number + ", cap=" + cap + ", province="
				+ province + ", city=" + city + ", lat=" + lat + ", lon=" + lon + "]";
	}
	
	
	

}
