package by.touchsoft.chat.services;

import by.touchsoft.chat.dao.UserDao;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(User user) {
        userDao.addUser(user);
    }

    public User addClient(User user) {
        userDao.addClient(user);
        return user;
    }

    public void addFree(User user){
        userDao.addFree(user);
    }

    public boolean isFree(User user){
        return userDao.isFree(user);
    }

    public boolean removeFree(User user) {
        return userDao.removeFree(user);
    }

    public User addLogged(User user) {
        return user.getRole()== Role.AGENT ? addAgent(user) : addClient(user);
    }

    public User addAgent(User user) {
        userDao.addAgent(user);
        return user;
    }

    public User removeAgent() {
        return userDao.removeAgent();
    }

    public User removeClient() {
        return userDao.removeClient();
    }

    public List<User> get(Role role, int pageNumber, int pageSize){
        List<User> users = userDao.get(role);
        return get(users, pageNumber, pageSize);
    }

    public List<User> getFree(Role role, int pageNumber, int pageSize) {
        List<User> users = userDao.getFree(role);
        return get(users,pageNumber,pageSize);
    }

    public int countFree(Role role) {
        return userDao.getFree(role).size();
    }

    public boolean delete(User user ) {
        return userDao.delete(user);
    }

    public User getById(String id){
        return userDao.getById(id);
    }

    private List<User> get(List<User> users, int num, int size) {
        List<User> responsePage = new ArrayList<>();
        for (int i = max(0,num * size); i < min(num * size + size, users.size()); i++) {
            responsePage.add(users.get(i));
        }
        return responsePage;
    }
}
