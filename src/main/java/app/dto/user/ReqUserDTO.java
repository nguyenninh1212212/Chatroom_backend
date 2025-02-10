package app.dto.user;

import java.time.LocalDateTime;
import java.util.UUID;


public class ReqUserDTO {
	    private UUID id;
	    private String username;
	    private String fullname;
	    private String email;
	    private LocalDateTime created;
	    private String updated;
	    private String deleted;
	    
	    public ReqUserDTO(UUID id, String username, String email,String fullname, LocalDateTime created) {
	        this.id = id;
	        this.username = username;
	        this.fullname=fullname;
	        this.email = email;
	        this.created=created;
	    }
	    
	    
		public String getFullname() {
			return fullname;
		}


		public void setFullname(String fullname) {
			this.fullname = fullname;
		}


		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public LocalDateTime getCreated() {
			return created;
		}
		public void setCreated(LocalDateTime created) {
			this.created = created;
		}
		public String getUpdated() {
			return updated;
		}
		public void setUpdated(String updated) {
			this.updated = updated;
		}
		public String getDeleted() {
			return deleted;
		}
		public void setDeleted(String deleted) {
			this.deleted = deleted;
		}
	    
	    
}
