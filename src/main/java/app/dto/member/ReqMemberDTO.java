package app.dto.member;

import java.time.LocalDateTime;
import java.util.UUID;

import app.dto.user.UserInfoDTO;
import app.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReqMemberDTO  {
	 private UUID id;

	    private UserInfoDTO user;
	    private LocalDateTime created;
	    private LocalDateTime updated;
	    private LocalDateTime deleted;
	    
		public ReqMemberDTO(UUID id,User user, LocalDateTime created) {
			this.user=new UserInfoDTO(user);
			this.id=id;
			this.created = created;
		}

	    
	    
}
