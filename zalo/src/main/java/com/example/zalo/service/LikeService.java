package com.example.zalo.service;

import com.example.zalo.model.dto.CommentDTO;
import com.example.zalo.model.dto.LikeDTO;
import com.example.zalo.model.request.CreateCommentRequest;
import com.example.zalo.model.request.CreateLikeRequest;
import com.example.zalo.model.request.UpdateCommentRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface LikeService {

    List<LikeDTO> getAllLike(int postId);
    LikeDTO createLike( int postId, int userId );
    void deleteLike(int id);//id post
}
