package server.service;

import org.springframework.stereotype.Component;
import server.room.Room;
import server.user.User;
import server.waitingUser.WaitingUser;
;

@Component
public enum DialogService {

    INSTANCE;

    private static final String CONNECTED = "dialog created";
    private static final String ENDED = "%s left the dialog";
    private static final String WAIT = "please, wait";



    public String createDialog(User requestingUser, WaitingUser requestedUsers, WaitingUser requestingUsersList){
        User companion = requestedUsers.get();
        if (companion == null) {
            requestingUsersList.add(requestingUser);
            return WAIT;
        } else {
            requestingUser.setRoom(new Room(companion));
            companion.setRoom(new Room(requestingUser));
            sendMessage(companion, CONNECTED);
            return CONNECTED;
        }
    }

    public String endDialog(User user) {
        if (user==null) return "error";
        if (user.getRoom()==null) return "haven't companion";
        User companion = user.getRoom().getCompanion();
        companion.setRoom(null);
        user.setRoom(null);
        sendMessage(companion, String.format(ENDED,user.getName()));
        return String.format(ENDED,companion.getName());
    }

    private void sendMessage(User user, String message) {
        user.getResponse().sendMessage(message);
    }
}
