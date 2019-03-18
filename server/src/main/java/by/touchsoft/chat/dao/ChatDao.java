package by.touchsoft.chat.dao;


import by.touchsoft.chat.model.Chat;
import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.response.ResponseDispatcher;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class ChatDao {

    private final List<Chat> all = Collections.synchronizedList(new LinkedList<>());
    private final List<Chat> open = Collections.synchronizedList(new LinkedList<>());
    private final Map<String, String> map = new HashMap<>();
    private final Map<String, ResponseDispatcher> responseMap = new HashMap<>();
    private final Map<String, List<Message>> chatStories = new HashMap<>();

    public void setResponse(String id, ResponseDispatcher dispatcher ){
        responseMap.put(id, dispatcher);
    }

    public ResponseDispatcher getResponse(String id){
        return responseMap.get(id);
    }

    public void add(Chat chat) {
        open.add(chat);
        map.put(chat.getUserId(), chat.getCompanionId());
        map.put(chat.getCompanionId(), chat.getUserId());
        all.add(chat);
        chatStories.put(chat.getChatId(),new ArrayList<>());
    }


    public void addMessage(String id, Message message){
        String companionId = getCompanionId(id);
        int i = open.indexOf(new Chat(id, companionId));
        if (i!=-1) {
            Chat chat = open.get(i);
            chatStories.get(chat.getChatId()).add(message);
        }
    }

    public List<Message> getByChatId(String id) {
        return chatStories.get(id);
    }

    public List<Message> getById(String id){
        String companionId = getCompanionId(id);
        int i = open.indexOf(new Chat(id, companionId));
        if (i!=-1){
            Chat chat = open.get(i);
            return chatStories.get(chat.getChatId());
        }
        return null;
    }

    public String getCompanionId(String id) {
        return map.getOrDefault(id, null);
    }

    public List<Chat> getOpen(){
        return open;
    }

    public void remove(Chat chat) {
        map.remove(chat.getCompanionId());
        map.remove(chat.getUserId());
        open.remove(chat);
    }

}