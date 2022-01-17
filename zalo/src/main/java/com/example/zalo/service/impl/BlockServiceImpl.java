package com.example.zalo.service.impl;

import com.example.zalo.entity.Block;
import com.example.zalo.entity.Post;
import com.example.zalo.entity.User;
import com.example.zalo.exception.BadRequestException;
import com.example.zalo.exception.DuplicateRecordException;
import com.example.zalo.exception.InternalServerException;
import com.example.zalo.exception.NotFoundException;
import com.example.zalo.model.dto.BlockDTO;
import com.example.zalo.model.mapper.BlockMapper;
import com.example.zalo.repository.BlockRepository;
import com.example.zalo.repository.PostRepository;
import com.example.zalo.repository.UserRepository;
import com.example.zalo.service.BlockService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;
    private final PostRepository postRepository;
    private final  UserRepository userRepository;
    public BlockServiceImpl(BlockRepository blockRepository, PostRepository postRepository, UserRepository userRepository) {
        this.blockRepository = blockRepository;

        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BlockDTO> getAllBlockChat(int userId) {
        List<Block> blocks = blockRepository.getAllBlockChat(userId);
        List<BlockDTO> result = new ArrayList<>();
        for (Block block:blocks){
            result.add(BlockMapper.toBlockDTO(block));
        }
        return result;
    }

    @Override
    public List<BlockDTO> getAllBlockDiary(int userId) {
        List<Block> blocks = blockRepository.getAllBlockDiary(userId);
        List<BlockDTO> result = new ArrayList<>();
        for (Block block:blocks){
            result.add(BlockMapper.toBlockDTO(block));
        }
        return result;
    }

    @Override
    public void createBlockChatRequest(int userAId, int userBId) {
        Optional<User> user2 = userRepository.findById(userBId);
        if(user2.isEmpty()){
            throw new NotFoundException("khong tim thay nguoi nay");
        }

        if(userAId == userBId){
            throw new BadRequestException("You cannot send block requests to yourself");
        }

        List<Block> blocks= blockRepository.findAll();
        for(Block b:blocks){
            if(b.getUserA().getId()==userAId && b.getUserB().getId()==userBId && b.getState().equals("chat") ){
                    throw new DuplicateRecordException("you have blocked this person's messages before");
            }
        }

        Block block = new Block();
        User userA = new User();
        User userB = new User();

        userA.setId(userAId);
        userB.setId(userBId);

        block.setUserA(userA);
        block.setUserB(userB);

        block= BlockMapper.toBlockChat();
        block.setUserA(userA);
        block.setUserB(userB);

        try {
            blockRepository.save(block);
        } catch (Exception ex) {
            throw new InternalServerException("Can't block this person");
        }
    }

    @Override
    public void createBlockDiaryRequest( int userAId, int userBId) {
        Optional<User> user2 = userRepository.findById(userBId);
        if(user2.isEmpty()){
            throw new NotFoundException("khong tim thay nguoi nay");
        }

        if(userAId == userBId){
            throw new BadRequestException("You cannot send block requests to yourself");
        }

        List<Block> blocks= blockRepository.findAll();
        for(Block b:blocks){
            if(b.getUserA().getId()==userAId && b.getUserB().getId()==userBId && b.getState().equals("diary") ){
                throw new DuplicateRecordException("you blocked this person before");
            }
        }
        Block block = new Block();
        User userA = new User();
        User userB = new User();

        userA.setId(userAId);
        userB.setId(userBId);

        block.setUserA(userA);
        block.setUserB(userB);

        block= BlockMapper.toBlockDiary();
        block.setUserA(userA);
        block.setUserB(userB);

        try {
            blockRepository.save(block);
        } catch (Exception ex) {
            throw new InternalServerException("Can't block this person");
        }
    }

    @Override
    public void createBlockUserRequest(int userAId, int userBId) {
        Optional<User> user2 = userRepository.findById(userBId);
        if(user2.isEmpty()){
            throw new NotFoundException("khong tim thay nguoi nay");
        }

        if(userAId == userBId){
            throw new BadRequestException("You cannot send block requests to yourself");
        }

        List<Block> blocks= blockRepository.findAll();
        for(Block b:blocks){
            if(b.getUserA().getId()==userAId && b.getUserB().getId()==userBId && b.getState().equals("user") ){
                throw new DuplicateRecordException("you blocked this person before");
            }
        }
        Block block = new Block();
        User userA = new User();
        User userB = new User();

        userA.setId(userAId);
        userB.setId(userBId);

        block.setUserA(userA);
        block.setUserB(userB);

        block= BlockMapper.toBlockUser();
        block.setUserA(userA);
        block.setUserB(userB);

        try {
            blockRepository.save(block);
        } catch (Exception ex) {
            throw new InternalServerException("Can't block this person");
        }
    }

    @Override
    public void createBlockUserCommentRequest(int userAId, int userBId) {
        Optional<User> user2 = userRepository.findById(userBId);
        if(user2.isEmpty()){
            throw new NotFoundException("khong tim thay nguoi nay");
        }

        if(userAId == userBId){
            throw new BadRequestException("You cannot send block requests to yourself");
        }

        List<Block> blocks= blockRepository.findAll();
        for(Block b:blocks){
            if(b.getUserA().getId()==userAId && b.getUserB().getId()==userBId && b.getState().equals("comment") ){
                throw new DuplicateRecordException("you blocked this person before");
            }
        }
        Block block = new Block();
        User userA = new User();
        User userB = new User();

        userA.setId(userAId);
        userB.setId(userBId);

        block.setUserA(userA);
        block.setUserB(userB);

        block= BlockMapper.toBlockUserComment();
        block.setUserA(userA);
        block.setUserB(userB);

        try {
            blockRepository.save(block);
        } catch (Exception ex) {
            throw new InternalServerException("Can't block this person");
        }
    }

    @Override
    public void createBlockCommentsRequest(int userAId, int postId) {
        List<Post> post = postRepository.findPostByUserIdAndPostId(userAId,postId);
        Optional<Post> post1 = postRepository.findById(postId);

        if(post1.isEmpty()){
            throw new NullPointerException("Bai viet ko ton tai");
        }
        if(post==null){
            throw new BadRequestException("Ban ko so huu bai viet nay");
        }

        List<Block> blocks= blockRepository.findAll();
        for(Block b:blocks){
            if(b.getUserA().getId()==userAId && b.getPostId()==postId && b.getState().equals("comments") ){
                throw new DuplicateRecordException("you blocked this post before");
            }
        }
        Block block = new Block();
        User userA = new User();

        userA.setId(userAId);

        block.setUserA(userA);

        block= BlockMapper.toBlockComments();
        block.setUserA(userA);
        block.setPostId(postId);

        try {
            blockRepository.save(block);
        } catch (Exception ex) {
            throw new InternalServerException("Can't block comments");
        }
    }

    @Override
    public void deleteBlockRequest(int id) {
        Optional<Block> block = blockRepository.findById(id);
        if (block.isEmpty()) {
            throw new NotFoundException("No block found");
        }

        try {
            blockRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Can't delete this block");
        }

    }
}
