package app.logic._0_credentialsSaver.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ConfirmUser {

	private String username;
	
	private String rnd;

	public ConfirmUser() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRnd() {
		return rnd;
	}

	public void setRnd(String rnd) {
		this.rnd = rnd;
	}

	@Override
	public String toString() {
		return "ConfirmUser [username=" + username + ", rnd=" + rnd + "]";
	}

	
}
