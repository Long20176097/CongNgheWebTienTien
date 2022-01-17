package com.example.zalo.service.impl;

import com.example.zalo.entity.Block;
import com.example.zalo.entity.Post;
import com.example.zalo.entity.User;
import com.example.zalo.exception.*;
import com.example.zalo.model.dto.PostDTO;
import com.example.zalo.model.mapper.PostMapper;
import com.example.zalo.model.request.CreatePostRequest;
import com.example.zalo.model.request.UpdatePostRequest;
import com.example.zalo.repository.BlockRepository;
import com.example.zalo.repository.PostRepository;
import com.example.zalo.repository.UserRepository;
import com.example.zalo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private  final UserRepository userRepository;
    private final BlockRepository blockRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, BlockRepository blockRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.blockRepository = blockRepository;
    }

    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts =postRepository.findAll();
        List<PostDTO> result = new ArrayList<>();
        for (Post post:posts){
            result.add(PostMapper.toPostDTO(post));
        }
        return result;
    }

    @Override
    public List<PostDTO> getAllUserPost(int userId,int authorId) {
        Block blockDiary= blockRepository.checkBlockDiary(userId,authorId);
        Block blockUser= blockRepository.checkBlockUser(userId,authorId);

        if(blockDiary != null){
            throw new DuplicateRecordException("diary");
        }
        if(blockUser != null){
            throw new InternalServerException("user");
        }
        List<Post> posts =postRepository.findPostByUserId(authorId);
        Optional<User> user2 = userRepository.findById(authorId);
        if(user2.isEmpty()){
            throw new BusinessException("khong tim thay nguoi nay");
        }
        if(posts==null){
            throw new NotFoundException("No post found");
        }
        List<PostDTO> result = new ArrayList<>();
        for (Post post:posts){
            result.add(PostMapper.toPostDTO(post));
        }
        return result;
    }

    @Override
    public PostDTO getPostById(int id,int userId,int authorId) {
        Block blockDiary= blockRepository.checkBlockDiary(authorId,userId);
        Block blockUser= blockRepository.checkBlockUser(authorId,userId);

        if(blockDiary != null){
            throw new DuplicateRecordException("diary");
        }
        if(blockUser != null){
            throw new InternalServerException("user");
        }

        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new NotFoundException("No post found");
        }
        return PostMapper.toPostDTO(post.get());
    }
    @Override
    public PostDTO createPost(CreatePostRequest request,int authorId) {
        Post post = new Post();
        User user = new User();
        user.setId(authorId);
        post = PostMapper.toPost(request);
        post.setAuthor(user);
        try {
            postRepository.save(post);
        }
        catch (Exception ex) {
            throw new InternalServerException("Can't create post");
        }
        return PostMapper.toPostDTO(post);
    }

    @Override
    public PostDTO updatePost(UpdatePostRequest request, int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new NotFoundException("No post found");
        }

        Post updatePost = PostMapper.toPost(request, id);
        updatePost.setAuthor(post.get().getAuthor());
        updatePost.setCreated(post.get().getCreated());
        updatePost.setNumberOfLikes(post.get().getNumberOfLikes());
        updatePost.setNumberOfComments(post.get().getNumberOfComments());
        try {
            postRepository.save(updatePost);
        } catch (Exception ex) {
            throw new InternalServerException("Can't update post");
        }

        return PostMapper.toPostDTO(updatePost);
    }

    @Override
    public void deletePost(int id,int userId) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) {
            throw new NotFoundException("No post found");
        }
        int authorId = post.get().getAuthor().getId();
        if(authorId !=userId){
            throw new BadRequestException("not access");
        }

        try {
            postRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Can't delete post");
        }
    }
}
