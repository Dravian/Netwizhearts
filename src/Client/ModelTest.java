package Client;

import static org.junit.Assert.*;

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
import ComObjects.ComCreateGameRequest;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComJoinRequest;
import ComObjects.ComLobbyUpdateGamelist;
import ComObjects.ComLoginRequest;
import ComObjects.ComUpdatePlayerlist;

public class ModelTest {
	
	ClientModel testModel;
	
	TestObserver testObserver;
	
	TestMessageListenerThread testNetIO;
	
	@Before
	public void setUp() throws Exception {
		testNetIO = new TestMessageListenerThread();
		testObserver = new TestObserver();
		testModel = new ClientModel((MessageListenerThread) testNetIO);
		testNetIO.setModel(testModel);
		testModel.addObserver(testObserver);
	}

	@After
	public void tearDown() throws Exception {
		testNetIO = null;
    	testModel = null;
    	testObserver = null;
	}
	
	@Test
	public void loginTest() {
		ComLoginRequest testLoginRequest;
		testModel.createConnection("Player2", "localhost");
		testLoginRequest = (ComLoginRequest) testNetIO.getModelInput().remove(0);
		assertEquals("Versende Login Request","Player2",testLoginRequest.getPlayerName());
	
		List<String> players = new LinkedList<String>();
		players.add("Player2");
		Set<GameServerRepresentation> games = new HashSet<GameServerRepresentation>();
		GameServerRepresentation game = new GameServerRepresentation("Peter", "Mein Spiel", 6, 1, RulesetType.Wizard, false, false);
		games.add(game);
	
		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);
		
		assertEquals("Observer Update", ViewNotification.windowChangeForced, testObserver.getNotification().remove(0));
		assertEquals("Spielerliste", players.get(0), testModel.getPlayerlist().get(0));
		assertEquals("Spielliste", games.contains(game), testModel.getLobbyGamelist().contains(game));
	}
	
	@Test
	public void listsUpdateTest() {
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist("Hans", false);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate, testObserver.getNotification().remove(0));
		assertTrue("Hans in Liste", testModel.getPlayerlist().contains("Hans"));
		
		updatePlayerList = new ComUpdatePlayerlist("Hans", true);
		testNetIO.injectComObject(updatePlayerList);
		assertEquals("Observer Update", ViewNotification.playerListUpdate, testObserver.getNotification().remove(0));
		assertFalse("Hans nicht mehr in Liste", testModel.getPlayerlist().contains("Hans"));
		
		GameServerRepresentation game = new GameServerRepresentation("Peter", "Mein Spiel", 6, 1, RulesetType.Wizard, false, false);
		ComLobbyUpdateGamelist updateGameList = new ComLobbyUpdateGamelist(false, game);
		testNetIO.injectComObject(updateGameList);
		assertEquals("Observer Update", ViewNotification.gameListUpdate, testObserver.getNotification().remove(0));
		assertTrue("Spiel in Liste", testModel.getLobbyGamelist().contains(game));
		
		game = new GameServerRepresentation("Peter", "Mein Spiel", 6, 1, RulesetType.Wizard, false, false);
		updateGameList = new ComLobbyUpdateGamelist(true, game);
		testNetIO.injectComObject(updateGameList);
		assertEquals("Observer Update", ViewNotification.gameListUpdate, testObserver.getNotification().remove(0));
		assertTrue("Spiel nicht mehr in Liste", testModel.getLobbyGamelist().isEmpty());
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
		players.add("Player2");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		assertEquals("ObserverUpdate", ViewNotification.joinGameSuccessful, testObserver.getNotification().remove(0));
		assertEquals("Spielerliste", players, testModel.getPlayerlist());
	}
	
	@Test
	public void joinGameTest() {
		ComJoinRequest testJoinRequest;
		GameServerRepresentation game = new GameServerRepresentation("Peter", "Mein Spiel", 6, 1, RulesetType.Wizard, false, false);
		ComLobbyUpdateGamelist updateGameList = new ComLobbyUpdateGamelist(false, game);
		testNetIO.injectComObject(updateGameList);
		testModel.joinGame("Peter", "");
		testJoinRequest = (ComJoinRequest) testNetIO.getModelInput().remove(0);
		assertEquals("Game Master", "Peter", testJoinRequest.getGameMasterName());
		assertEquals("Passwort", "", testJoinRequest.getPassword());
		
		List<String> players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		assertEquals("ObserverUpdate", ViewNotification.joinGameSuccessful, testObserver.getNotification().remove(1));
		assertEquals("Spielerliste", players, testModel.getPlayerlist());
	}
}
