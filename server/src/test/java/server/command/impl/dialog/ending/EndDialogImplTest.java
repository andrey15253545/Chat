package server.command.impl.dialog.ending;

import org.junit.Before;
import org.junit.Test;
import server.response.ResponseDispatcher;
import server.user.User;
import server.room.Room;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class EndDialogImplTest {

    private EndDialogImpl endDialogAgentImpl = new EndDialogImpl();
    private User client;
    private User agent;

    @Before
    public void setUp() throws Exception {
        agent = new User(mock(ResponseDispatcher.class));
        client = new User(mock(ResponseDispatcher.class));
        client.setRoom(new Room(agent));
        agent.setRoom(new Room(client));
    }

    @Test
    public void executeRoomIsNullTest() throws Exception {
        endDialogAgentImpl.execute(agent);
        assertNull(client.getRoom());
        assertNull(agent.getRoom());
    }

}
