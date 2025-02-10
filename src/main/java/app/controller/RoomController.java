package app.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	
	@PostMapping("/join")
	public ResponseEntity<?> joinRoom(@RequestBody(required = true) Map<String, UUID> req ) {
	    UUID userId = req.get("user_id");
	    UUID roomId = req.get("id");
	    try {
	        Room room = roomSer.JoinRoom(userId, roomId);
	        return ResponseEntity.ok(Map.of("message", "Join room successfully!! 🎈", "data", room));
	    } catch (ResponseStatusException e) {
	        return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason()));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Unexpected error occurred!"));
	    }
	}


	
	
}
