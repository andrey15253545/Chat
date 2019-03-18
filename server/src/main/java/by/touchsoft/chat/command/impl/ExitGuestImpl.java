package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
@Key(url = "/exit", role = Role.GUEST)
public class ExitGuestImpl implements Command {

    private static final String EXITED = "exited";

    @Override
    public String execute(User user, String arg) {
        return EXITED;
    }
}
