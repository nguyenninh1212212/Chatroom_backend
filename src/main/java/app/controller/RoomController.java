package app.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import app.dto.room.RoomDetailDTO;
import app.service.MemberService;
import app.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import app.dto.room.ReqRoomDTO;
import app.dto.room.RoomInfoDTO;
import app.entity.Room;
import app.service.RoomService;

@RequestMapping("/room")
@RestController
public class RoomController {
	@Autowired
	private RoomService roomSer;
	@Autowired
	private MemberService memberSer;
	@Autowired
	private ValidationService validSer;
	
	@PostMapping("/cre")
	public ResponseEntity<?> create (@RequestBody Map<String,String> req,@RequestParam(required = true) UUID user_id){
		try {
			String name=req.get("name");
			CompletableFuture<ReqRoomDTO> room=roomSer.create(name,user_id);
			ReqRoomDTO roomDTO = room.join();  
	        return ResponseEntity.ok(Map.of("message", "Create roommate successfully!!", "data", roomDTO));
		} catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred", "error", e.getMessage()));
}
		
	}
	

	@GetMapping("")
	public ResponseEntity<?> rooms(@RequestParam(required = true) UUID user_id) {
	    try {
	    	List<RoomInfoDTO> roomList = roomSer.Rooms(user_id);
	    	return ResponseEntity.ok(Map.of("message", "successfully!! 🎈", "data", roomList));
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Room not found"));
		}  catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Unexpected error occurred!"));
	    }
	}

	@GetMapping("/detail")
	public ResponseEntity<?> TheRoom(@RequestParam UUID room_id,@RequestParam UUID user_id) {
		try {
			RoomDetailDTO roomDetail = roomSer.TheRooms( room_id,user_id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

			return ResponseEntity.ok(Map.of("message", "Successfully!! 🎈", "data", roomDetail));
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(Map.of("message", e.getReason()));
		} catch (Exception e) {
			e.printStackTrace(); // In lỗi chi tiết ra console
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", e.getMessage()));
		}

	}



	@PostMapping("/join")
	public ResponseEntity<?> joinRoom(@RequestBody(required = true) Map<String, UUID> req ) {
	    UUID userId = req.get("user_id");
	    UUID roomId = req.get("room_id");
	    try {
	        Room room = roomSer.JoinRoom(userId, roomId);
	        return ResponseEntity.ok(Map.of("message", "Join room successfully!! 🎈", "data", room));
	    } catch (ResponseStatusException e) {
	        return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason()));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Unexpected error occurred!"));
	    }
	}

	@DeleteMapping("/leave")
	public ResponseEntity<?> leaveRoom(@RequestBody(required = true) Map<String, UUID> req,@RequestHeader("Authorization") String bearer) {
		try {
			UUID room_id = req.get("room_id");
			String token = bearer.replace("Bearer ", "").trim();
			UUID user_id = validSer.validateUserAndGetId(token);
			validSer.validateRoom(room_id);
			memberSer.leaveRoom(user_id, room_id);
			return ResponseEntity.ok(Map.of("message", "User left the room successfully"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
		}
	}


	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam(required = false) String keyword) {
		try {
			List<RoomInfoDTO> roomList = roomSer.searchRooms(keyword);
			return ResponseEntity.ok(Map.of("message", "successfully!! 🎈", "data", roomList));
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Room not found"));
		}  catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Unexpected error occurred!"));
		}




	}
	
	
}
