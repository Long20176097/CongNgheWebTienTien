package com.example.zalo.service;

import com.example.zalo.entity.User;
import com.example.zalo.exception.NotFoundException;
import com.example.zalo.model.dto.FriendDTO;
import com.example.zalo.model.dto.PostDTO;
import com.example.zalo.model.dto.UserDTO;
import com.example.zalo.model.mapper.UserMapper;
import com.example.zalo.model.request.CreateFriendRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FriendService {
    List<FriendDTO> getAllFriend(int userId);

    List<FriendDTO> getAllFriend();
    List<FriendDTO> getAllFriendAccepted();
    List<FriendDTO> getAllFriendAccepted(int userId);

    List<FriendDTO> getAllFriendRequest(int userId);

    void createFriendRequest(int userAId, int userBId);

    void acceptFriendRequest( int id);

    void deleteFriendRequest(int id);// đúng trong cả trường hợp gửi lời mời kết bạn và
    // trường hợp từ chối lời mời kết bạn
}
