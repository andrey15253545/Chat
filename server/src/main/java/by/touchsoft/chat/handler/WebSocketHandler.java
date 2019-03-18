package by.touchsoft.chat.handler;

import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.factory.CommandFactory;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.response.impl.WebSocketResponseImpl;
import by.touchsoft.chat.services.ChatService;
import by.touchsoft.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final CommandFactory factory;
    private final UserService userService;
    private final ChatService chatService;
    private final ApplicationContext context;

    private static final String COMMAND_NOT_FOUND = "Command '%s' not found";

     @Autowired
     public WebSocketHandler(ApplicationContext context, CommandFactory factory, UserService userService, ChatService chatService) {
        this.factory = factory;
        this.userService = userService;
        this.context = context;
         this.chatService = chatService;
     }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String id = session.getId();
        User user = new User();
        WebSocketResponseImpl response = context.getBean(WebSocketResponseImpl.class);
        response.setSession(session);
        chatService.setResponse(id,response);
        user.setId(id);
        userService.add(user);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String mess = message.getPayload().trim();
        User user = userService.getById(session.getId());
        Command command = factory.getCommand(mess, user.getRole());
        String result = command != null ? command.execute(user, mess) : String.format(COMMAND_NOT_FOUND, mess);
        session.sendMessage(new TextMessage(result));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        User user = userService.getById(session.getId());
        Command command = factory.getCommand("/exit", user.getRole());
        command.execute(user, null);
    }
}
