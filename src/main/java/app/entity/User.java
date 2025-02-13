package app.entity;

import app.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@UuidGenerator
	private UUID id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String fullname;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true)
	private String role = Role.USER.name();

	@Column(nullable = false, updatable = false)
	private LocalDateTime created = LocalDateTime.now();

	@Column(nullable = false)
	@LastModifiedDate
	private LocalDateTime updated;

	private LocalDateTime deleted;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private Set<Member> members;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<Room> rooms;

}
