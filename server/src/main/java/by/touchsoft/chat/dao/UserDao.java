package by.touchsoft.chat.dao;

import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

import static by.touchsoft.chat.model.Role.AGENT;
import static by.touchsoft.chat.model.Role.CLIENT;
import static by.touchsoft.chat.model.Role.GUEST;


@Repository
public class UserDao  {

    private List<User> agents = Collections.synchronizedList(new LinkedList<>());
    private List<User> clients = Collections.synchronizedList(new LinkedList<>());
    private Map<String, User> map = new HashMap<>();
    private List<User> freeAgents = Collections.synchronizedList(new LinkedList<>());
    private List<User> freeClients = Collections.synchronizedList(new LinkedList<>());

    private void addFreeAgent(User user) {
        freeAgents.add(user);
    }

    private void addFreeClient(User user) {
        freeClients.add(user);
    }

    public List<User> getFree (Role role){
        if (role == Role.AGENT) {
            return freeAgents;
        }
        else if (role == Role.CLIENT) {
            return freeClients;
        }
        else {
            return new ArrayList<>();
        }
    }

    public User getById(String id) {
        return map.get(id);
    }

    public List<User> get(Role role) {
        if (role == AGENT) {
            return agents;
        }
        else if (role == CLIENT){
            return clients;
        }
        else{
            List<User> list = new ArrayList<>();
            for (User user : map.values()){
                if (user.getRole()==GUEST){
                    list.add(user);
                }
            }
            return list;
        }
    }

    public boolean isFree(User user) {
        return user.getRole() == Role.AGENT ? freeAgents.contains(user) : freeClients.contains(user);
    }

    public void addAgent(User user) {
        agents.add(user);
        add(user);
    }

    public void addFree(User user) {
        if (user.getRole()==AGENT){
            addFreeAgent(user);
        }
        else if (user.getRole()==CLIENT) {
            addFreeClient(user);
        }
    }

    public boolean delete(User user) {
        if (user == null) {
            return false;
        }
        else {
            if (user.getRole()==AGENT) {
                freeAgents.remove(user);
                agents.remove(user);
            }
            else if (user.getRole()==CLIENT) {
                freeClients.remove(user);
                clients.remove(user);
            }
        }
        return map.remove(user.getId()) != null;
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
