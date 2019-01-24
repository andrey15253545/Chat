package server.user;

import server.room.Room;
import server.response.ResponseDispatcher;

public class User {

    private Room room;
    private ResponseDispatcher response;
    private Role role = Role.GUEST;
    private String lastMessage = "hi";
    private String name = "user";

    public User (ResponseDispatcher response){
        this.response = response;
    }

    public User(){}

    public void setResponse(ResponseDispatcher response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResponseDispatcher getResponse() {
        return response;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
