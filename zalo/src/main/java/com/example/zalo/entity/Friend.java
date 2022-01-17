package com.example.zalo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.ui.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "friend")
public class Friend   implements Serializable {
    @Id
    @Column(name ="fid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;


    @JoinColumn(name = "user_a", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    public User userA;


    @JoinColumn(name = "user_b", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    public User userB;


    @Column(name ="created")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @Column(name = "state")
    private String state;

    }

