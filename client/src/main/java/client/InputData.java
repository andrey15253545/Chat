package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

class InputData extends Thread {

    private static final String EXIT_INPUT_LINE = "exited";
    private static final String MESSAGE_SEND = "message send";
    private static final String ERROR_MESSAGE = "error";
    private Socket socket;

    InputData(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream())) {
            String read;
            do {
                read = in.readUTF();
                if (!read.equals(MESSAGE_SEND)) {
                    System.out.println(read);
                }
            }while (!read.equalsIgnoreCase(EXIT_INPUT_LINE));
        } catch (IOException e) {
            System.err.print(e);
            e.printStackTrace();
            System.err.print(ERROR_MESSAGE);
        }
    }
}