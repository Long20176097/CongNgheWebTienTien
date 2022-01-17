package com.example.zalo.repository;

import com.example.zalo.entity.Block;
import com.example.zalo.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1  and state like '%chat%'  ", nativeQuery = true)
    public List<Block> getAllBlockChat(int userId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1  and state like '%diary%'  ", nativeQuery = true)
    public List<Block> getAllBlockDiary(int userId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1  and state like '%user%'  ", nativeQuery = true)
    public List<Block> getAllBlockUser(int userId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1  and state like '%comment%'  ", nativeQuery = true)
    public List<Block> getAllBlockUserComment(int userId);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1  and state like '%comments%'  ", nativeQuery = true)
    public List<Block> getAllBlockComments(int userId);

    @Transactional
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1 and user_b=?2 and state like '%user%' ", nativeQuery = true)
    Block checkBlockUser(int userAId,int userBId);

    @Transactional
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1 and user_b=?2 and state like '%diary%' ", nativeQuery = true)
    Block checkBlockDiary(int userAId,int userBId);

    @Transactional
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1 and user_b=?2 and state like '%comment%' ", nativeQuery = true)
    Block checkBlockUserComment(int userAId,int userBId);

    @Transactional
    @Query(value = "SELECT * FROM block  WHERE  user_a=?1 and user_b=?2 and state like '%comments%' ", nativeQuery = true)
    Block checkBlockComments(int userAId,int userBId);

}
