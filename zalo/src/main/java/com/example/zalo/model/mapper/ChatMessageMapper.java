package com.example.zalo.model.mapper;

import com.example.zalo.entity.ChatMessage;
import com.example.zalo.entity.User;
import com.example.zalo.model.dto.ChatMessageDTO;
import com.example.zalo.model.dto.UserDTO;

public class ChatMessageMapper {
    public static ChatMessageDTO toChatMessageDTO(ChatMessage chatMessage) {
        ChatMessageDTO tmp = new ChatMessageDTO();
        tmp.setId(chatMessage.getId());
        tmp.setSenderId(chatMessage.getSenderId().getId());
        tmp.setRecipientId(chatMessage.getRecipientId().getId());
        return tmp;
    }
}
