package by.touchsoft.chat.response;

import by.touchsoft.chat.model.Message;

public interface ResponseDispatcher {
    boolean sendMessage(Message message);
}
