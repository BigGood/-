package sun.model;

public class EditModel {
	private String password;
	private String newPassword;
	public String getNewPassword() {
		return newPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
