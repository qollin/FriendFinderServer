package de.inovex.academy.android.server.dto;

public class UserProfilePicture {

	private byte[] profilePicture;
	private User user;

	public byte[] getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
