package com.example.zalo.model.dto;

import com.example.zalo.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private Integer id;
    private  Integer peopleLikeId;
    private LocalDateTime updated;
    private Integer likePost;
}
