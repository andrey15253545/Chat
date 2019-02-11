package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.services.ChatService;
import by.touchsoft.chat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Key(url = "/msg .+", role = {Role.AGENT, Role.CLIENT})
@Component
public class SendMessageImpl implements Command {

    private final ChatService chatService;

    @Autowired
    public SendMessageImpl(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public String execute(User user, String arg) {
        return chatService.sendMessage(user.getId(), arg.substring(5));
    }
}
