package server.command.impl.exit;

import server.annotation.Key;
import server.command.Command;
import server.command.impl.logout.LogoutImpl;
import server.user.Role;
import server.user.User;
@Key(url = "/exit", role = {Role.AGENT, Role.CLIENT})
public class ExitLoggedInUserImpl implements Command {
    @Override
    public synchronized String execute(User user) {
        new LogoutImpl().execute(user);
        return new ExitGuestImpl().execute(user);
    }
}
