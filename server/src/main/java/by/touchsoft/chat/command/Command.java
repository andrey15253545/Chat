package by.touchsoft.chat.command;


import by.touchsoft.chat.model.User;

public interface Command {
    String execute(User user, String arg);
}
