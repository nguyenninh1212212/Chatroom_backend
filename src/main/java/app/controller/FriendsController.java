package app.controller;

import app.dto.Friends.FriendsDTO;
import app.dto.Friends.ReqFriendsDTO;
import app.dto.user.UserInfoDTO;
import app.entity.FriendRequest;
import app.exception.UserAlreadyExistsException;
import app.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    private FriendsService friendsService;

    @GetMapping("")
    public ResponseEntity<?> friends (@RequestParam UUID user_id){
        try{
            List<FriendsDTO> friendsList =friendsService.getFriends(user_id);
            return ResponseEntity.ok(Map.of("message","successfully 🎈","data", friendsList));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage()) ;
        }
    }

    @PostMapping("/invitation/send")
    public ResponseEntity<?> sendFriendRequest(@RequestBody Map<String,String> req){
        try{
            String email = req.get("email");
            UUID user_id = UUID.fromString(req.get("user_id"));
            String invitation = friendsService.sendFriendRequest(user_id,email);
            return ResponseEntity.ok(Map.of("message", invitation));
        }catch (Exception e){
            throw new UserAlreadyExistsException(e.getMessage()) ;
        }
    }

    @PostMapping("/invitation/respond")
    public ResponseEntity<?> getInvitaionRespond(@RequestBody UUID user_id,@RequestBody Boolean appect){
        try{
            String invitation = friendsService.respondToFriendRequest(user_id,appect);
            return ResponseEntity.ok(Map.of("message", invitation));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage()) ;
        }
    }
    @GetMapping("/invitation/sender")
    public ResponseEntity<?> getInvitaionSender(@RequestParam UUID user_id){
        try{
            List<ReqFriendsDTO> invitationList =friendsService.getFriendsRequest(user_id).stream().map(friends->ReqFriendsDTO.builder().sender(new UserInfoDTO(friends.getSender())).receiver(new UserInfoDTO(friends.getReceiver())).status(friends.getStatus()).isMe(friends.getSender().getId().equals(user_id)).build()).collect(Collectors.toList());
            return ResponseEntity.ok(Map.of ("message", "Success 🎈🎈","data", invitationList));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage()) ;
        }
    }

    @GetMapping("/invitation/receiver")
    public ResponseEntity<?> getInvitaionReceived(@RequestParam UUID user_id){
        try{
            List<ReqFriendsDTO> invitationList =friendsService.getFriendsReceive(user_id).stream().map(friends->ReqFriendsDTO.builder().sender(new UserInfoDTO(friends.getSender())).receiver(new UserInfoDTO(friends.getReceiver())).status(friends.getStatus()).isMe(friends.getSender().getId().equals(user_id)).build()).collect(Collectors.toList());
            return ResponseEntity.ok(Map.of("message", "Success 🎈🎈","data", invitationList));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage()) ;
        }
    }
}
