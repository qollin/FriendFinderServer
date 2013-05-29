package de.inovex.academy.android.server.dto;

public class User {
	private String login;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	@Override
	public String toString() {
		return login;
	}
}
