package app.dto.member;

import java.util.UUID;

import app.dto.user.UserInfoDTO;
import app.entity.User;

public class MembersDTO {
	private UUID id;
	private UserInfoDTO user;
	public MembersDTO(UUID id,User user) {
		super();
		this.id = id;
		this.user = new UserInfoDTO(user);
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UserInfoDTO getUser() {
		return user;
	}
	public void setUser(UserInfoDTO user) {
		this.user = user;
	}
	
	
}
