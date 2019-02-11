package by.touchsoft.chat.services;

import by.touchsoft.chat.dao.UserDao;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addFree(User user) {
        if (user.getRole() == Role.AGENT) {
            userDao.addFreeAgent(user);
        } else {
            userDao.addFreeClient(user);
        }
    }

    public void add(User user) {
        userDao.addUser(user);
    }

    public User addClient(User user) {
        userDao.addClient(user);
        return user;
    }

    public boolean isFree(User user) {
        return userDao.isFree(user);
    }

    public User addLogged(User user) {
        return user.getRole()== Role.AGENT ? addAgent(user) : addClient(user);
    }

    public User addAgent(User user) {
        userDao.addAgent(user);
        return user;
    }

    public List<User> getAgents(int pageNumber, int pageSize){
        List<User> users = userDao.getAgents();
        return get(users, pageNumber, pageSize);
    }

    public List<User> getFreeAgents(int pageNumber, int pageSize) {
        List<User> users = userDao.getFreeAgents();
        return get(users,pageNumber,pageSize);
    }

    public List<User> getClients(int pageNumber, int pageSize){
        List<User> users = userDao.getClients();
        return get(users,pageNumber,pageSize);
    }

    public int countFreeAgents() {
        return userDao.getFreeAgents().size();
    }

    public int countFreeClients() {
        return userDao.getFreeClients().size();
    }

    public List<User> getFreeClients(int pageNumber, int pageSize){
        List<User> users = userDao.getFreeClients();
        return get(users,pageNumber,pageSize);
    }

    private List<User> get(List<User> users, int num, int size) {
        List<User> responsePage = new ArrayList<>();
        for (int i = num * size; i < min(num * size + size, users.size()); i++) {
            responsePage.add(users.get(i));
        }
        return responsePage;
    }

    public User getById(String id){
        return userDao.getById(id);
    }

    public boolean removeFree(User user){
        return userDao.removeFree(user);
    }

    public User removeAgent(){
        return userDao.removeAgent();
    }

    public User removeClient(){
        return userDao.removeClient();
    }

}
