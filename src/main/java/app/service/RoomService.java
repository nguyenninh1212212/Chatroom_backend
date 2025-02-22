package app.service;



import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import app.dto.PagedResponse;
import app.dto.message.MessagesDTO;
import app.dto.room.RoomDetailDTO;
import app.entity.Message;
import app.repository.MessageReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	private MessageReponsitory messageRep;
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
	    User owner = userRep.findById(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	    Room room = Room.builder().owner(owner).name(name).created(LocalDateTime.now()).build();
	    room = roomRep.save(room);

	    return CompletableFuture.completedFuture(room);
	}

	@Async
	public CompletableFuture<Member> addMemberToRoom(Room room, UUID user_id) {
	    User owner = userRep.findById(user_id).orElseThrow();


	    Member member = Member.builder().room(room).user(owner).addBy(user_id).build();
        memberRep.save(member);

	    return CompletableFuture.completedFuture(member);
	}

	public CompletableFuture<ReqRoomDTO> create(String name, UUID user_id) {
	    return createRoom(name, user_id).thenCompose(room -> 
	        addMemberToRoom(room, user_id).thenApply(member -> 
	            new ReqRoomDTO(room.getId(), room.getName(), room.getCreated(), room.getOwner())
	        )
	    );
	}

	public PagedResponse<RoomInfoDTO> Rooms(UUID userId, int page, int size) {
		try {
			User user = userRep.findById(userId).orElseThrow();
			Pageable pageable = PageRequest.of(page-1, size, Sort.by("created").descending());
			Page<Room> roomPage = roomRep.findAllByUserId(user.getId(), pageable);

			List<RoomInfoDTO> roomList = roomPage.stream().map(room -> {
				Optional<MesssageLatestDTO> messageLatest = messageSer.getLatestMessages(room.getId());
				String content = messageLatest.map(MesssageLatestDTO::getContent).orElse("no message yet");
				return new RoomInfoDTO(room, content);
			}).collect(Collectors.toList());

			return PagedResponse.<RoomInfoDTO>builder()
					.data(roomList)
					.page(roomPage.getNumber()+1)
					.size(roomPage.getSize())
					.totalPages(roomPage.getTotalPages())
					.build();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Optional<RoomDetailDTO> TheRooms( UUID room_id,UUID user_id,LocalDateTime created, int page) {
		try {
			Pageable pageable = PageRequest.of(page-1 , 30);
			Page<Message> messagePage = messageRep.findByRoomIdAndCreatedBeforeOrderByCreatedDesc(room_id, created,pageable);
			List<MessagesDTO> message = messagePage.getContent().stream()
					.map(mess -> new MessagesDTO(mess, user_id))
					.sorted(Comparator.comparing(MessagesDTO::getCreated)) // Sắp xếp tăng dần theo thời gian
					.collect(Collectors.toList());

			PagedResponse listMessage= PagedResponse.<MessagesDTO>builder().data(message).page(messagePage.getNumber()+1).totalPages(messagePage.getTotalPages()).size(messagePage.getSize()).build();
			return roomRep.findByIdAndUserId(room_id,user_id).stream().map(e->{return new RoomDetailDTO(e,user_id,listMessage);}).findFirst();
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






}
