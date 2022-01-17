package com.example.zalo.repository;

import com.example.zalo.entity.Post;
import com.example.zalo.entity.User;
import com.example.zalo.model.dto.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query(value = "SELECT * FROM post WHERE author_id = ?1 ", nativeQuery = true)
    List<Post> findPostByUserId(int id);

    @Query(value = "SELECT * FROM post WHERE author_id = ?1 and id=?2", nativeQuery = true)
    List<Post> findPostByUserIdAndPostId(int authorId,int id);



//sai
//    @Transactional
//    @Modifying
//    @Query(value = "select number_of_comments from post  WHERE id = ?1", nativeQuery = true)
//     int findComment(int postId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE post SET number_of_comments = ?1  WHERE id = ?2", nativeQuery = true)
    void updateComment(int comment, int postId);


    @Transactional
    @Modifying
    @Query(value = "UPDATE post SET number_of_likes = ?1  WHERE id = ?2", nativeQuery = true)
    void updateLike(int comment, int postId);


}
