package app.repository;

import app.entity.FriendRequest;
import app.entity.User;
import app.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, UUID> {
    Boolean existsBySenderAndReceiver(User sender, User receiver);
    List<FriendRequest> findAllByReceiverAndStatus(User receiver, FriendStatus status);
    List<FriendRequest> findAllBySenderAndStatus(User sender, FriendStatus status);
}
