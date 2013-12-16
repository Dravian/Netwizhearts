package Client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestMessageListenerThread;
import test.TestObserver;
import ComObjects.ComChatMessage;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComUpdatePlayerlist;
import Ruleset.RulesetType;
import Server.GameServerRepresentation;

public class ClientInGameLobbyTest {

	ClientModel testModel;

	TestObserver testObserver;

	TestMessageListenerThread testNetIO;

	String testText;

	@Before
	public void setUp() throws Exception {
		testNetIO = new TestMessageListenerThread();
		testObserver = new TestObserver();
		testModel = new ClientModel((MessageListenerThread) testNetIO);
		testNetIO.setModel(testModel);
		testModel.addObserver(testObserver);
		testModel.createConnection("TestPlayer1", "localhost");
		List<String> players = new LinkedList<String>();
		players.add("Player2");
		Set<GameServerRepresentation> games =
				new HashSet<GameServerRepresentation>();
		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);
		testModel.hostGame("My <3", false, "", RulesetType.Wizard);
		players = new LinkedList<String>();
		players.add("TestPlayer1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		testNetIO.getModelInput().remove(0);
		testNetIO.getModelInput().remove(0);
		testObserver.getNotification().remove(0);
		testObserver.getNotification().remove(0);
	}

	@After
	public void tearDown() throws Exception {
		testNetIO = null;
    	testModel = null;
    	testObserver = null;
    	testText = null;
	}

	@Test
	public void testSendChatMessage() {
		String inputText = "Hello Test!";
		testModel.sendChatMessage(inputText);
		testText = ((ComChatMessage) testNetIO.getModelInput().get(0)).getChatMessage();
		assertTrue("Vergleich der gesendeten Chatnachrichten", testText.contains(inputText));
	}

	@Test
	public void testReceiveChatMessage() {
		ComChatMessage testMessage = new ComChatMessage("Hello Test!");
		testNetIO.injectComObject(testMessage);
		assertTrue("Vergleich der empfangenen Chatnachrichten", 
				testObserver.getChatMessage().equals(testMessage.getChatMessage()));
	}

	@Test
	public void playerListUpdateTest() {
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Hans", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Hans in Liste",
				testModel.getPlayerlist().contains("Hans"));
	}

	@Test
	public void kickPlayerTest() {
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Hans", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Hans in Liste",
				testModel.getPlayerlist().contains("Hans"));
	}
}
