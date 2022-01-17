package com.example.zalo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateFriendRequest {


    @JsonProperty("user_a")
    private Integer userA;

    @JsonProperty("user_b")
    private Integer userB;


    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("state")
    private String state;
}
