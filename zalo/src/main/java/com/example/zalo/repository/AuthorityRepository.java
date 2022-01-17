package com.example.zalo.repository;


import com.example.zalo.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository  extends JpaRepository<Authority, Integer> {
    @Query(value = "SELECT user_id from authorities ", nativeQuery = true)
    Authority findByUserId(int user_id);

}
