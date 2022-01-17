package com.example.zalo.controller;

import com.example.zalo.entity.Friend;
import com.example.zalo.exception.*;
import com.example.zalo.model.dto.CommentDTO;
import com.example.zalo.model.dto.FriendDTO;
import com.example.zalo.model.dto.PostDTO;
import com.example.zalo.model.dto.UserDTO;
import com.example.zalo.model.request.CreateFriendRequest;
import com.example.zalo.model.request.CreatePostRequest;
import com.example.zalo.model.request.UpdatePostRequest;
import com.example.zalo.service.FriendService;
import com.example.zalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FriendController {
    private final FriendService friendService;
    private final UserService userService;
    @Autowired
    public FriendController(FriendService friendService, UserService userService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @GetMapping("/friends")
    public ResponseEntity<?> getAllFriend( Principal principal){

        String phoneNumber = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(phoneNumber);
        String authority = userDTO.getAuthority();
        int userId = userDTO.getId();

        List<FriendDTO> friends =null;
        if(authority.equals("admin")){
            friends= friendService.getAllFriend();
        }
        if(authority.equals("user")){
            friends=  friendService.getAllFriend(userId);
        }


        return ResponseEntity.ok(friends);
    }




    @PostMapping("/friendRequest/{userBId}")
    public ResponseEntity<?> createFriendRequest( @PathVariable int userBId,Principal principal) {
        String phoneNumber = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(phoneNumber);

        int userAId =userDTO.getId();
        try{       friendService.createFriendRequest(userAId,userBId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "code", "1000",
                    "message", "OK",
                    "note","Đã gửi lời mời kết bạn thành công"));}
        catch (BadGuyException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "code", "1004",
                    "message", "Not access",
                    "note","Bạn đã bị  người này block "
            ));
        }
        catch (NotFoundException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", "9995",
                    "message", "User is not validated",
                    "note","Không có người dùng này"
            ));
        }
        catch (BadRequestException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "code", "9995",
                    "message", "Parameter value is invalid",
                    "note","Bạn không thể gửi lời mời kết bạn cho bản thân"
            ));
        }
        catch (DuplicateRecordException e){

            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of(
                    "code", "9995",
                    "message", "Action has been done previously by this user",
                    "note","Bạn đã gửi lời mời kêt bạn trươc đó"
            ));
        }
        catch (BusinessException e){

            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(Map.of(
                    "code", "9995",
                    "message", "Action has been done previously by this user",
                    "note","Hai người đã là bạn bè rồi "
            ));
        }

    }


    @GetMapping("/friendRequest")
    public ResponseEntity<?> getAllFriendRequest( Principal principal){

        String phoneNumber = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(phoneNumber);

        int userId = userDTO.getId();

        List<FriendDTO> friends = friendService.getAllFriendRequest(userId);
        return ResponseEntity.ok(friends);
    }
    //admin
    @GetMapping("/allFriendAccepted")
    public ResponseEntity<?> getAllFriendAccepted(){
        List<FriendDTO> friends = friendService.getAllFriendAccepted();
        return ResponseEntity.ok(friends);
    }

    @PutMapping("/friendRequest/{id}")
    public ResponseEntity<?> acceptFriendRequest( @PathVariable int id) {
        friendService.acceptFriendRequest(id);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of( "code", "1000",
                "message", "OK",
                "note","Đã châp nhận lời mời kêt bạn"
        ));
    }

    @DeleteMapping("/friendRequest/{id}")
    public ResponseEntity<?> deleteFriendRequest(@PathVariable int id) {
        friendService.deleteFriendRequest(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "code", "1000",
                "message", "OK",
                "note","Đã xóa lời mời kêt bạn"
        ));
    }

}
