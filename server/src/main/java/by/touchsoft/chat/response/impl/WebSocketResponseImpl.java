package by.touchsoft.chat.response.impl;

import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.response.ResponseDispatcher;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
@Scope("prototype")
@Setter
public class WebSocketResponseImpl implements ResponseDispatcher {

    private WebSocketSession session;

    @Override
    public void sendMessage(Message message) throws IOException {
//        byte[] data = SerializationUtils.serialize(text);
//        session.sendMessage(new BinaryMessage(data != null ? data : new byte[0]));
        session.sendMessage(new TextMessage(message.getText()));
    }
}

