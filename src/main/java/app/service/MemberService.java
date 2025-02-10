package app.service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.dto.member.MembersDTO;
import app.dto.member.ReqMemberDTO;
import app.entity.Member;
import app.entity.Room;
import app.entity.User;
import app.repository.MemberReponsitory;
import app.repository.RoomReponsitory;
import app.repository.UserReponsitory;

@Service
public class MemberService {
	@Autowired
	private MemberReponsitory memberRep;
	
	@Autowired
    private UserReponsitory userRep;
    
    @Autowired
    private RoomReponsitory roomRep;
	
    @Autowired 
    private ValidationService validSer;
    
    public ReqMemberDTO create(UUID user_id, UUID room_id,UUID add_by) {
       try {
    	   User user = validSer.validateUserId(user_id);
           Room room = validSer.validateRoomId(room_id);

           
           if (memberRep.findByUserAndRoom(user, room) != null) {
               throw new RuntimeException("User is already a member of this room");}

           Member member = new Member(user,room,add_by);
           member = memberRep.save(member);
           return new ReqMemberDTO(member.getId(),member.getUser(),member.getCreated()) ;  
	} catch (Exception e) {
		throw new RuntimeException(e.getMessage());
	}
    }
    

	public List<MembersDTO> Members (UUID room_id) {
		Room room =roomRep.findById(room_id).orElseThrow(()-> new RuntimeException("This room is inconrrect"));
		List<Member> existingMember = memberRep.findAllByRoom(room);
		List<MembersDTO> memberList=existingMember.stream().map(member -> new MembersDTO(member.getId(),member.getUser()) ).collect(Collectors.toList());
		return memberList;
		
	}
	
	public void leaveRoom(UUID user_id, UUID room_id) {
	    try {
	    	Member member = memberRep.findByUserIdAndRoomId(user_id, room_id)
	    	        .orElseThrow(() -> new RuntimeException("This member does not exist"));

	    	memberRep.delete(member); 
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	
}
