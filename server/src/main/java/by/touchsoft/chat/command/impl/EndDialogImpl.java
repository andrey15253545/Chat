package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Key(url = "/end", role = {Role.AGENT, Role.CLIENT})
@Component
public class EndDialogImpl implements Command {

    private final ChatService dialogService;

    @Autowired
    public EndDialogImpl(ChatService dialogService) {
        this.dialogService = dialogService;
    }

    public String execute(User user, String arg) {
        return dialogService.endDialog(user);
    }

}
