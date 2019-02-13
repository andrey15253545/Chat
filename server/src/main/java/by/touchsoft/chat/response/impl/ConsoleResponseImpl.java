package by.touchsoft.chat.response.impl;

import by.touchsoft.chat.model.Message;
import by.touchsoft.chat.response.ResponseDispatcher;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;

@Component
@Scope("prototype")
@Setter
public class ConsoleResponseImpl implements ResponseDispatcher {

    private DataOutputStream out;

    @Override
    public void sendMessage(Message message) throws IOException{
        out.writeUTF(message.toString());
    }
}
