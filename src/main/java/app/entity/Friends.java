package app.entity;


import app.enums.FriendStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Friends {
    @Id
    @UuidGenerator
    private UUID id;
    @Enumerated(EnumType.STRING) // Lưu trạng thái là dạng chuỗi
    @Column(nullable = false)
    private FriendStatus status;
    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;
    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;
    private LocalDateTime created;


}
