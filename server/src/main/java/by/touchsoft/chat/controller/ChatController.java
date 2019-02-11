package by.touchsoft.chat.controller;

import by.touchsoft.chat.dto.MessageDTO;
import by.touchsoft.chat.model.Chat;
import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.services.MessageService;
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

    private final ChatService chatService;
    private final MessageService messageService;

//    Получить детальную информацию об одном указанном чате
//    Получить текущие открытые чаты
//    Покинуть чат

    @Autowired
    public ChatController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String chat(){
        return "index";
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMessage(
            @PathVariable("id") String id,
            @RequestBody MessageDTO dto) {
        String response = chatService.sendMessage(id, dto.getMessage());
        HttpStatus status = response.matches(".+ : message send$")
                ? HttpStatus.OK
                : HttpStatus.NOT_IMPLEMENTED;
        return new ResponseEntity<>(response, status);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Chat>> allChats(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        List<Chat> chats = chatService.getAll(pageNumber,pageSize);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/new", method = RequestMethod.GET)
    public ResponseEntity<String> getNewMessage(
            @PathVariable("id") String id) {
        String resp = messageService.get(id);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    //TODO : пагинация

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getAllMessage(
            @PathVariable("id") String id) {
        List<Message> response = messageService.getAll(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //TODO : пагинация

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ResponseEntity<String> startDialog(
            @RequestParam(value = "id") String id) {
        String resp = chatService.create(id);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "/end", method = RequestMethod.GET)
    public ResponseEntity<String> endDialog(
            @RequestParam(value = "id") String id) {
        String resp = chatService.endDialog(id);
        return new ResponseEntity<>((resp), HttpStatus.OK);
    }


}