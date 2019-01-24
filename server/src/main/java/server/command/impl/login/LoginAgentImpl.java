package server.command.impl.login;

import server.annotation.Key;
import server.command.Command;
import server.user.Role;
import server.user.User;

@Key(url = "/a \\w+", role = {Role.GUEST})
public class LoginAgentImpl implements Command {

    private static final String MESSAGE = "you logged as an agent";

    private String getName(String url){
        return url.contains(" ") ? url.substring(url.indexOf(" ")+1) : url;
    }

    public String execute (User user) {
        user.setRole(Role.AGENT);
        user.setName(getName(user.getLastMessage()));
        return MESSAGE;
    }
}
