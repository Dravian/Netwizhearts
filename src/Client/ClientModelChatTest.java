package Client;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import Server.GameServerRepresentation;
import test.TestMessageListenerThread;
import test.TestObserver;
import ComObjects.ComChatMessage;
import ComObjects.ComInitLobby;

public class ClientModelChatTest {

	ComChatMessage testMessage;

	ClientModel testModel;

	TestObserver testObserver;

	TestMessageListenerThread testNetIO;

	String testText;

	@Before
    public void setUp() throws Exception {
		testMessage = new ComChatMessage("Hello Test!");
		testNetIO = new TestMessageListenerThread();
		testObserver = new TestObserver();
		testModel = new ClientModel((MessageListenerThread) testNetIO);
		testNetIO.setModel(testModel);
		testModel.addObserver(testObserver);
		testModel.createConnection("TestPlayer1", "localhost");
		List<String> players = new LinkedList<String>();
		players.add("Player1");
		Set<GameServerRepresentation> games =
				new HashSet<GameServerRepresentation>();
		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
    }
 
    @After
    public void tearDown() throws Exception {
    	testNetIO = null;
    	testMessage = null;
    	testModel = null;
    	testObserver = null;
    }

	@Test
	public void testSendChatMessage() {
		String inputText = "Hello Test!";
		testModel.sendChatMessage(inputText);
		testText = ((ComChatMessage)
				testNetIO.getModelInput().get(0)).getChatMessage();
		assertTrue("Vergleich der gesendeten Chatnachrichten",
				testText.contains(inputText));
	}

	@Test
	public void testReceiveChatMessage() {
		testNetIO.injectComObject(testMessage);
		assertTrue("Vergleich der empfangenen Chatnachrichten",
		   testObserver.getChatMessage().equals(testMessage.getChatMessage()));
	}
}
