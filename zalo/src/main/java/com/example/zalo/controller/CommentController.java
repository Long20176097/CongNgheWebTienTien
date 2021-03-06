package com.example.zalo.controller;

import com.example.zalo.entity.Comment;
import com.example.zalo.entity.User;
import com.example.zalo.exception.*;
import com.example.zalo.model.dto.CommentDTO;
import com.example.zalo.model.dto.UserDTO;
import com.example.zalo.model.request.CreateCommentRequest;
import com.example.zalo.model.request.CreatePostRequest;
import com.example.zalo.model.request.UpdateCommentRequest;
import com.example.zalo.service.CommentService;
import com.example.zalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/comments/post/{postId}")
    public ResponseEntity<?> searchComment(@PathVariable int postId){
        List<CommentDTO> comments = commentService.getAllComment(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comments/post/{id}")
    public ResponseEntity<?> createComment(@Valid @RequestBody CreateCommentRequest request, @PathVariable int id, Principal principal) {
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);

        int commentatorId = userDTO.getId();
try{
    CommentDTO result = commentService.createComment(request,id,commentatorId);
    return ResponseEntity.ok(result);

}     catch (BadRequestException e){

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "code", "1009",
            "message", "Not access",
            "note","B???n b??? ch??? b??i vi???t ch???n b??nh lu???n"
    ));
}
catch (DuplicateRecordException e){

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "code", "1009",
            "message", "Not access",
            "note","Ch??? b??i vi???t ???? ch???n b???n kh???i b??i vi???t"
    ));
}
catch (BusinessException e){

    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of(
            "code", "1009",
            "message", "Not access",
            "note","Ch??? b??i vi???t ???? kh??a b??nh lu???n"
    ));
}
catch (InternalServerException e){

    return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(Map.of(
            "code", "1009",
            "message", "Not access",
            "note","Ch??? b??i vi???t ???? ch???n b???n"
    ));
}
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<?> updateComment(@Valid @RequestBody UpdateCommentRequest request, @PathVariable int id, Principal principal) {
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);


        int authorId = userDTO.getId();

try{
    CommentDTO result = commentService.updateComment(request,id ,authorId);
    return ResponseEntity.ok(result);
}  catch (NotFoundException e){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "code", "9992",
            "message", "Comment is not exited",
            "note","Kh??ng t??m th???y comment n??y"
    ));
}
catch (BadRequestException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "code", "1009",
            "message", "Not access.",
            "note","B???n kh??ng ph???i t??c gi??? n??n kh??ng th??? s???a b??nh lu???n n??y"
    ));
}

    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteCommentRequest(@PathVariable int id  , Principal principal) {
        String username = principal.getName();
        UserDTO userDTO =userService.findByPhoneNumber1(username);

        int authorId = userDTO.getId();
        try{ commentService.deleteComment(id,authorId);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "code", "1000",
                    "message", "OK",
                    "note","???? x??a b??nh lu???n th??nh c??ng "
            ));}
        catch (BadRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "code", "1009",
                    "message", "Not access.",
                    "note","B???n kh??ng ph???i t??c gi??? n??n kh??ng th??? x??a b??nh lu???n n??y"
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
