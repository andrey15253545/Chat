package server.command.impl.login;

import server.annotation.Key;
import server.command.Command;
import server.user.Role;
import server.user.User;

@Key(url = "/c \\w+", role = {Role.GUEST})
public class LoginClientImpl implements Command {

    private static final String MESSAGE = "You logged as a client";

    private String getName(String url){
        return url.contains(" ") ? url.substring(url.indexOf(" ")+1) : url;
    }

    public String execute (User user) {
        user.setRole(Role.CLIENT);
        user.setName(getName(user.getLastMessage()));
        return MESSAGE;
    }
}
