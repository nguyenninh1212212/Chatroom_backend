package app.dto.member;

import java.util.UUID;

import app.dto.user.UserInfoDTO;
import app.entity.User;

public class MemberInfoDTO {
	private UUID id;
	private UserInfoDTO userInfo;
	static int count=0;
	public MemberInfoDTO() {
		count++;
	}
	public MemberInfoDTO(UUID id,User user) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.userInfo=new UserInfoDTO(user);
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UserInfoDTO getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfoDTO userInfo) {
		this.userInfo = userInfo;
	}
	
	public static void displayCount() {
        System.out.println("Count: " + count);  
    }
}
