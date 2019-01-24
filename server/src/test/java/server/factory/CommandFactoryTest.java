package server.factory;

import org.junit.Test;
import server.command.Command;
import server.command.impl.login.LoginAgentImpl;
import server.user.Role;

import static org.junit.Assert.assertEquals;

public class CommandFactoryTest {

    private static final CommandFactory factory = CommandFactory.INSTANCE;

    @Test
    public void getCommand() throws Exception {
        Command expected = new LoginAgentImpl();
        Command actual = factory.getCommand("/a asd", Role.GUEST);
        assertEquals(actual.getClass(), expected.getClass());
    }

}