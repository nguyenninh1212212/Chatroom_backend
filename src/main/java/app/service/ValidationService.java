package app.service;

import java.util.UUID;

import app.util.TokenPayloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Room;
import app.entity.User;
import app.repository.RoomReponsitory;
import app.repository.UserReponsitory;

@Service
public class ValidationService {
		@Autowired
	    private final UserReponsitory userRep;
		@Autowired
	    private final RoomReponsitory roomRep;

	    public ValidationService(UserReponsitory userRep, RoomReponsitory roomRep) {
	        this.userRep = userRep;
	        this.roomRep = roomRep;
	    }

	    public UUID validateUserAndGetId(String token) {
	        return userRep.findByUsername(new TokenPayloadUtil().getUsername(token))
	                .map(User::getId)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	    }
	    public User validateUserId(UUID user_id) {
	        return userRep.findById(user_id).orElseThrow();
	    }
	    public Room validateRoomId(UUID room_id) {
	        return roomRep.findById(room_id).orElseThrow(()->new RuntimeException("Room with ID " + room_id + " does not exist"));
	    }


	    public void validateRoom(UUID roomId) {
	        if (roomRep.findById(roomId).isEmpty()) {
	            throw new RuntimeException("This room is not available");
	        }
	    }
}
