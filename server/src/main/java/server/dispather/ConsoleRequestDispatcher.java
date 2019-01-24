package server.dispather;

import org.apache.log4j.Logger;
import server.command.Command;
import server.factory.CommandFactory;
import server.response.impl.ConsoleResponseImpl;
import server.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConsoleRequestDispatcher extends Thread {


    private static final String EXIT_URL = "/exit";
    private static final String COMMAND_NOT_FOUND = "Command '%s' not found";
    private final static Logger logger = Logger.getLogger(ConsoleRequestDispatcher.class);
    private Socket socket;

    private CommandFactory factory = CommandFactory.INSTANCE;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        User user = null;
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            user = new User(new ConsoleResponseImpl(out));
            String readLine;
            do {
                readLine = in.readUTF();
                System.out.print(socket.isClosed());
                user.setLastMessage(readLine);
                Command command = factory.getCommand(readLine, user.getRole());
                String result = command != null ? command.execute(user) : String.format(COMMAND_NOT_FOUND,readLine);
                user.getResponse().sendMessage(result);
            } while (!readLine.equalsIgnoreCase(EXIT_URL));
            socket.close();
        } catch (IOException e) {
            if (user != null)
                factory.getCommand(EXIT_URL, user.getRole()).execute(user);
            logger.warn(e);
        }
    }
}
