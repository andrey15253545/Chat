package by.touchsoft.chat.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements CommandLineRunner {

    private final ConsoleServer consoleServer;


    @Autowired
    public MyBean(ConsoleServer consoleServer) {
        this.consoleServer = consoleServer;
    }

    @Override
    public void run(String... args) throws Exception {
        consoleServer.run();
    }
}
