package server.command.impl.login;

import org.junit.Before;
import org.junit.Test;
import server.response.impl.ConsoleResponseImpl;
import server.user.Role;
import server.user.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class AddClientImplTest {

    private User user;
    private LoginClientImpl addClient = new LoginClientImpl();;

    @Before
    public void setUp() throws Exception {
        user = new User(mock(ConsoleResponseImpl.class));
    }

    @Test
    public void executeTest() throws Exception {
        String expected = "You logged as a client";
        String actual = addClient.execute(user);
        assertEquals(expected, actual);
    }

    @Test
    public void executeRoleTest() {
        Role expected = Role.CLIENT;
        addClient.execute(user);
        Role actual =user.getRole();
        assertEquals(expected,actual);
    }
}