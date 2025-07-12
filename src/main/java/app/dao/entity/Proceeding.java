package app.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Proceeding {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String number;
	private String year;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "Procedure [id=" + id + ", number=" + number + ", year=" + year + "]";
	}
	
	
	
	
	
}
