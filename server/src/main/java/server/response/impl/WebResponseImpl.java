package server.response.impl;

import org.apache.log4j.Logger;
import server.response.ResponseDispatcher;

import javax.websocket.Session;
import java.io.IOException;

public class WebResponseImpl implements ResponseDispatcher {

    private Session session;
    private static final Logger logger = Logger.getLogger(ConsoleResponseImpl.class);

    public WebResponseImpl(Session session) {
        this.session = session;
    }

    @Override
    public String sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
            return "message send";
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("message doesn't send", e);
            return "message doesn't send";
        }
    }
}
