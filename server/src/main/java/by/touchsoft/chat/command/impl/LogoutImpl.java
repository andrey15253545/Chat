package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Key(url = "/logout", role = {Role.AGENT, Role.CLIENT})
public class LogoutImpl implements Command {

    private final EndDialogImpl endDialog;

    @Autowired
    public LogoutImpl(EndDialogImpl endDialog) {
        this.endDialog = endDialog;
    }

    @Override
    public String execute(User user, String arg) {
        endDialog.execute(user, arg);
        user.setRole(Role.GUEST);
        return "Logged logout";
    }
}
