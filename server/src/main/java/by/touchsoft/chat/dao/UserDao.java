package by.touchsoft.chat.dao;

import by.touchsoft.chat.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class UserDao  {

    private List<User> agents = Collections.synchronizedList(new LinkedList<>());
    private List<User> clients = Collections.synchronizedList(new LinkedList<>());
    private Map<String, User> map = new HashMap<>();
    private List<User> freeAgents = Collections.synchronizedList(new LinkedList<>());
    private List<User> freeClients = Collections.synchronizedList(new LinkedList<>());

    public void addFreeAgent(User user) {
        freeAgents.add(user);
    }

    public void addFreeClient(User user) {
        freeClients.add(user);
    }

    public List<User> getFreeAgents() {
        return freeAgents;
    }

    public List<User> getFreeClients() {
        return freeClients;
    }

    public User getById(String id) {
        return map.get(id);
    }

    public List<User> getAgents() {
        return agents;
    }

    public List<User> getClients() {
        return clients;
    }

    public boolean isFree(User user) {
        return freeClients.contains(user) || freeAgents.contains(user);
    }

    public void addAgent(User user) {
        agents.add(user);
        add(user);
    }

    public void addUser(User user){
        add(user);
    }

    public void addClient(User user) {
        clients.add(user);
        add(user);
    }

    private void add(User user){
        if(!map.containsKey(user.getId())){
            map.put(user.getId(),user);
        }
    }

    public boolean removeFree(User user){
        return freeAgents.remove(user) || freeClients.remove(user);
    }

    public User removeAgent() {
        return freeAgents.size() == 0 ? null : freeAgents.remove(0);
    }

    public User removeClient() {
        return freeClients.size() == 0 ? null : freeClients.remove(0);
    }

}
