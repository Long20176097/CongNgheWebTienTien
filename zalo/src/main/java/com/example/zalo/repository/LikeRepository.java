package com.example.zalo.repository;

import com.example.zalo.entity.Comment;
import com.example.zalo.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository  extends JpaRepository<Like, Integer> {

    @Transactional

    @Query(value = "SELECT id, user_id , post_id, updated FROM tbl_like  WHERE post_id = ?1 ", nativeQuery = true)
    public List<Like> getAllLike(int postId);

    @Transactional
    @Modifying
    @Query(value = "SELECT count(id) FROM tbl_like  WHERE post_id = ?1 ", nativeQuery = true)
    public int countLike(int postId);

    @Transactional

    @Query(value = "SELECT * FROM tbl_like  WHERE post_id = ?1 ", nativeQuery = true)
    Optional<Like> findByPostId(int postId);

    @Transactional

    @Query(value = "SELECT * FROM tbl_like  WHERE post_id = ?1 and user_id=?2 ", nativeQuery = true)
    Optional<Like> findByPostIdAndUserId(int postId ,int userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE  FROM tbl_like  WHERE post_id = ?1 ", nativeQuery = true)
    void deleteByPostId(int id);
}
