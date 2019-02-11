package by.touchsoft.chat.command.impl;

import by.touchsoft.chat.annotation.Key;
import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Key(url = "/exit", role = {Role.AGENT, Role.CLIENT})
public class ExitLoggedInUserImpl implements Command {

    private final LogoutImpl logout;
    private final ExitGuestImpl exitGuest;

    @Autowired
    public ExitLoggedInUserImpl(LogoutImpl logout, ExitGuestImpl exitGuest) {
        this.logout = logout;
        this.exitGuest = exitGuest;
    }

    @Override
    public synchronized String execute(User user, String arg) {
        logout.execute(user, arg);
        return exitGuest.execute(user, arg);
    }
}
