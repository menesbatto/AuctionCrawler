package app.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(
	    name = "proceeding",
	    uniqueConstraints = @UniqueConstraint(columnNames = {"number", "year"})
	)
public class Proceeding {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String number;
	private String year;
	private String description;
	
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Proceeding [id=" + id + ", number=" + number + ", year=" + year + ", description=" + description + "]";
	}
	
	
	
	
	
}
