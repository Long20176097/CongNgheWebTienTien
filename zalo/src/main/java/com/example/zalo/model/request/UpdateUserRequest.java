package com.example.zalo.model.request;
import com.example.zalo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
//    @NotNull(message = "Username is required")
//    @NotEmpty(message = "Username is required")
    @ApiModelProperty(
            example="thaimeo1",
            notes="Username cannot be empty",
            required=true
    )
    @JsonProperty("phone_number")
    private String phoneNumber;



    @ApiModelProperty(
            example="123",
            notes="",
            required=true
    )
    @JsonProperty("password")
    private String password;



    @ApiModelProperty(
            example="Da1",
            notes="First name cannot be empty",
            required=true
    )
    @JsonProperty("first_name")
    private String  firstName;

    @ApiModelProperty(
            example="Th1",
            notes="Last name cannot be empty",
            required=true
    )
    @JsonProperty("last_name")
    private String lastName;


    @ApiModelProperty(
            example="male",
            notes="Gender cannot be empty , 1-Male , 2-Female",
            required=true
    )
    @JsonProperty("gender")
    private String gender;

    @ApiModelProperty(
            example="Th1",
            notes="Last name cannot be empty",
            required=true
    )
    @JsonProperty("link_avatar")
    private String linkAvatar;


    @ApiModelProperty(
            example="enable",
            notes="Status cannot be empty , 1-Enable , 2-Disable",
            required=true
    )
    @JsonProperty("status")
    private String status;


    @ApiModelProperty(
            example="1999-06-02T21:33:45.249967",
            notes="Birth Date  cannot be empty",
            required=true
    )
    @JsonProperty("dob")
    private LocalDate dob;



    @ApiModelProperty(
            example="1999-06-02T21:33:45.249967",
            notes="Joined Date  cannot be empty",
            required=true
    )
    @JsonProperty("joined_date")
    private LocalDateTime joinedDate;




    @ApiModelProperty(
            example="user",
            notes="Gender cannot be empty , 1-ADMIN , 2-STAFF",
            required=true
    )
    @JsonProperty("authority")
    private String authority;


    @ApiModelProperty(
            example="user",
            notes="Gender cannot be empty , 1-ADMIN , 2-STAFF",
            required=true
    )
    @JsonProperty("user_id")
    private User user;





}
