package com.example.zalo.service.impl;

import com.example.zalo.entity.Block;
import com.example.zalo.entity.Comment;
import com.example.zalo.entity.Post;
import com.example.zalo.entity.User;
import com.example.zalo.exception.*;
import com.example.zalo.model.dto.CommentDTO;
import com.example.zalo.model.mapper.CommentMapper;
import com.example.zalo.model.request.CreateCommentRequest;
import com.example.zalo.model.request.UpdateCommentRequest;
import com.example.zalo.repository.BlockRepository;
import com.example.zalo.repository.CommentRepository;
import com.example.zalo.repository.PostRepository;
import com.example.zalo.service.CommentService;
import com.example.zalo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private  final UserService userService;
    private  final PostRepository postRepository;
    private final BlockRepository blockRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, UserService userService, PostRepository postRepository, BlockRepository blockRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;

        this.postRepository = postRepository;
        this.blockRepository = blockRepository;
    }

    @Override
    public List<CommentDTO> getAllComment(int postId) {
        List<Comment> comments = commentRepository.getAllComment(postId);
        return comments.stream().map(commentMapper::toCommentDTO).collect(Collectors.toList());

    }

    @Override
    public CommentDTO createComment(CreateCommentRequest request, int postId ,int commentatorId) {
        Optional<Post> post1 = postRepository.findById(postId);
        int userId =post1.get().getAuthor().getId();

        Block blockDiary= blockRepository.checkBlockDiary(userId,commentatorId);
        Block blockUser= blockRepository.checkBlockUser(userId,commentatorId);
        Block blockUserComment= blockRepository.checkBlockUserComment(userId,commentatorId);
        Block blockComments= blockRepository.checkBlockComments(userId,commentatorId);

        if(blockUserComment !=null ){
            throw new BadRequestException("ban bi chan comment");
        }
        if(blockComments!=null){
            throw new BusinessException("chu bai viet khoa comment");
        }
        if(blockDiary!=null){
            throw new DuplicateRecordException("dairy");
        }
        if(blockUser!=null){
            throw new InternalServerException("user");
        }



        Comment comment = new Comment();

        int countComment = post1.get().getNumberOfComments();

        countComment =countComment+1;
//
//

        User user =new User();
        user.setId(commentatorId);
        Post post = new Post();
        post.setId(postId);

        comment = CommentMapper.toComment(request,postId);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        postRepository.updateComment(countComment,postId);

        return commentMapper.toCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(UpdateCommentRequest request, int id ,int userId) {
        Optional<Comment> comment= commentRepository.findById(id);

        if (comment.isEmpty()) {
            throw new NotFoundException("No comment found");
        }

        int authorId = comment.get().getUser().getId();
        if(authorId !=userId){
            throw new BadRequestException("not access");
        }
        Comment updateComment = CommentMapper.toComment(request, id);
        try {
            updateComment.setCreated(comment.get().getCreated());
            updateComment.setPost(comment.get().getPost());
            updateComment.setUser(comment.get().getUser());

            commentRepository.save(updateComment);
        } catch (Exception ex) {
            throw new InternalServerException("Can't update comment");
        }

        return commentMapper.toCommentDTO(updateComment);
    }

    @Override
    public void deleteComment(int id,int userId) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isEmpty()) {
            throw new NotFoundException("No comment found");
        }
        int authorId = comment.get().getUser().getId();
        if(authorId !=userId){
            throw new BadRequestException("not access");
        }

        int postId = comment.get().getPost().getId();
        Optional<Post> post1 = postRepository.findById(postId);
        int countComment = post1.get().getNumberOfComments();
        countComment =countComment-1;

        try {
            commentRepository.deleteById(id);
            postRepository.updateComment(countComment,postId);
        } catch (Exception ex) {
            throw new InternalServerException("Can't delete comment");
        }
    }
}
