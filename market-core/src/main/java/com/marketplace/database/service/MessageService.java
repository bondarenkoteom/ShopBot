package com.marketplace.database.service;

import com.marketplace.database.jpa.model.Message;
import com.marketplace.database.jpa.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Page<Message> getChatMessages(Long superId, Long id, Pageable pageable) {
        return messageRepository.findChatMessages(superId, id, pageable);
    }
}
