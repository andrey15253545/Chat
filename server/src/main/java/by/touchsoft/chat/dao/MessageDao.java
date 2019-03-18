package by.touchsoft.chat.dao;

import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.response.ResponseDispatcher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessageDao {

    private final Map<String, List<Message>> newMessages = new HashMap<>();

    public void addNew(String id, Message message){
        newMessages.computeIfAbsent(id, k -> new ArrayList<>());
        newMessages.get(id).add(message);
    }

    public List<Message> getNew(String id) {
        return newMessages.remove(id);
    }

}
