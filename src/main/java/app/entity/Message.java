package app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
	@Id
	@UuidGenerator
	private UUID id;

	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@Column(nullable = false, updatable = false)
	private LocalDateTime created = LocalDateTime.now();

	@LastModifiedDate
	private LocalDateTime updated;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isSeen = false;
}
