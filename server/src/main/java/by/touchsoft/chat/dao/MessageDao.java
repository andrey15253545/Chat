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

    private final Map<String, List<Message>> messageStorage = new HashMap<>();
    private final Map<String, List<Message>> newMessages = new HashMap<>();
    private final Map<String, ResponseDispatcher> responseMap = new HashMap<>();

    public String add(String id, Message message){
        messageStorage.computeIfAbsent(id, k -> new ArrayList<>());
        messageStorage.get(id).add(message);
        return "message added";
    }

    public void addNew(String id, Message message){
        newMessages.computeIfAbsent(id, k -> new ArrayList<>());
        newMessages.get(id).add(message);
    }

    public void setResponse(String id, ResponseDispatcher dispatcher ){
        responseMap.put(id, dispatcher);
    }

    public ResponseDispatcher getResponse(String id){
        return responseMap.get(id);
    }

    public List<Message> getNew(String id) {
        return newMessages.remove(id);
    }

    public List<Message> getAll(String id) {
        return messageStorage.get(id);
    }

}
