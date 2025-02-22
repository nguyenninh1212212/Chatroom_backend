package app.service;

import app.dto.Friends.FriendsDTO;

import app.dto.user.UserInfoDTO;
import app.entity.FriendRequest;
import app.entity.Friends;
import app.entity.User;
import app.enums.FriendStatus;
import app.repository.FriendRequestRepository;
import app.repository.FriendsReponsitory;
import app.repository.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.HttpRetryException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FriendsService {
    @Autowired
    private FriendsReponsitory friendsReponsitory;
    @Autowired
    private FriendRequestRepository FriendRequestRepository;
    @Autowired
    private UserReponsitory UserRepo;


    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public List<FriendsDTO> getFriends(UUID user_id) {
        try{User user =UserRepo.findById(user_id).get();
            List<FriendsDTO> result =friendsReponsitory.findAllByUser(user).stream().map(friend->
                    FriendsDTO.builder().id(friend.getId()).user(new UserInfoDTO(friend.getUser1())).created(LocalDateTime.now()).status(friend.getStatus()).build()
            ).sorted(Comparator.comparing(FriendsDTO::getCreated)).collect(Collectors.toList());
            return result;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String sendFriendRequest(UUID user_id, String receiverEmail) {
        try{
            User sender = UserRepo.findById(user_id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            User receiver = UserRepo.findByEmail(receiverEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (FriendRequestRepository.existsBySenderAndReceiver(sender, receiver)) {
                throw new HttpRetryException("Friend request already sent!",HttpStatus.CONFLICT.value());
            }

            FriendRequest request = FriendRequest.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .status(FriendStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();
            friendRequestRepository.save(request);
            return "Friend request sent!";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String respondToFriendRequest(UUID requestId, boolean accept) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (accept) {
            Friends newFriend = Friends.builder()
                    .user1(request.getSender())
                    .user2(request.getReceiver())
                    .created(LocalDateTime.now())
                    .build();
            friendsReponsitory.save(newFriend);
            request.setStatus(FriendStatus.ACCEPTED);
            friendRequestRepository.delete(request);
        } else {
            friendRequestRepository.delete(request);
            return "Friend request rejected!";
        }
        return "Friend request accepted!";

    }
    public List<FriendRequest> getFriendsRequest(UUID user_id) {
        try{
            User sender = UserRepo.findById(user_id).get();
            List<FriendRequest> result = friendRequestRepository.findAllBySenderAndStatus(sender,FriendStatus.PENDING);
            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<FriendRequest> getFriendsReceive(UUID user_id) {
        try{
            User recieve = UserRepo.findById(user_id).get();
            List<FriendRequest> result = friendRequestRepository.findAllByReceiverAndStatus(recieve,FriendStatus.PENDING);
            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
