package server.command;

import server.user.User;

public interface Command {
    String execute(User user);
}
