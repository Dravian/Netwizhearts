package Client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockMessageListenerThread;
import test.MockObserver;
import ComObjects.ComChatMessage;
import ComObjects.ComClientLeave;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComKickPlayerRequest;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;
import Ruleset.RulesetType;
import Server.GameServerRepresentation;

public class ClientInGameLobbyTest {

	ClientModel testModel;

	MockObserver testObserver;

	MockMessageListenerThread testNetIO;

	String testText;

	List<String> players;

	@Before
	public void setUp() throws Exception {
		testNetIO = new MockMessageListenerThread();
		testObserver = new MockObserver();
		testModel = new ClientModel((MessageListenerThread) testNetIO);
		testNetIO.setModel(testModel);
		testModel.addObserver(testObserver);
		testModel.createConnection("TestPlayer1", "localhost");
		players = new LinkedList<String>();
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
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
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
		testText = ((ComChatMessage)
				testNetIO.getModelInput().get(0)).getChatMessage();
		assertTrue("Vergleich der gesendeten Chatnachrichten",
				testText.contains(inputText));
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

	@Test (expected=IllegalArgumentException.class)
	public void playerlistUpdateArgumentNullTest() {
		testModel.receiveMessage((ComUpdatePlayerlist) null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void addPlayerToListTwiceTest() {
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Hans", false);
		testNetIO.injectComObject(updatePlayerList);
		testNetIO.injectComObject(updatePlayerList);
	}

	@Test (expected=IllegalArgumentException.class)
	public void removePlayerFromListTwiceTest() {
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Hans", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList =
				new ComUpdatePlayerlist("Hans", true);
		testNetIO.injectComObject(updatePlayerList);
		testNetIO.injectComObject(updatePlayerList);
	}

	@Test (expected=IllegalArgumentException.class)
	public void corruptPlayerListUpdateTest1() {
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist(null, false);
		testNetIO.injectComObject(updatePlayerList);
	}

	@Test (expected=IllegalArgumentException.class)
	public void corruptPlayerListUpdateTest2() {
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist(null, false);
		testNetIO.injectComObject(updatePlayerList);
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

		testModel.kickPlayer("Hans");
		ComKickPlayerRequest request =
				(ComKickPlayerRequest) testNetIO.getModelInput().remove(0);
		assertEquals("Hans im Request", "Hans", request.getPlayerName());
		
		ComUpdatePlayerlist gameListUpdate = new ComUpdatePlayerlist("Hans",
				true);
		testNetIO.injectComObject(gameListUpdate);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertFalse("Hans nichtmehr in Liste",
				testModel.getPlayerlist().contains("Hans"));
	}

	@Test (expected=IllegalArgumentException.class)
	public void kickPlayerArgumentNullTest() {
		testModel.kickPlayer(null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void kickPlayerArgumentEmptyTest() {
		testModel.kickPlayer("");
	}

	@Test
	public void startGameWizardTest() {
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Player2 in Liste",
				testModel.getPlayerlist().contains("Player2"));

		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Player3 in Liste",
				testModel.getPlayerlist().contains("Player3"));

		testModel.startGame();
		ComStartGame start = (ComStartGame)
				testNetIO.getModelInput().remove(0);
		assertEquals("StartGameNachricht", ComStartGame.class,
				start.getClass());

		testNetIO.injectComObject(new ComStartGame());
		assertEquals("wechsel zu Spielfenster", ViewNotification.gameStarted,
				testObserver.getNotification().remove(0));
	}

	@Test (expected=IllegalArgumentException.class)
	public void startGameReceiveMessageArgumentNullTest() {
		testModel.receiveMessage((ComStartGame) null);
	}

	@Test
	public void startGameHeartsTest() {
		testNetIO = new MockMessageListenerThread();
		testObserver = new MockObserver();
		testModel = new ClientModel((MessageListenerThread) testNetIO);
		testNetIO.setModel(testModel);
		testModel.addObserver(testObserver);
		testModel.createConnection("TestPlayer1", "localhost");
		players = new LinkedList<String>();
		players.add("Player2");
		Set<GameServerRepresentation> games =
				new HashSet<GameServerRepresentation>();
		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);
		testModel.hostGame("My <3", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("TestPlayer1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();

		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Player2 in Liste",
				testModel.getPlayerlist().contains("Player2"));

		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Player3 in Liste",
				testModel.getPlayerlist().contains("Player3"));

		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Player4 in Liste",
				testModel.getPlayerlist().contains("Player4"));

		testModel.startGame();
		ComStartGame start = (ComStartGame)
				testNetIO.getModelInput().remove(0);
		assertEquals("StartGameNachricht", ComStartGame.class,
				start.getClass());

		testNetIO.injectComObject(new ComStartGame());
		assertEquals("wechsel zu Spielfenster", ViewNotification.gameStarted,
				testObserver.getNotification().remove(0));
	}

	@Test
	public void returnToLobbyTest() {
		testModel.returnToLobby();
		assertEquals("ClientLeave", ComClientLeave.class,
				testNetIO.getModelInput().remove(0).getClass());
	}

	@Test (expected=IllegalArgumentException.class)
	public void receiveComStartGameArgumentNullTest() {
		testModel.receiveMessage((ComStartGame) null);
	}
}
