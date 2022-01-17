package com.example.zalo.repository;


import com.example.zalo.entity.Comment;
import com.example.zalo.model.dto.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
@Repository
public interface CommentRepository  extends JpaRepository<Comment, Integer> {
    @Transactional
    @Modifying
    @Query(value = "SELECT id,content, user_id , post_id, created,updated FROM comment  WHERE post_id = ?1 ", nativeQuery = true)
    public List<Comment> getAllComment(int postId);

//    @Transactional
//    @Modifying
//    @Query(value = "SELECT count(id) FROM comment  WHERE post_id = ?1 ", nativeQuery = true)
//    public int countComment(int postId);

}
