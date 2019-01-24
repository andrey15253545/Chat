package server.command.impl.dialog.starting;

import org.junit.Before;
import org.junit.Test;
import server.response.impl.ConsoleResponseImpl;
import server.response.ResponseDispatcher;
import server.user.User;
import server.waitingUser.WaitingUser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class StartDialogAgentImplTest {

    private StartDialogAgentImpl startDialogAgent = new StartDialogAgentImpl();
    private User user;
    private static final WaitingUser WAITING_USER = WaitingUser.AGENT;

    @Before
    public void setUp() throws Exception {
        user = new User(mock(ConsoleResponseImpl.class));
        WAITING_USER.clear();
    }

    @Test
    public void executeTestWait() throws Exception {
        String actualResponse = startDialogAgent.execute(user);
        assertEquals(actualResponse,"please, wait");
    }

    @Test
    public void executeConnectTest() throws Exception {
        User companion = new User(mock(ResponseDispatcher.class));
        WAITING_USER.add(companion);
        String actualResponse = startDialogAgent.execute(user);
        assertEquals(actualResponse, "dialog created");
    }

}