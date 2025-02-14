package app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
	@Id
	@UuidGenerator
	private UUID id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private Set<Member> members;

	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private List<Message> messages;

	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	@JsonBackReference
	private User owner;

	@Column( updatable = false)
	private LocalDateTime created = LocalDateTime.now();

	@LastModifiedDate
	private LocalDateTime updated;
}
