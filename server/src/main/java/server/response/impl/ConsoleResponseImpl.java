package server.response.impl;

import org.apache.log4j.Logger;
import server.response.ResponseDispatcher;

import java.io.DataOutputStream;
import java.io.IOException;

public class ConsoleResponseImpl implements ResponseDispatcher {

    private DataOutputStream out;
    private static final Logger logger = Logger.getLogger(ConsoleResponseImpl.class);

    public ConsoleResponseImpl(DataOutputStream out){
        this.out = out;
    }

    @Override
    public String sendMessage(String message) {
        try {
            out.writeUTF(message);
            return "message send";
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn(e);
            return "message doesn't send";
        }
    }
}
