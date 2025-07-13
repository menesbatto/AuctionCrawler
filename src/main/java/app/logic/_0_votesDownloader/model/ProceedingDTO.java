package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public class ProceedingDTO implements Serializable{

	private static final long serialVersionUID = -6013685603395381635L;

	private String number;
	private String year;
	private String description;
	
	
	
	public ProceedingDTO(String number, String year, String description) {
		super();
		this.number = number;
		this.year = year;
		this.description = description;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ProceedingDTO [number=" + number + ", year=" + year + ", description=" + description + "]";
	}
	
	
}
