package app.dto.member;

import java.util.UUID;

import app.dto.user.UserFEDTO;
import app.dto.user.UserInfoDTO;
import app.entity.Member;
import app.entity.User;

public class MemberInfoDTO {
	private UUID id;
	private UserFEDTO userFEDTO;



	public MemberInfoDTO(Member member) {
		this.id = member.getId();
		this.userFEDTO = new UserFEDTO(member.getUser());
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UserFEDTO getUserInfo() {
		return userFEDTO;
	}
	public void setUserInfo(UserFEDTO userFEDTO) {
		this.userFEDTO = userFEDTO	;
	}
	
}
