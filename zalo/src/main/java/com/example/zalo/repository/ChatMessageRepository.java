package com.example.zalo.repository;

import com.example.zalo.entity.ChatMessage;
import com.example.zalo.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Integer> {
    long countBySenderIdAndRecipientIdAndStatus(
            int senderId, int recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE chat_message SET status = ?3 WHERE sender_id =  :?1  and recipient_id= :?2", nativeQuery = true)
    void updateStatus(int senderId, int recipientId, MessageStatus status);
}
