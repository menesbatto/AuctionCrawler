package app.logic._0_credentialsSaver.model;

public class Credentials {

	private static final long serialVersionUID = -8182168610565154395L;
	
	private String username;
	
	private String password;

	public Credentials() {
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Credentials [username=" + username + ", password=" + password + "]";
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	
	
	
}
