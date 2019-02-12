package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import org.springframework.stereotype.Component;


@Component
@Key(url = "/help", role = Role.GUEST)
public class HelpGuestImpl implements Command {

    private static final String HELP =
            "Enter \n" +
            "\t\"/a <you name>\" to login as an Agent\n" +
            "\t\"/с <you name>\" to login as a Client\n" +
            "\t\"/exit\" to exit";

    @Override
    public String execute(User user, String arg) {
        return HELP;
    }
}
