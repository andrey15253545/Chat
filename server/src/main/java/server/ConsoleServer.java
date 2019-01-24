package server;

import server.dispather.ConsoleRequestDispatcher;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class ConsoleServer extends Thread {

//    private static final Logger log = Logger.ge(String.valueOf(ConsoleServer.class));
    private static final String ADDRESS = "127.0.0.2";
    private static final int PORT = 6666;



    @Override
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByName(ADDRESS);
            ServerSocket ss = new ServerSocket(PORT,10,inetAddress);
            while (true) {
                Socket socket = ss.accept();
                ConsoleRequestDispatcher requestDispatcher = new ConsoleRequestDispatcher();
                requestDispatcher.setSocket(socket);
                requestDispatcher.start();
            }
        } catch (IOException e) {
//            log.error(e);
        }
    }
}
