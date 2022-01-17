package com.example.zalo.model.mapper;

import com.example.zalo.entity.Comment;
import com.example.zalo.entity.Post;
import com.example.zalo.model.dto.CommentDTO;
import com.example.zalo.model.dto.PostDTO;
import com.example.zalo.model.request.CreateCommentRequest;
import com.example.zalo.model.request.CreatePostRequest;
import com.example.zalo.model.request.UpdateCommentRequest;
import com.example.zalo.model.request.UpdatePostRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    public  CommentDTO toCommentDTO(Comment comment) {
        CommentDTO tmp = new CommentDTO();
        tmp.setId(comment.getId());
        tmp.setUser(comment.getUser().getId());
        tmp.setContent(comment.getContent());
        tmp.setCreated(comment.getCreated());
        tmp.setUpdated(comment.getUpdated());
        tmp.setPost(comment.getPost().getId());
        return tmp;
    }


    public static Comment toComment(CreateCommentRequest request,int id) {
        Comment comment = new Comment();
        LocalDateTime now = LocalDateTime.now();
        comment.setCreated(now);
        comment.setContent(request.getContent());
        comment.setUpdated(now);
        return comment;
    }

    public static Comment toComment(UpdateCommentRequest request, int id) {
        Comment comment = new Comment();
        LocalDateTime now = LocalDateTime.now();
        comment.setId(id);
        comment.setContent(request.getContent());
        comment.setUpdated(now);
        return comment;
    }

}
