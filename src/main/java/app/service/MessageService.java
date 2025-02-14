package app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.message.ChatMessageDTO;
import app.dto.message.MessagesDTO;
import app.dto.message.MesssageLatestDTO;
import app.dto.message.ReqMessageDTO;
import app.entity.Message;
import app.entity.Room;
import app.entity.User;
import app.repository.MessageReponsitory;
import app.repository.RoomReponsitory;
import app.repository.UserReponsitory;

@Service
public class MessageService {
	@Autowired
	private MessageReponsitory messageRep;
	@Autowired
	private UserReponsitory userRep;
	@Autowired
	private RoomReponsitory roomRep;
	public ReqMessageDTO create(ChatMessageDTO messageSend,UUID room_id){
		User user=userRep.findById(messageSend.getUser_id()).orElseThrow(()-> new RuntimeException("This user is not existing"));
		Room room=roomRep.findById(room_id).orElseThrow(()-> new RuntimeException("This room is not existing"));
		
		Message message=Message.builder().content(messageSend.getContent()).user(user).room(room).build();
		message=messageRep.save(message);
		return new ReqMessageDTO(message.getId(),message.getContent(),message.getUser(),message.getRoom());
	}

	public List<Message> messages(UUID room_id, UUID user_id) {
		try {
			Room room = roomRep.findById(room_id)
					.orElseThrow(() -> new RuntimeException("This room is not existing"));
			User user = userRep.findById(user_id)
					.orElseThrow(() -> new RuntimeException("This User is not existing"));

			List<Message> mess = messageRep.findAllByRoomId(room.getId());



			return mess;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}


	public Optional<MesssageLatestDTO> getLatestMessages(UUID room_id) {
        return messageRep.findLatestMessageByRoomId(room_id).map(e->new MesssageLatestDTO(e));
        
    }
	
	public void removeMessage(UUID room_id,UUID id) {
        try {
        	Message message=messageRep.findByRoomIdAndId(room_id, id);
        	messageRep.delete(message);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
        
    }

	
}
