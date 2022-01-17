package com.example.zalo.model.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockDTO {

    public int id;

    public Integer userA;

    public Integer userB;

    private LocalDateTime created;

    private String state;

    private int postId;

}
