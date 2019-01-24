package server.room;

import org.springframework.stereotype.Component;
import server.user.User;

@Component
public class Room{

    private User companion;

    public Room(User companion) {
        this.companion = companion;
    }

    public User getCompanion() {
        return companion;
    }
}
