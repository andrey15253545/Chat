package by.touchsoft.chat.services;

import by.touchsoft.chat.dao.ChatDao;
import by.touchsoft.chat.dao.MessageDao;
import by.touchsoft.chat.dao.UserDao;
import by.touchsoft.chat.model.Chat;
import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.response.ResponseDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    private static final String CONNECTED = "dialog created with %s";
    private static final String ENDED = "%s left the dialog";
    private static final String WAIT = "please, wait";
    private static final String IN_CHAT = "you already have a companion";
    private static final String IN_QUEUE = "you already in queue";
    private static final String LEFT_THE_CHAT = "you are not chatting";
    private static final String LEFT_THE_QUEUE = "you left the queue";
    private static final String MESSAGE_SEND = "message send";
    private static final String MESSAGE_NOT_SEND = "message does't send";
    private static final String MESSAGE = "%s : %s";

    private final ChatDao chatDao;
    private final MessageDao messageDao;
    private final UserDao userDao;

    @Autowired
    public ChatService(ChatDao chatDao, MessageDao messageDao, UserDao userService) {
        this.chatDao = chatDao;
        this.messageDao = messageDao;
        this.userDao = userService;
    }

    // TODO : realise page
    public List<Chat> getAll(int pageNumber, int pageSize){
        return chatDao.getAll();
    }

    public String create(User user){
        if (inChat(user)){
            return IN_CHAT;
        }
        else if (inQueue(user)) {
            return IN_QUEUE;
        }
        return user.getRole()== Role.AGENT ? createForAgent(user) : createForClient(user);
    }

    private String createForClient(User client){
        return createDialog(client, userDao.removeAgent());
    }

    private String createForAgent(User agent) {
        return createDialog(agent, userDao.removeClient());
    }

    private String createDialog(User user, User companion) {
        if (companion==null){
            userDao.addFree(user);
            return WAIT;
        }
        Message message = generateMessage(user, String.format(CONNECTED, user.getName()));
        if (send(companion.getId(), message)) {
            Chat chat = new Chat(user.getId(), companion.getId());
            chatDao.add(chat);
            return String.format(CONNECTED, companion.getName());
        }
        else {
            return create(user);
        }
    }

    public String endDialog(User user) {
        if (user==null) return "error";
        String companionId = chatDao.getCompanionId(user.getId());
        if (companionId==null) {
            return userDao.removeFree(user) ? LEFT_THE_QUEUE : LEFT_THE_CHAT;
        }
        chatDao.remove(new Chat(user.getId(), companionId));
        Message message = generateMessage(user, String.format(ENDED, user.getName()));
        send(companionId, message);
        return String.format(ENDED, "you");
    }

    /**
     *
     * @param id - index of the user to whom the message is sent
     * @param message {@link Message} - message to be send
     * @return true if message send and false if message does't send
     */
    private boolean send(String id, Message message) {
        try {
            ResponseDispatcher response = messageDao.getResponse(id);
            response.sendMessage(message);
            messageDao.add(id,message);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String sendMessage(String id, String text) {
        String companionId = chatDao.getCompanionId(id);
        if (companionId == null) {
            return LEFT_THE_CHAT;
        }
        User user = userDao.getById(id);
        Message message = generateMessage(user, text);
        String result = send(companionId, message)
                ? MESSAGE_SEND
                : MESSAGE_NOT_SEND;
        return String.format(MESSAGE, message, result);
    }

    private Message generateMessage(User user, String text) {
        return new Message(user, new Date(), text);
    }

    private boolean inChat(User user){
        return chatDao.getCompanionId(user.getId()) != null;
    }

    private boolean inQueue(User user){
        return userDao.isFree(user);
    }
}