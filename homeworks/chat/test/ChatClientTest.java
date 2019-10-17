import com.company.ChatClient;
import com.company.ChatServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class ChatClientTest {
    private static final int MAX_USERS = 10;
    ChatServer chatServer = new ChatServer();

    List<ChatClient> chatClients = new LinkedList<>();

    @BeforeEach
    void startServer() {
        Assertions.assertDoesNotThrow(() -> new Thread(() -> {
            try {
                //chatServer = new ChatServer();
                chatServer.start(8888);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }

    @AfterEach
    void stopServer() {
        Assertions.assertDoesNotThrow(() -> {
            chatServer.stop();
            chatClients.clear();
        });
    }

    @Test
    void testConnect() {
        Assertions.assertDoesNotThrow(() -> {
            ChatClient chatClient = new ChatClient();
            chatClient.startConnection("localhost", 8888);
            chatClients.add(chatClient);

        });
    }

    @Test
    void testBroadCastMessage() {
        testConnect();
        testConnect();
        testConnect();

        Assertions.assertDoesNotThrow(() -> {

            //clear connecting messages
            chatClients.get(2).readLine();

            chatClients.get(0).sendMessage("all Hello");

//            clear connecting messages
            chatClients.get(1).readLine();
            chatClients.get(1).readLine();

            Assertions.assertEquals("[user1] (all) Hello", chatClients.get(2).readLine());
            Assertions.assertEquals("[user1] (all) Hello", chatClients.get(1).readLine());
        });

        testDisconnect();
        testDisconnect();
        testDisconnect();

    }

    @Test
    void testConnectMaxUsers() {
        for (int i = 0; i < MAX_USERS; i++) {
            testConnect();
        }
        assertTimeout(Duration.ofMillis(1000), this::testConnect);
    }

    @Test
    void testGetInfo(){
        testConnect();
        testConnect();

        Assertions.assertDoesNotThrow(() -> {

            chatClients.get(0).readLine();
            chatClients.get(0).sendMessage("info");


//            System.out.println(chatClients.get(0).readLine());

            Assertions.assertEquals("users: [user1]", chatClients.get(0).readLine());

        });

        testDisconnect();
        testDisconnect();

    }


    @Test
    void testPrivateMessage() {
        testConnect();
        testConnect();
        testConnect();

        Assertions.assertDoesNotThrow(() -> {
            chatClients.get(2).readLine(); // clear connected message
            chatClients.get(0).sendMessage("private user3 hello");
            Assertions.assertEquals("[user1] (private) hello", chatClients.get(2).readLine());
        });

        testDisconnect();
        testDisconnect();
        testDisconnect();
    }

    @Test
    void testDisconnect() {
        testConnect();
        Assertions.assertDoesNotThrow(() -> {
            chatClients.get(0).stopConnection();
            chatClients.remove(chatClients.size() - 1);

        });
    }

    @Test
    void testChangeName() {
        testConnect();
        testConnect();

        Assertions.assertDoesNotThrow(() -> {
            chatClients.get(0).sendMessage("name anon");
            chatClients.get(0).readLine();
            chatClients.get(0).sendMessage("info");
            Assertions.assertTrue(chatClients.get(0).readLine().contains("anon"));

            chatClients.get(0).sendMessage("name nono");
            chatClients.get(0).readLine();
            chatClients.get(0).sendMessage("info");
            Assertions.assertTrue(chatClients.get(0).readLine().contains("nono"));


            chatClients.get(1).sendMessage("name nono");
            chatClients.get(1).readLine();
            chatClients.get(1).sendMessage("info");
            Assertions.assertTrue(chatClients.get(1).readLine().contains("user2"));
        });

        testDisconnect();
        testDisconnect();
    }
}
