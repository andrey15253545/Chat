package server.command.impl.exit;

import server.annotation.Key;
import server.command.Command;
import server.user.Role;
import server.user.User;
@Key(url = "/exit", role = Role.GUEST)
public class ExitGuestImpl implements Command {

    private static final String EXITED = "exited";

    @Override
    public String execute(User user) {
        return EXITED;
    }
}
