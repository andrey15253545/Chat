import server.ConsoleServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConsoleServer server = new ConsoleServer();
        server.start();
    }
}
