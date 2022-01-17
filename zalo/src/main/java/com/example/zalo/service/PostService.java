package com.example.zalo.service;

import com.example.zalo.entity.User;
import com.example.zalo.model.dto.PostDTO;
import com.example.zalo.model.dto.UserDTO;
import com.example.zalo.model.request.CreatePostRequest;
import com.example.zalo.model.request.CreateUserRequest;
import com.example.zalo.model.request.UpdatePostRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostService {
    List<PostDTO> getAllPost();
    List<PostDTO> getAllUserPost(int authorId,int userId);//authorId la cua chu bai viet khac , userid la cua nguoi call api
    PostDTO getPostById(int id, int userId,int authorId);
    PostDTO createPost(CreatePostRequest request,int authorId);

    PostDTO updatePost(UpdatePostRequest request, int id);

    void deletePost(int id,int userId);

}
