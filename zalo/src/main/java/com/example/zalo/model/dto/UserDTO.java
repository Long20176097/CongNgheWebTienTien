package com.example.zalo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String gender;

    private LocalDateTime joinedDate;

    private String status;

//    @JsonIgnore
    private String password;

    private String linkAvatar;

    private String authority;









}
