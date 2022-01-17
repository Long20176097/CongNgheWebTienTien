package com.example.zalo.service.impl;

import com.example.zalo.entity.ChatRoom;
import com.example.zalo.entity.User;
import com.example.zalo.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {


    @Autowired private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(
            int senderId, int recipientId, boolean createIfNotExist) {
          User sender = new User();
          sender.setId(senderId);
        User recipient = new User();
        recipient.setId(senderId);


        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                    var chatId =
                            String.format("%s_%s", senderId, recipientId);

                    ChatRoom senderRecipient = ChatRoom
                            .builder()
                            .chatId(chatId)
                            .senderId(sender)
                            .recipientId(recipient)
                            .build();

                    ChatRoom recipientSender = ChatRoom
                            .builder()
                            .chatId(chatId)
                            .senderId(recipient)
                            .recipientId(sender)
                            .build();
                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
}