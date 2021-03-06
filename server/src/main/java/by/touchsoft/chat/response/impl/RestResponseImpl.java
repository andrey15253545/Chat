package by.touchsoft.chat.response.impl;

import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.response.ResponseDispatcher;
import by.touchsoft.chat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RestResponseImpl implements ResponseDispatcher {

    private final MessageService messageDao;
    private String id;

    @Autowired
    public RestResponseImpl(MessageService messageDao) {
        this.messageDao = messageDao;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void sendMessage(Message message) {
        messageDao.addNew(id, message);
    }
}
