package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Key(url = "/s", role = {Role.AGENT, Role.CLIENT})
@Component
public class StartDialogImpl implements Command {

    private final ChatService chatService;

    @Autowired
    public StartDialogImpl(ChatService chatService) {
        this.chatService = chatService;
    }

    public String execute(User user, String arg) {
        return chatService.create(user);
    }
}
