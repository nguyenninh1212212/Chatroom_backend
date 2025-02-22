package app.dto.room;

import app.dto.PagedResponse;
import app.dto.member.MemberInfoDTO;
import app.dto.message.MessagesDTO;
import app.dto.user.UserFEDTO;
import app.entity.Room;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter @Setter
public class RoomDetailDTO {
    private UUID id;
    private String name;
    private UserFEDTO owner;
    private Set<MemberInfoDTO> members;
    private PagedResponse<MessagesDTO> messages;

    public RoomDetailDTO(Room room, UUID currentUserId , PagedResponse<MessagesDTO> messages) {
        this.id = room.getId();
        this.name = room.getName();
        this.owner=new UserFEDTO(room.getOwner());
        this.messages =messages;
        this.members = room.getMembers().stream()
                .map(MemberInfoDTO::new)

                .collect(Collectors.toSet());
    }


}
