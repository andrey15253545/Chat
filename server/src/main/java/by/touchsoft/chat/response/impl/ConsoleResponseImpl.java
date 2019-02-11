package by.touchsoft.chat.response.impl;

import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.response.ResponseDispatcher;

import java.io.DataOutputStream;
import java.io.IOException;


public class ConsoleResponseImpl implements ResponseDispatcher{

    private final DataOutputStream out;

    public ConsoleResponseImpl(DataOutputStream out) {
        this.out = out;
    }

    @Override
    public boolean sendMessage(Message message) {
        try {
            out.writeUTF(message.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
