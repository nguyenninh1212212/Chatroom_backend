package app.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.dto.member.MembersDTO;
import app.dto.member.ReqMemberDTO;
import app.service.MemberService;
import app.service.ValidationService;

@RequestMapping("/member")
@RestController
public class MemberController {
	@Autowired
	private MemberService memberSer;
	@Autowired
	private ValidationService validSer;
	
	@PostMapping("/cre")
	ResponseEntity<?> create(@RequestBody(required = true) Map <String,UUID> req ,@RequestHeader("Authorization") String bearer){

		try {
			UUID room_id = req.get("room_id");
			UUID user_id = req.get("user_id");
			String token = bearer.replace("Bearer ", "").trim();
			UUID add_by = validSer.validateUserAndGetId(token);
	        validSer.validateRoom(room_id);
			ReqMemberDTO member=memberSer.create(user_id, room_id ,add_by);
			return ResponseEntity.ok(Map.of("message","successfully!! 🎈","data",member));
		}
		catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body(Map.of("message", e.getMessage()));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Map.of("message", "An unexpected error occurred", "error", e.getMessage()));
	    }
	} 
	

	
	@GetMapping("")
	public ResponseEntity<?> members(@RequestParam(required = true) UUID room_id){
		try {

			List<MembersDTO> memberList=memberSer.Members(room_id);
			return ResponseEntity.ok(Map.of("message","successfully","data",memberList));
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
		}
	}
	
	
}
