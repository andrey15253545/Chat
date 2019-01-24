package server.command.impl.message;

import server.annotation.Key;
import server.command.Command;
import server.user.Role;
import server.user.User;
import server.room.Room;

@Key(url = "/msg", role = {Role.AGENT, Role.CLIENT})
public class SendMessageImpl implements Command {

    private static final String MESSAGE_FORMAT = "%s : %s";

    @Override
    public synchronized String execute(User user) {
        if (user == null ) return "error";
        Room room = user.getRoom();
        if (room == null) return "you haven't companion";
        User companion = room.getCompanion();
        String mess = String.format(MESSAGE_FORMAT,user.getLastMessage(),user.getName());
        return companion.getResponse().sendMessage(mess);
    }
}
