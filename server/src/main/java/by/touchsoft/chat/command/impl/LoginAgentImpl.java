package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Key(url = "/a \\w+", role = {Role.GUEST})
@Component
public class LoginAgentImpl implements Command {

    private final UserService userService;

    private static final String MESSAGE = "You logged as an agent %s";

    @Autowired
    public LoginAgentImpl(UserService userService) {
        this.userService = userService;
    }

    public String execute (User user, String arg) {
        user.setRole(Role.AGENT);
        String name = arg.split(" ")[1];
        user.setName(name);
        userService.addAgent(user);
        return String.format(MESSAGE, name);
    }
}
