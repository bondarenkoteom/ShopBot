package com.shop.ShopBot.database.service;

import com.shop.ShopBot.database.model.Message;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.database.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void save(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getChatMessages(Long superId, Long id) {
        return messageRepository.findChatMessages(superId, id);
    }
}
