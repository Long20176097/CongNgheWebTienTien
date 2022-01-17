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
public class CreateLikeRequest {


    @JsonProperty("user_id")
    private Integer peopleLikeId;

    @JsonProperty("updated")
    private LocalDateTime updated;

    @JsonProperty("post_id")
    private Integer likePost;
}
