package by.touchsoft.chat.services;

import by.touchsoft.chat.dao.MessageDao;
import by.touchsoft.chat.dao.UserDao;
import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.response.ResponseDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private final MessageDao messageDao;

    public Message generateMessage(User user, String text) {
        return new Message(user, new  Date(), text);
    }

    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    /**
     *
     * @param id - index of the user to whom the message is sent
     * @param message {@link Message} - message to be send
     * @return true if message send and false if message does't send
     */
    public boolean send(String id, Message message) {
        messageDao.add(id,message);
        ResponseDispatcher response = messageDao.getResponse(id);
        return response.sendMessage(message);
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
