package com.example.zalo.service;

import com.example.zalo.model.dto.BlockDTO;
import com.example.zalo.model.dto.FriendDTO;
import com.example.zalo.model.request.CreateBlockRequest;
import com.example.zalo.model.request.CreateFriendRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BlockService {
    List<BlockDTO> getAllBlockChat(int userId);// lấy ra danh sách những người mình block chat

    List<BlockDTO> getAllBlockDiary(int userId);// lấy ra danh sách những người mình block diary

    void createBlockChatRequest( int userAId, int userBId);//chat

    void createBlockDiaryRequest( int userAId, int userBId);//post

    void createBlockUserRequest( int userAId, int userBId);//user

    void createBlockUserCommentRequest( int userAId, int userBId);//comment

    void createBlockCommentsRequest( int userAId, int postId);//comments

    void deleteBlockRequest(int id);
}
