package app.dto.room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import app.dto.user.UserInfoDTO;
import app.entity.Member;
import app.entity.Message;
import app.entity.User;

public class ReqRoomDTO {
	 	private UUID id;
	    private String name;
	    private Set<Member> members;
	    private List<Message> messages;
	    private LocalDateTime created;
	    private String updated;
	    private String deleted;
	    private UserInfoDTO is_owner;
	    
	    public ReqRoomDTO(UUID id,String name,LocalDateTime created,User is_owner) {
	    	this.id=id;
	    	this.name=name;
	    	this.created=created;
	    	this.setIs_owner(new UserInfoDTO(is_owner));
	    }
	    
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Set<Member> getMembers() {
			return members;
		}
		public void setMembers(Set<Member> members) {
			this.members = members;
		}
		public List<Message> getMessages() {
			return messages;
		}
		public void setMessages(List<Message> messages) {
			this.messages = messages;
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

		public UserInfoDTO getIs_owner() {
			return is_owner;
		}

		public void setIs_owner(UserInfoDTO is_owner) {
			this.is_owner = is_owner;
		}

			    
	    
}
