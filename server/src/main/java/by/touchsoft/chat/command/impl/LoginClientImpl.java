package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Key(url = "/c \\w+", role = {Role.GUEST})
public class LoginClientImpl implements Command {

    private static final String MESSAGE = "You logged as a client %s";

    private final UserService userService;

    @Autowired
    public LoginClientImpl(UserService userService)
    {
        this.userService= userService;
    }

    public String execute (User user, String arg) {
        user.setRole(Role.CLIENT);
        String name = arg.split(" ")[1];
        user.setName(name);
        userService.addClient(user);
        return String.format(MESSAGE, name);
    }
}


