package server.waitingUser;

import server.user.User;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public enum WaitingUser{

    AGENT,
    CLIENT;

    private List<Object> users = Collections.synchronizedList(new LinkedList<>());

    public void add(User user) {
        users.add(user);
    }

    public User get() {
        return users.isEmpty() ? null : (User)users.remove(0);
    }

    public void clear() {
        users.clear();
    }

}
