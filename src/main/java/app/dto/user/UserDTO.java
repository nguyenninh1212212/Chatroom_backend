package app.dto.user;

public class UserDTO {
	private String username;
	private String password;
	private String fullname;
	private String email;
	
	
	
	public UserDTO(String username, String password, String fullname, String email) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
