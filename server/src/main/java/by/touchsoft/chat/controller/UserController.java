package by.touchsoft.chat.controller;

import by.touchsoft.chat.dto.UserDTO;
import by.touchsoft.chat.model.Role;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.response.impl.RestResponseImpl;
import by.touchsoft.chat.services.ChatService;
import by.touchsoft.chat.services.MessageService;
import by.touchsoft.chat.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chat-1.0-SNAPSHOT/users")
@Api(value = "UserControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_PAGE_NUMBER = "0";

    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final ApplicationContext context;

//    Получить детальную информацию об одном указанном агенте
//    Получить детальную информацию об одном указанном клиенте

    @Autowired
    public UserController(UserService userService, ChatService chatService, MessageService messageService, ApplicationContext context) {
        this.userService = userService;
        this.chatService = chatService;
        this.messageService = messageService;
        this.context = context;
    }
    @GetMapping(value = "/{role}")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<List<User>> allUsersByRole(
            @PathVariable(value = "role") Role role,
            @RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        List<User> agents = userService.get(role, pageNumber, pageSize);
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @RequestMapping(value = "/{role}/free", method = RequestMethod.GET)
    public ResponseEntity<List<User>> freeUsersByRole(
            @PathVariable(value = "role") Role role,
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        List<User> agents = userService.getFree(role, pageNumber,pageSize);
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @RequestMapping(value = "/{role}/free/count", method = RequestMethod.GET)
    public ResponseEntity<Integer> countFreeByRole(
            @PathVariable(value = "role") Role role
    ) {
        int count = userService.countFree(role);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable(value = "id") String id) {
        User user = userService.getById(id);
        if (user!=null){
            chatService.endDialog(user);
            userService.delete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(
            @RequestBody @Valid UserDTO userDTO) {
        User user = new User(userDTO.getName(),userDTO.getRole());
        userService.addLogged(user);
        RestResponseImpl response = context.getBean(RestResponseImpl.class);
        response.setId(user.getId());
        messageService.setResponse(user.getId(),response);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}