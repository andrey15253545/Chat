package by.touchsoft.chat.command.impl;


import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import org.springframework.stereotype.Component;

@Component
@Key(url = "/help", role = {Role.AGENT, Role.CLIENT})
public class HelpLoggedImpl implements Command{

    private static final String HELP =
            "Enter \n" +
                    "\t\"/s\" to find companion \n" +
                    "\t\"<your message>\" to send a message to a companion \n"+
                    "\t\"/end\" to leave the chat\n" +
                    "\t\"/logout\" to logout\n" +
                    "\t\"/exit\" to exit\n";
    @Override
    public String execute(User user, String arg) {
        return HELP;
    }
}
