package app.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String firstname;
	private String lastname;
	
	private String password;
	
	private String email;
	
	private String username;
	
	private String gazzettaUsername;
	
	private String gazzettaPassword;
	
	private Integer toBeConfirm;
	

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getGazzettaPassword() {
		return gazzettaPassword;
	}

	public void setGazzettaPassword(String gazzettaPassword) {
		this.gazzettaPassword = gazzettaPassword;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", password=" + password
				+ ", mail=" + email + ", username=" + username + ", gazzettaUsername=" + getGazzettaUsername() + ", gazzettaPassword="
				+ gazzettaPassword + "]";
	}

	public String getGazzettaUsername() {
		return gazzettaUsername;
	}

	public void setGazzettaUsername(String gazzettaUsername) {
		this.gazzettaUsername = gazzettaUsername;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getToBeConfirm() {
		return toBeConfirm;
	}

	public void setToBeConfirm(Integer toBeConfirm) {
		this.toBeConfirm = toBeConfirm;
	}

	
	
	
	
}
