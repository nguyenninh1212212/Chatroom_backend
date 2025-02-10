package app.dto.member;

import java.time.LocalDateTime;
import java.util.UUID;

import app.dto.user.UserInfoDTO;
import app.entity.User;

public class ReqMemberDTO {
	 private UUID id;

	    private UserInfoDTO user;

	    
	    private LocalDateTime created;
	    private LocalDateTime updated;
	    private LocalDateTime deleted;
	    
	    public ReqMemberDTO() {};
		public ReqMemberDTO(UUID id,User user, LocalDateTime created) {
			this.id=id;
			this.user = new UserInfoDTO(user);
			this.created = created;
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
		public LocalDateTime getCreated() {
			return created;
		}
		public void setCreated(LocalDateTime created) {
			this.created = created;
		}
		public LocalDateTime getUpdated() {
			return updated;
		}
		public void setUpdated(LocalDateTime updated) {
			this.updated = updated;
		}
		public LocalDateTime getDeleted() {
			return deleted;
		}
		public void setDeleted(LocalDateTime deleted) {
			this.deleted = deleted;
		}
	    
	    
}
