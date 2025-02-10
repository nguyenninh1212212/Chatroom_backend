package app.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    
    @JoinColumn(name = "add_by", nullable = false)
    private UUID addBy;  

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    
    public Member() {}

    public Member(User user, Room room, UUID addBy) {
        this.user = user;
        this.room = room;
        this.addBy = addBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public UUID getAddBy() {  
        return addBy;
    }

    public void setAddBy(UUID addBy) {  
        this.addBy = addBy;
    }
}
