package com.example.zalo.controller;

import com.example.zalo.entity.Post;
import com.example.zalo.exception.*;
import com.example.zalo.model.dto.PostDTO;
import com.example.zalo.model.dto.UserDTO;
import com.example.zalo.model.request.CreatePostRequest;
import com.example.zalo.model.request.UpdatePostRequest;
import com.example.zalo.repository.PostRepository;
import com.example.zalo.service.PostService;
import com.example.zalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final PostRepository postRepository;
    @Autowired
    public PostController(PostService postService, UserService userService, PostRepository postRepository) {
        this.postService = postService;
        this.userService = userService;

        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(Principal principal){
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);
        int authorId = userDTO.getId();
        String authority = userDTO.getAuthority();
        List<PostDTO> posts=null;
        if(authority.equals("admin")){
             posts= postService.getAllPost();
        }
        if(authority.equals("user")){
            posts= postService.getAllUserPost(authorId,authorId);
        }


        return ResponseEntity.ok(posts);
    }
    @GetMapping("/posts/user/{authorId}")
    public ResponseEntity<?> getAllPosts(@PathVariable int authorId , Principal principal){
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);
        int userId = userDTO.getId();
try{
        List<PostDTO>  posts= postService.getAllUserPost(authorId,userId);

        return ResponseEntity.ok(posts);}
catch (NotFoundException e){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "code", "9992",
            "message", "Post is not exited",
            "note", "Không có bài viết nào"
    ));
}
catch (BusinessException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "code", "9995",
            "message", "User is not validated",
            "note", "User không tồn tại"
    ));
}
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id,Principal principal) {
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);

        int userId = userDTO.getId();
        Optional<Post> post1 = postRepository.findById(id);
        int authorId = post1.get().getAuthor().getId();
       try{ PostDTO result = postService.getPostById(id,userId,authorId);
        return ResponseEntity.ok(result);}
       catch (NotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                   "code", "9992",
                   "message", "Post is not exited",
                   "note", "Bài viết không tồn tại"
           ));
       }
       catch (InternalServerException e){

           return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(Map.of(
                   "code", "1009",
                   "message", "Not access",
                   "note","Chủ bài viết đã chặn bạn"
           ));
       }
       catch (DuplicateRecordException e){

           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                   "code", "1009",
                   "message", "Not access",
                   "note","Chủ bài viết đã chặn bạn khỏi bài viết"
           ));
       }

    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostRequest request, Principal principal) {
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);

        int authorId = userDTO.getId();
        try {
            PostDTO result = postService.createPost(request, authorId);
            return ResponseEntity.ok(result);
        }
        catch (InternalServerException e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(Map.of(
                    "code", "1005",
                    "message", "Unknown error",
                    "note","Không tạo được bài viết"
            ));
        }
    }


    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id,Principal principal) {
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);

        int authorId = userDTO.getId();

        try{ postService.deletePost(id,authorId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "code", "1000",
                "message", "OK",
                "note","Đã xóa bài viết thành công "
        ));}
        catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "code", "1009",
                    "message", "Not access.",
                    "note","Bạn không phải tác giả nên không thể xóa bài viết này"
            ));
        }
        catch (InternalServerException e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(Map.of(
                    "code", "1005",
                    "message", "Unknown error"

            ));
        }


    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody UpdatePostRequest request, @PathVariable int id) {

        try {
            PostDTO result = postService.updatePost(request, id);
            return ResponseEntity.ok(result);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "code", "1009",
                    "message", "Not access.",
                    "note", "Bạn không phải tác giả nên không thể sửa bài viết này"
            ));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", "9992",
                    "message", "Post is not exited",
                    "note", "Bài viết không tồn tại"
            ));
        }
        catch (InternalServerException e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(Map.of(
                    "code", "1005",
                    "message", "Unknown error"

            ));
        }

    }
}
