package com.example.zalo.model.dto;

import com.example.zalo.entity.MessageStatus;
import com.example.zalo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {

    private Integer id;

    private String chatId;


    private Integer senderId;


    private Integer recipientId;


    private String senderName;

    private String recipientName;


    private String content;


    private Date timestamp;


    private MessageStatus status;
}
