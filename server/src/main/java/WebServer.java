import server.command.Command;
import server.factory.CommandFactory;
import server.response.impl.WebResponseImpl;
import server.user.User;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/chat-1.0-SNAPSHOT/ws")
public class WebServer {

    private static final String COMMAND_NOT_FOUND = "Command '%s' not found";
    private static final String RESPONSE = "%s : %s";
    private Map<String, User> mapUsers = new HashMap<>();


    @OnOpen
    public void open(Session session) throws IOException, EncodeException {
        session.getBasicRemote().sendText("(Server): Welcome to the chat room");
        User user = new User();
        user.setResponse(new WebResponseImpl(session));
        mapUsers.put(session.getId(),user);
    }

    @OnClose
    public void close(Session session) throws IOException, EncodeException {
        User user = mapUsers.get(session.getId());
        Command command = CommandFactory.INSTANCE.getCommand("/exit", user.getRole());
        command.execute(user);
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException, EncodeException {
        User user = mapUsers.get(session.getId());
        user.setLastMessage(message);
        Command command = CommandFactory.INSTANCE.getCommand(message, user.getRole());
        String result = command != null ? command.execute(user) : String.format(COMMAND_NOT_FOUND,message);
        sendMessage(session, String.format(RESPONSE,message,result));
    }

    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
