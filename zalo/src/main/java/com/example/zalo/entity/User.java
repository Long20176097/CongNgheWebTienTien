package com.example.zalo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User implements Serializable {


    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name ="first_name")
    private String firstName;

    @Column(name ="last_name")
    private String lastName;

    @Column(name ="dob")
//    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dob;

    @Column(name ="gender")
    private String gender;

    @Column(name ="joined_date")
//    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinedDate;



    @Column(name ="status" )
    private String status;

//    @Column(name="registration_date")
//    private Timestamp registrationDate;

    @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL)
    private Authority authority;

    @Column(name = "link_avatar")
    private String linkAvatar;


    @OneToMany(mappedBy = "author" ,cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Post> posts;



    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "peopleLikeId" ,cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Like> likes;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userA")
    public List<Friend> followUsers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userB")
    public List<Friend> followers;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userA")
    public List<Block> blocker;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userB")
    public List<Block> blockedUser;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "senderId")
    public ChatMessage  senderId;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "recipientId")
    public ChatMessage recipientId;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "senderId")
    public ChatRoom  senderIdRoom;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "recipientId")
    public ChatRoom recipientIdRoom;

}
