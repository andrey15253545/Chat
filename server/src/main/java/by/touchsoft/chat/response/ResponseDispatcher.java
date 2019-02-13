package by.touchsoft.chat.response;

import by.touchsoft.chat.model.Message;

import java.io.IOException;

public interface ResponseDispatcher {
    void sendMessage(Message message) throws IOException;
}
