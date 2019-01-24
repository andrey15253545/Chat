package server.command.impl.message;

import org.junit.Before;
import org.junit.Test;
import server.response.ResponseDispatcher;
import server.room.Room;
import server.user.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SendMessageImplTest {

    private static final String MESSAGE_SEND = "message send";

    private User user;
    private SendMessageImpl sendMessage = new SendMessageImpl();

    @Before
    public void setUp() throws Exception {
        user=new User(mock(ResponseDispatcher.class));
        ResponseDispatcher response = mock(ResponseDispatcher.class);
        when(response.sendMessage("user : hi")).thenReturn(MESSAGE_SEND);
        User companion = new User(response);
        user.setRoom(new Room(companion));
    }

//    @Test
//    public void executeSendTest() throws Exception {
//        String actual = sendMessage.execute(user);
//        assertEquals(MESSAGE_SEND, actual);
//    }
}