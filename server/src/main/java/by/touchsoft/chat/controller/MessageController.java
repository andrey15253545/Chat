package by.touchsoft.chat.controller;


import by.touchsoft.chat.dto.MessageDTO;
import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.services.ChatService;
import by.touchsoft.chat.services.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-1.0-SNAPSHOT/message")
@Api(value = "MessageControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private final ChatService chatService;
    private MessageService messageService;

    @Autowired
    public MessageController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> sendMessage(
            @PathVariable("id") String id,
            @RequestBody MessageDTO messageDTO) {
        String response = chatService.sendMessage(id, messageDTO.getMessage());
        HttpStatus status = response.matches(".+ : message send$")
                ? HttpStatus.OK
                : HttpStatus.NOT_IMPLEMENTED;
        return new ResponseEntity<>(new MessageDTO(response), status);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getAllMessage(
            @PathVariable("id") String id) {
        List<Message> response = messageService.getAll(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //TODO : пагинация

    @RequestMapping(value = "/{id}/new", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getNewMessage(
            @PathVariable("id") String id) {
        List<Message> resp = messageService.get(id);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    //TODO : пагинация

}
