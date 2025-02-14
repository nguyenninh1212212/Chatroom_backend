package app.service;



import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import app.dto.room.RoomDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.dto.message.MesssageLatestDTO;
import app.dto.room.ReqRoomDTO;
import app.dto.room.RoomInfoDTO;
import app.entity.Member;
import app.entity.Room;
import app.entity.User;
import app.exception.ResourceNotFoundException;
import app.repository.MemberReponsitory;
import app.repository.RoomReponsitory;
import app.repository.UserReponsitory;

@Service
public class RoomService {
	
	@Autowired
	private RoomReponsitory roomRep;
	@Autowired
	private UserReponsitory userRep;
	@Autowired
	private MemberReponsitory memberRep;
	@Autowired
	private MessageService messageSer;
	@Autowired
	private ValidationService validationService;
	@Async
	public CompletableFuture<Room> createRoom(String name, UUID user_id) {
	    if (name.isEmpty()) {
	        throw new RuntimeException("Room name is not empty!!");
	    }
	    User owner = userRep.findById(user_id).orElseThrow();


	    Room room = new Room();
	    room.setOwner(owner);
	    room.setName(name);
	    room = roomRep.save(room);

	    return CompletableFuture.completedFuture(room);
	}

	@Async
	public CompletableFuture<Member> addMemberToRoom(Room room, UUID user_id) {
	    User owner = userRep.findById(user_id).orElseThrow();


	    Member member = new Member();
	    member.setRoom(room);
	    member.setUser(owner);
	    member.setAddBy(user_id);
	    member = memberRep.save(member);

	    return CompletableFuture.completedFuture(member);
	}

	public CompletableFuture<ReqRoomDTO> create(String name, UUID user_id) {
	    return createRoom(name, user_id).thenCompose(room -> 
	        addMemberToRoom(room, user_id).thenApply(member -> 
	            new ReqRoomDTO(room.getId(), room.getName(), room.getCreated(), room.getOwner())
	        )
	    );
	}
	
	public List<RoomInfoDTO> Rooms(UUID user_id) {

	    try {
			User user = userRep.findById(user_id).orElseThrow();
	        List<RoomInfoDTO> roomList = roomRep.findAllByUserId(user.getId()).stream().map(room -> {
	            Optional<MesssageLatestDTO> messagelatest = messageSer.getLatestMessages(room.getId());
	            String content=messagelatest.map(MesssageLatestDTO::getContent).orElse("no message yet");
	            return new RoomInfoDTO(room, content);
	        }).collect(Collectors.toList());
	        return roomList;
	    } catch (Exception e) {
	        throw new RuntimeException(e.getMessage());
	    }
	}

	public Optional<RoomDetailDTO> TheRooms( UUID room_id,UUID user_id) {
		try {

			return roomRep.findById(room_id).stream().map(e->{return new RoomDetailDTO(e,user_id);}).findFirst();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}



	public Room JoinRoom(UUID user_id, UUID id) {
	    Room room = roomRep.findById(id)
	                       .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found!"));

	    User user = validationService.validateUserId(user_id);

	    boolean isMember = memberRep.findByUserIdAndRoomId(user.getId(), id).isPresent();
	    if (isMember) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "You are already a member of this room!");
	    }

	    try {
	        Member newMember = new Member();
	        newMember.setRoom(room);
	        newMember.setUser(user);
	        memberRep.save(newMember);
	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to join room due to system error.");
	    }

	    return room;
	}

	public List<RoomInfoDTO> searchRooms(String keyword){
		try{
			List<RoomInfoDTO> roomList = roomRep.searchRooms(keyword).stream().map(room -> {
				return new RoomInfoDTO(room, "");
			}).collect(Collectors.toList());
			return roomList;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}




}
