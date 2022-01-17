package com.example.zalo.service;

import com.example.zalo.entity.Comment;
import com.example.zalo.model.dto.CommentDTO;
import com.example.zalo.model.dto.PostDTO;
import com.example.zalo.model.request.CreateCommentRequest;
import com.example.zalo.model.request.CreatePostRequest;
import com.example.zalo.model.request.UpdateCommentRequest;
import com.example.zalo.model.request.UpdatePostRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    List<CommentDTO> getAllComment(int postId);
    CommentDTO createComment(CreateCommentRequest request,int postId, int commentatorId);

    CommentDTO updateComment(UpdateCommentRequest request, int id ,int userId);//id comment

    void deleteComment(int id,int userId);//id comment

}
