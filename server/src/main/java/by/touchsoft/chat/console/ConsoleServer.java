package by.touchsoft.chat.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class ConsoleServer extends Thread{

    private static final String ADDRESS = "127.0.0.2";
    private static final int PORT = 6666;
    private final ApplicationContext context;

    @Autowired
    public ConsoleServer(ApplicationContext context) {
        this.context = context;
    }

    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByName(ADDRESS);
            ServerSocket ss = new ServerSocket(PORT,10,inetAddress);
            while (true) {
                Socket socket = ss.accept();
                ConsoleRequestDispatcher requestDispatcher = context.getBean(ConsoleRequestDispatcher.class);
                requestDispatcher.setSocket(socket);
                requestDispatcher.start();
            }
        } catch (IOException e) {
            this.start();
        }
    }
}
