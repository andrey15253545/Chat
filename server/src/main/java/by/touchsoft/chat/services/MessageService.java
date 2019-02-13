package by.touchsoft.chat.services;

import by.touchsoft.chat.dao.MessageDao;
import by.touchsoft.chat.dao.UserDao;
import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.response.ResponseDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private final MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void setResponse(String id, ResponseDispatcher dispatcher) {
        messageDao.setResponse(id, dispatcher);
    }

    public List<Message> get(String id) {
        return messageDao.getNew(id);
    }

    public List<Message> getAll(String id){
        return messageDao.getAll(id);
    }


}
