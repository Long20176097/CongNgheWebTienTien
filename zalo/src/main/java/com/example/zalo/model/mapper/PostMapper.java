package com.example.zalo.model.mapper;

import com.example.zalo.entity.Post;
import com.example.zalo.entity.User;
import com.example.zalo.model.dto.PostDTO;
import com.example.zalo.model.dto.UserDTO;
import com.example.zalo.model.request.CreatePostRequest;
import com.example.zalo.model.request.CreateUserRequest;
import com.example.zalo.model.request.UpdatePostRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {
    public static PostDTO toPostDTO(Post post) {
        PostDTO tmp = new PostDTO();
        tmp.setId(post.getId());
        tmp.setContent(post.getContent());
        tmp.setMedia(post.getMedia());
        tmp.setUpdated(post.getUpdated());
        tmp.setCreated(post.getCreated());
        tmp.setAuthor(post.getAuthor().getId());
        tmp.setNumberOfComments(post.getNumberOfComments());
        tmp.setNumberOfLikes(post.getNumberOfLikes());
        return tmp;
    }


    public static Post toPost(CreatePostRequest request) {
        Post post = new Post();
        LocalDateTime now = LocalDateTime.now();
        post.setCreated(now);
        post.setNumberOfComments(0);
        post.setNumberOfLikes(0);
        post.setContent(request.getContent());
        post.setMedia(request.getMedia());
        post.setUpdated(now);
        return post;
    }

    public static Post toPost(UpdatePostRequest request,int id) {
        Post post = new Post();
        LocalDateTime now = LocalDateTime.now();
        post.setId(id);
        post.setContent(request.getContent());
        post.setMedia(request.getMedia());
        post.setUpdated(now);

        return post;
    }

}
