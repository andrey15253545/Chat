package server.command.impl.logout;

import server.annotation.Key;
import server.command.Command;
import server.command.impl.dialog.ending.EndDialogImpl;
import server.user.Role;
import server.user.User;

@Key(url = "/logout", role = {Role.AGENT, Role.CLIENT})
public class LogoutImpl implements Command {
    @Override
    public String execute(User user) {
        new EndDialogImpl().execute(user);
        user.setRole(Role.GUEST);
        return "Logged logout";
    }
}
