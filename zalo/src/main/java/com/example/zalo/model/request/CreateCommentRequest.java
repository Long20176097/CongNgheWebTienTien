package com.example.zalo.model.request;

import com.example.zalo.entity.Post;
import com.example.zalo.entity.User;
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
public class CreateCommentRequest {
    @ApiModelProperty(
            example="content",
            required=true
    )
    @JsonProperty("content")
    private String content;

    @ApiModelProperty(
            example="content",
            required=true
    )
    @JsonProperty("user_id")
    private Integer user;

    @ApiModelProperty(
            example="1999-06-02T21:33:45.249967",
            required=true
    )
    @JsonProperty("updated")
    private LocalDateTime updated;

    @JsonProperty("created")
    private LocalDateTime created;
    @ApiModelProperty(
            example="content",
            required=true
    )
    @JsonProperty("post_id")
    private Integer Post;
}
