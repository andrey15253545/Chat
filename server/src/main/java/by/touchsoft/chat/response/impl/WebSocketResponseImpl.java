package by.touchsoft.chat.response.impl;

import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.response.ResponseDispatcher;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketResponseImpl implements ResponseDispatcher {

    private WebSocketSession session;

    public WebSocketResponseImpl(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public boolean sendMessage(Message message) {
        try {
            session.sendMessage(new TextMessage(message.toString()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            //      logger.warn("message doesn't send", e);
            return false;
        }
    }
}
