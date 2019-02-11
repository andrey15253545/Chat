package by.touchsoft.chat.dao;


import by.touchsoft.chat.model.Chat;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class ChatDao {

    private final List<Chat> list = Collections.synchronizedList(new LinkedList<>());
    private final Map<String, String> map = new HashMap<>();

    public void add(Chat chat) {
        list.add(chat);
        map.put(chat.getUserId(), chat.getCompanionId());
        map.put(chat.getCompanionId(), chat.getUserId());
    }

    public String getCompanionId(String id) {
        return map.getOrDefault(id, null);
    }

    public List<Chat> getAll(){
        return list;
    }

    private void removeById(String id) {
        map.remove(id);
    }

    public void remove(Chat chat) {
        removeById(chat.getCompanionId());
        removeById(chat.getUserId());
        list.remove(chat);
    }

}