package server.command.impl.login;

import org.junit.Before;
import org.junit.Test;
import server.response.ResponseDispatcher;
import server.user.Role;
import server.user.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class AddAgentImplTest {

    private User user;
    private LoginAgentImpl addAgent = new LoginAgentImpl();

    @Before
    public void setUp() throws Exception {
        user = new User(mock(ResponseDispatcher.class));
    }

    @Test
    public void executeChangeRoleTest() {
        Role expected = Role.AGENT;
        addAgent.execute(user);
        Role actual = user.getRole();
        assertEquals(expected,actual);
    }

    @Test
    public void executeTestReturn() throws Exception {
        String expected = "you logged as an agent";
        String actual = addAgent.execute(user);
        assertEquals(expected, actual);
    }

}