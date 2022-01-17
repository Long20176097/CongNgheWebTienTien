package com.example.zalo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendDTO {
    private Integer id;

    private Integer userA;// người gửi

    private Integer userB;// người nhận

    private LocalDateTime created;

    private String  state;
}
