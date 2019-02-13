package by.touchsoft.chat.console;

import by.touchsoft.chat.command.Command;
import by.touchsoft.chat.factory.CommandFactory;
import by.touchsoft.chat.model.User;
import by.touchsoft.chat.response.impl.ConsoleResponseImpl;
import by.touchsoft.chat.services.MessageService;
import by.touchsoft.chat.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Component
@Scope("prototype")
public class ConsoleRequestDispatcher extends Thread {

    private static final String EXIT_URL = "/exit";
    private static final String COMMAND_NOT_FOUND = "Command '%s' not found";
    private final static Logger logger = Logger.getLogger(ConsoleRequestDispatcher.class);
    private Socket socket;

    private final CommandFactory factory;
    private final MessageService messageService;
    private final UserService userService;
    private final ConsoleResponseImpl response;

    @Autowired
    public ConsoleRequestDispatcher(CommandFactory factory,
                                    MessageService messageService,
                                    UserService userService,
                                    ConsoleResponseImpl response) {
        this.factory = factory;
        this.messageService = messageService;
        this.userService = userService;
        this.response = response;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        User user = new User();
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            response.setOut(out);
            messageService.setResponse(user.getId(),response);
            userService.add(user);
            String mess;
            do {
                mess = in.readUTF();
                Command command = factory.getCommand(mess, user.getRole());
                String result = command != null ? command.execute(user, mess) : String.format(COMMAND_NOT_FOUND,mess);
                out.writeUTF(result);
            } while (!mess.equalsIgnoreCase(EXIT_URL));
            socket.close();
        } catch (IOException e) {
            factory.getCommand(EXIT_URL, user.getRole()).execute(user, EXIT_URL);
            logger.warn(e);
        }
    }
}
