package server.command.impl.logout;

import org.junit.Before;
import org.junit.Test;
import server.response.impl.ConsoleResponseImpl;
import server.user.Role;
import server.user.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class LogoutImplTest {

    private User user;
    private LogoutImpl logout = new LogoutImpl();
    private static final String LOGOUT = "Logged logout";

    @Before
    public void setUp() throws Exception {
        ConsoleResponseImpl consoleResponse = mock(ConsoleResponseImpl.class);
        user = new User(consoleResponse);
    }

    @Test
    public void executeRoleTest() throws Exception {
        Role expected = Role.GUEST;
        logout.execute(user);
        Role actual = user.getRole();
        assertEquals(expected, actual);
    }

    @Test
    public void executeTest() {
        String actual = LOGOUT;
        String expected = logout.execute(user);
        assertEquals(expected, actual);
    }



}