package app.dto.room;

import app.dto.member.MemberInfoDTO;
import app.dto.message.MessagesDTO;
import app.dto.user.UserFEDTO;
import app.entity.Room;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RoomDetailDTO {
    private UUID id;
    private String name;
    private UserFEDTO owner;
    private Set<MemberInfoDTO> members;
    private List<MessagesDTO> messages;

    public RoomDetailDTO(Room room, UUID currentUserId) {
        this.id = room.getId();
        this.name = room.getName();
        this.owner=new UserFEDTO(room.getOwner());
        this.messages = room.getMessages().stream()
                .map(message -> new MessagesDTO(message, currentUserId))
                .sorted(Comparator.comparing(MessagesDTO::getCreated))
                .collect(Collectors.toList());
        this.members = room.getMembers().stream()
                .map(MemberInfoDTO::new)

                .collect(Collectors.toSet());
    }

    public UserFEDTO getOwner() {
        return owner;
    }

    public void setOwner(UserFEDTO owner) {
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<MemberInfoDTO> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberInfoDTO> members) {
        this.members = members;
    }

    public List<MessagesDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesDTO> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
