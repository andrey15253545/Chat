package client;

import java.net.*;
import java.io.*;

public class Client {

    private static final String EXIT_URL = "/exit";
    private static final String ADDRESS = "127.0.0.2";
    private static final int SERVER_PORT = 6666;
    private static final String ERROR_MESSAGE = "problems on the server";

    public static void main(String[] ar) {


        try {
            InetAddress ipAddress = InetAddress.getByName(ADDRESS);
            Socket socket = new Socket(ipAddress, SERVER_PORT);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            InputData inputData = new InputData(socket);
            inputData.start();

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line;
            do {
                line = keyboard.readLine();
                out.writeUTF(line);
                out.flush();
            } while (!line.equalsIgnoreCase(EXIT_URL));
        } catch (IOException e) {
            System.err.print(ERROR_MESSAGE);
        }
    }
}

