package com.example.zalo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CreateBlockRequest {

    @JsonProperty("user_a")
    private Integer userA;

    @JsonProperty("user_b")
    private Integer userB;


    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("block_chat")
    private String blockChat;

    @JsonProperty("block_diary")
    private String blockDiary;

    @JsonProperty("block_user")
    private String user;

}
