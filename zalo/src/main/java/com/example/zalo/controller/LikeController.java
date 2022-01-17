package com.example.zalo.controller;

import com.example.zalo.exception.DuplicateRecordException;
import com.example.zalo.model.dto.CommentDTO;
import com.example.zalo.model.dto.LikeDTO;
import com.example.zalo.model.dto.UserDTO;
import com.example.zalo.model.request.CreateCommentRequest;
import com.example.zalo.model.request.CreateLikeRequest;
import com.example.zalo.model.request.UpdateCommentRequest;
import com.example.zalo.service.LikeService;
import com.example.zalo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class LikeController {
    private final UserService userService;
    private final LikeService likeService;

    public LikeController(UserService userService, LikeService likeService) {
        this.userService = userService;
        this.likeService = likeService;
    }


    @GetMapping("/likes/post/{postId}")
    public ResponseEntity<?> searchLike(@PathVariable int postId){
        List<LikeDTO> likes = likeService.getAllLike(postId);
        return ResponseEntity.ok(likes);
    }

    @PostMapping("/likes/post/{id}")
    public ResponseEntity<?> createLike( @PathVariable int id, Principal principal) {
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);

        int userId = userDTO.getId();
        try{
        LikeDTO result = likeService.createLike(id,userId);
        return ResponseEntity.ok(result);}

           catch (DuplicateRecordException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "code", "1010",
                    "message", "Action has been done previously by this user",
                    "note","Bạn đã like bài viết này "
            ));
        }
    }



    @DeleteMapping("/likes/post/{id}")
    public ResponseEntity<?> deleteLikeRequest(@PathVariable int id) {
        likeService.deleteLike(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of(
                        "code", "1000",
                        "message", "Deleted like"));
    }
}
