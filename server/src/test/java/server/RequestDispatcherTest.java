package server;

import org.junit.After;
import org.junit.Test;
import server.dispather.ConsoleRequestDispatcher;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class RequestDispatcherTest {

    private boolean isClosed;
    private ConsoleRequestDispatcher requestDispatcher;
    private ByteArrayOutputStream response;
    private int indRequest = 0;
    private static final List<String> REQUEST_ARR = new ArrayList<>();
    static {
        REQUEST_ARR.add("/exit");
    }

//    @Before
//    public void setUp() throws Exception {
//        isClosed = false;
//        response = new ByteArrayOutputStream();
//        requestDispatcher = new RequestDispatcher(new Socket(){
//
//            @Override
//            public InputStream getInputStream() throws IOException {
//                return new ByteArrayInputStream(REQUEST_ARR.get(indRequest++).getBytes("UTF-8"));
//            }
//
//            @Override
//            public OutputStream getOutputStream() throws IOException {
//                return response;
//            }
//
//            @Override
//            public synchronized void close() throws IOException {
//                isClosed = true;
//            }
//        });
//    }

    @After
    public void tearDown() throws Exception {
//        response.close();
    }

    @Test
    public void runTest() throws Exception {
//        requestDispatcher.run();
//        assertEquals("exited",response.toString("UTF-8"));
//        assertTrue(isClosed);
    }

}