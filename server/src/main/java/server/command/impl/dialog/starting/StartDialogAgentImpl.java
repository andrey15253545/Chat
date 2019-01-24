package server.command.impl.dialog.starting;

import server.annotation.Key;
import server.command.Command;
import server.service.DialogService;
import server.user.Role;
import server.user.User;
import server.waitingUser.WaitingUser;

@Key(url = "/s", role = {Role.AGENT})
public class StartDialogAgentImpl implements Command {

    private static final DialogService dialogService = DialogService.INSTANCE;
    private static final WaitingUser clients = WaitingUser.CLIENT;
    private static final WaitingUser agents = WaitingUser.AGENT;

    public synchronized String execute(User user) {
        return dialogService.createDialog(user, clients, agents);
    }
}
