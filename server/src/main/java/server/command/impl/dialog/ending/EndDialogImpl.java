package server.command.impl.dialog.ending;

import server.annotation.Key;
import server.command.Command;
import server.service.DialogService;
import server.user.Role;
import server.user.User;

@Key(url = "/end", role = {Role.AGENT, Role.CLIENT})
public class EndDialogImpl implements Command {

    private final DialogService dialogService = DialogService.INSTANCE;

    public String execute(User user) {
        return dialogService.endDialog(user);
    }

}
