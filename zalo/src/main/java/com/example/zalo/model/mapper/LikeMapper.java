package com.example.zalo.model.mapper;

import com.example.zalo.entity.Comment;
import com.example.zalo.entity.Friend;
import com.example.zalo.entity.Like;
import com.example.zalo.model.dto.CommentDTO;
import com.example.zalo.model.dto.FriendDTO;
import com.example.zalo.model.dto.LikeDTO;
import com.example.zalo.model.request.CreateCommentRequest;
import com.example.zalo.model.request.CreateFriendRequest;
import com.example.zalo.model.request.CreateLikeRequest;
import com.example.zalo.model.request.UpdateCommentRequest;
import com.example.zalo.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LikeMapper {
    private static UserService userService;

    public LikeDTO toLikeDTO(Like like) {
        LikeDTO tmp = new LikeDTO();
        tmp.setId(like.getId());
        tmp.setPeopleLikeId(like.getPeopleLikeId().getId());

        tmp.setUpdated(like.getUpdated());
        tmp.setLikePost(like.getLikePost().getId());
        return tmp;
    }


    public static Like toLike( int id) {
        Like like = new Like();
        LocalDateTime now = LocalDateTime.now();
        like.setUpdated(now);
        return like;
    }


}
