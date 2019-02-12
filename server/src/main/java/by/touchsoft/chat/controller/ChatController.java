package by.touchsoft.chat.controller;

import by.touchsoft.chat.model.Chat;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.services.MessageService;
import by.touchsoft.chat.services.UserService;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import by.touchsoft.chat.services.ChatService;

import java.util.List;

@Controller
@RequestMapping("/chat-1.0-SNAPSHOT/chat")
public class ChatController {

    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final Logger logger = Logger.getLogger(ChatController.class);
    private final ChatService chatService;
    private final UserService userService;

//    Получить детальную информацию об одном указанном чате
//    Получить текущие открытые чаты
//    Покинуть чат

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String chat(){
        return "index";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Chat>> allChats(
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        List<Chat> chats = chatService.getAll(pageNumber,pageSize);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> startDialog(
            @PathVariable(value = "id") String id) {
        User user = userService.getById(id);
        if (user!=null){
            String resp = chatService.create(user);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> endDialog(
            @PathVariable(value = "id") String id) {
        User user = userService.getById(id);
        if (user!=null){
            String resp = chatService.endDialog(user);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}