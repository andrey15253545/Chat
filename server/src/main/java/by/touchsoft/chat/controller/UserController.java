package by.touchsoft.chat.controller;

import by.touchsoft.chat.model.User;
import by.touchsoft.chat.response.impl.RestResponseImpl;
import by.touchsoft.chat.services.MessageService;
import by.touchsoft.chat.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-1.0-SNAPSHOT/users")
@Api(value = "UserControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private static final String DEFAULT_PAGE_SIZE = "10";

    private final UserService userService;
    private final MessageService messageService;
    private final ApplicationContext context;

    @Autowired
    public UserController(UserService userService, MessageService messageService, ApplicationContext context) {
        this.userService = userService;
        this.messageService = messageService;
        this.context = context;
    }


//    Получить детальную информацию об одном указанном агенте
//    Получить детальную информацию об одном указанном клиенте



    @RequestMapping(value = "/agents", method = RequestMethod.GET)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = ResponseEntity.class)})
    public ResponseEntity<List<User>> allAgents(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        List<User> agents = userService.getAgents(pageNumber,pageSize);
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public ResponseEntity<List<User>> allClients(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        List<User> clients = userService.getClients(pageNumber,pageSize);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @RequestMapping(value = "/agents/free", method = RequestMethod.GET)
    public ResponseEntity<List<User>> freeAgents(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        List<User> agents = userService.getFreeAgents(pageNumber,pageSize);
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/free", method = RequestMethod.GET)
    public ResponseEntity<List<User>> freeClients(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        List<User> clients = userService.getFreeClients(pageNumber,pageSize);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @RequestMapping(value = "/agents/free/count", method = RequestMethod.GET)
    public ResponseEntity<Integer> countFreeAgents() {
        int count = userService.countFreeAgents();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/free/count", method = RequestMethod.GET)
    public ResponseEntity<Integer> countWaitingClients() {
        int count = userService.countFreeClients();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> Register(
            @RequestBody User user) {
        userService.addLogged(user);
        RestResponseImpl response = context.getBean(RestResponseImpl.class);
        response.setId(user.getId());
        messageService.setResponse(user.getId(),response);
        return new ResponseEntity<>(user.toString(), HttpStatus.OK);
    }
}