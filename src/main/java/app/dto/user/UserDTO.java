package app.dto.user;

import app.entity.User;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserDTO {
	private String username;
	private String password;
	private String fullname;
	private String email;
	
	
	
	public UserDTO(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.fullname = user.getFullname();
		this.email = user.getEmail();
	}

	
	
}
