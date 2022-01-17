package com.example.zalo.model.mapper;

import com.example.zalo.entity.Block;
import com.example.zalo.entity.Friend;
import com.example.zalo.model.dto.BlockDTO;
import com.example.zalo.model.dto.FriendDTO;
import com.example.zalo.model.request.CreateBlockRequest;
import com.example.zalo.model.request.CreateFriendRequest;
import com.example.zalo.model.request.UpdateBlockRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BlockMapper {
    public static BlockDTO toBlockDTO(Block block) {
        BlockDTO tmp = new BlockDTO();
        tmp.setId(block.getId());
        tmp.setUserA(block.getUserA().getId());
        tmp.setUserB(block.getUserB().getId());
        tmp.setPostId(block.getPostId());
        tmp.setCreated(block.getCreated());
        tmp.setState(block.getState());
        return tmp;
    }


    public static Block toBlockChat() {
        Block block = new Block();
        block.setState("chat");


        LocalDateTime now = LocalDateTime.now();
        block.setCreated(now);
        return block;
    }

    public static Block toBlockDiary() {
        Block block = new Block();
        block.setState("diary");
        LocalDateTime now = LocalDateTime.now();
        block.setCreated(now);
        return block;
    }


    public static Block toBlockUser() {
        Block block = new Block();
        block.setState("user");
        LocalDateTime now = LocalDateTime.now();
        block.setCreated(now);
        return block;
    }


    public static Block toBlockUserComment() {
        Block block = new Block();
        block.setState("comment");
        LocalDateTime now = LocalDateTime.now();
        block.setCreated(now);
        return block;
    }

    public static Block toBlockComments() {
        Block block = new Block();
        block.setState("comments");
        LocalDateTime now = LocalDateTime.now();
        block.setCreated(now);
        return block;
    }

}
