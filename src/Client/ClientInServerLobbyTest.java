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

import Ruleset.RulesetType;
import Server.GameServerRepresentation;
import test.TestMessageListenerThread;
import test.TestObserver;
import ComObjects.ComChatMessage;
import ComObjects.ComCreateGameRequest;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComJoinRequest;
import ComObjects.ComLobbyUpdateGamelist;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;

public class ClientInServerLobbyTest {

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

	@Test (expected=IllegalArgumentException.class)
	public void testSendChatMessageNull() {
		testModel.sendChatMessage(null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void testSendChatMessageEmpty() {
		testModel.sendChatMessage("");
	}

	@Test
	public void testReceiveChatMessage() {
		ComChatMessage testMessage = new ComChatMessage("Hello Test!");
		testNetIO.injectComObject(testMessage);
		assertTrue("Vergleich der empfangenen Chatnachrichten", 
		   testObserver.getChatMessage().equals(testMessage.getChatMessage()));
	}

	@Test (expected=IllegalArgumentException.class)
	public void testReceiveChatMessageNull() {
		testModel.receiveMessage((ComChatMessage) null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void testReceiveChatMessageEmpty() {
		testModel.receiveMessage(new ComChatMessage(""));
	}

	@Test
	public void listsUpdateTest() {
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Hans", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Hans in Liste",
				testModel.getPlayerlist().contains("Hans"));

		updatePlayerList = new ComUpdatePlayerlist("Hans", true);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate,
				testObserver.getNotification().remove(0));
		assertFalse("Hans nicht mehr in Liste",
				testModel.getPlayerlist().contains("Hans"));

		GameServerRepresentation game = new GameServerRepresentation("Peter",
				"Mein Spiel", 6, 1, RulesetType.Wizard, false, false);
		ComLobbyUpdateGamelist updateGameList =
				new ComLobbyUpdateGamelist(false, game);
		testNetIO.injectComObject(updateGameList);
		assertEquals("Observer Update", ViewNotification.gameListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Spiel in Liste",
				testModel.getLobbyGamelist().contains(game));

		game = new GameServerRepresentation("Peter",
				"Mein Spiel", 6, 2, RulesetType.Wizard, false, false);
		updateGameList = new ComLobbyUpdateGamelist(false, game);
		testNetIO.injectComObject(updateGameList);
		assertEquals("Observer Update", ViewNotification.gameListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Spiel in Liste Update",
				testModel.getLobbyGamelist().contains(game));

		game = new GameServerRepresentation("Peter", "Mein Spiel",
				6, 2, RulesetType.Wizard, false, false);
		updateGameList = new ComLobbyUpdateGamelist(true, game);
		testNetIO.injectComObject(updateGameList);
		assertEquals("Observer Update", ViewNotification.gameListUpdate,
				testObserver.getNotification().remove(0));
		assertTrue("Spiel nicht mehr in Liste",
				testModel.getLobbyGamelist().isEmpty());
	}

	@Test (expected=IllegalArgumentException.class)
	public void gameListUpdateArgumentNullTest() {
		testModel.receiveMessage((ComLobbyUpdateGamelist) null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void gameListUpdateCorruptedTest() {
		ComLobbyUpdateGamelist updateGameList = 
				new ComLobbyUpdateGamelist(false, null);
		testNetIO.injectComObject(updateGameList);
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

	@Test (expected=IllegalArgumentException.class)
	public void initLobbyArgumentNullTest() {
		testModel.receiveMessage((ComInitLobby) null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void initLobbyPlayerlistNullTest() {
		Set<GameServerRepresentation> games =
				new HashSet<GameServerRepresentation>();
		ComInitLobby testInitLobby = new ComInitLobby(null, games);
		testNetIO.injectComObject(testInitLobby);
	}

	@Test (expected=IllegalArgumentException.class)
	public void initLobbyPlayerlistEmptyTest() {
		List<String> players = new LinkedList<String>();
		Set<GameServerRepresentation> games =
				new HashSet<GameServerRepresentation>();
		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);
	}

	@Test (expected=IllegalArgumentException.class)
	public void initLobbyGamelistNullTest() {
		List<String> players = new LinkedList<String>();
		players.add("Player1");
		ComInitLobby testInitLobby = new ComInitLobby(players, null);
		testNetIO.injectComObject(testInitLobby);
	}

	

	@Test
	public void hostGameTest() {
		ComCreateGameRequest game;
		testModel.hostGame("My <3", false, "", RulesetType.Hearts);
		game = (ComCreateGameRequest) testNetIO.getModelInput().remove(0);
		assertEquals("Spielname", "My <3", game.getGameName());
		assertEquals("Passwort Boolean", false, game.hasPassword());
		assertEquals("Passwort", "", game.getPassword());
		assertEquals("Regelwerk", RulesetType.Hearts, game.getRuleset());

		List<String> players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		assertEquals("ObserverUpdate", ViewNotification.joinGameSuccessful,
				testObserver.getNotification().remove(0));
		assertEquals("Spielerliste", players, testModel.getPlayerlist());
	}

	@Test (expected=IllegalArgumentException.class)
	public void enterGameLobbyArgumentNullTest() {
		testModel.hostGame("My <3", false, "", RulesetType.Hearts);
		testModel.receiveMessage((ComInitGameLobby) null);
	}

	@Test (expected=IllegalStateException.class)
	public void receiveGameLobbyInitWrongStateTest() {
		testModel.receiveMessage((ComInitGameLobby) null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void enterGameLobbyPlayerlistNullTest() {
		testModel.hostGame("My <3", false, "", RulesetType.Hearts);
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(null);
		testNetIO.injectComObject(gameLobbyInit);
	}

	@Test (expected=IllegalArgumentException.class)
	public void enterGameLobbyPlayerlistEmptyTest() {
		testModel.hostGame("My <3", false, "", RulesetType.Hearts);
		List<String> players = new LinkedList<String>();
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
	}

	@Test (expected=IllegalArgumentException.class)
	public void hostGameArgumentNullTest() {
		testModel.hostGame("", true, "password", null);
	}

	@Test
	public void hostGameInconsistentArguments1Test() {
		ComCreateGameRequest game;
		testModel.hostGame("My <3", true, "", RulesetType.Hearts);
		game = (ComCreateGameRequest) testNetIO.getModelInput().remove(0);
		assertEquals("Spielname", "My <3", game.getGameName());
		assertEquals("Passwort Boolean", false, game.hasPassword());
		assertEquals("Passwort", "", game.getPassword());
		assertEquals("Regelwerk", RulesetType.Hearts, game.getRuleset());
	}

	@Test
	public void hostGameInconsistentArguments2Test() {
		ComCreateGameRequest game;
		testModel.hostGame(null, true, null, RulesetType.Hearts);
		game = (ComCreateGameRequest) testNetIO.getModelInput().remove(0);
		assertEquals("Spielname", "", game.getGameName());
		assertEquals("Passwort Boolean", false, game.hasPassword());
		assertEquals("Passwort", "", game.getPassword());
		assertEquals("Regelwerk", RulesetType.Hearts, game.getRuleset());
	}

	@Test
	public void joinGameTest() {
		ComJoinRequest testJoinRequest;
		GameServerRepresentation game = new GameServerRepresentation("Peter",
				"Mein Spiel", 6, 1, RulesetType.Wizard, false, false);
		ComLobbyUpdateGamelist updateGameList =
				new ComLobbyUpdateGamelist(false, game);
		testNetIO.injectComObject(updateGameList);
		testModel.joinGame("Peter", "");
		testJoinRequest = (ComJoinRequest) testNetIO.getModelInput().remove(0);
		assertEquals("Game Master", "Peter",
				testJoinRequest.getGameMasterName());
		assertEquals("Passwort", "", testJoinRequest.getPassword());

		List<String> players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		assertEquals("ObserverUpdate", ViewNotification.joinGameSuccessful,
				testObserver.getNotification().remove(1));
		assertEquals("Spielerliste", players, testModel.getPlayerlist());
	}

	@Test (expected=IllegalArgumentException.class)
	public void joinGameEmptyNameTest() {
		testModel.joinGame("", "password");
	}

	@Test (expected=IllegalArgumentException.class)
	public void joinGameNameNullTest() {
		testModel.joinGame(null, null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionsTest3() {
		testModel.receiveMessage((ComRuleset) null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionsTest4() {
		testModel.receiveMessage((ComStartGame) null);
	}
}
