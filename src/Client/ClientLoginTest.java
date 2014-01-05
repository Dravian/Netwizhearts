package Client;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
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
import ComObjects.ComInitLobby;
import ComObjects.ComLoginRequest;

public class ClientLoginTest {

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
		testModel.createConnection("Player1", "localhost");
		testLoginRequest =
				(ComLoginRequest) testNetIO.getModelInput().remove(0);
		assertEquals("Versende Login Request", "Player1",
				testLoginRequest.getPlayerName());

		List<String> players = new LinkedList<String>();
		players.add("Player1");
		Set<GameServerRepresentation> games =
				new HashSet<GameServerRepresentation>();
		GameServerRepresentation game = new GameServerRepresentation("Peter",
				"Mein Spiel", 6, 1, RulesetType.Wizard, false, false);
		games.add(game);

		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);

		assertEquals("Observer Update", ViewNotification.windowChangeForced,
				testObserver.getNotification().remove(0));
		assertEquals("Spielerliste", players.get(0),
				testModel.getPlayerlist().get(0));
		assertEquals("Spielliste", games.contains(game),
				testModel.getLobbyGamelist().contains(game));
	}

	@Test
	public void loginTestPortScope() {
		testModel.createConnection("Player1", "localhost:-");
		assertEquals("Leerer Name", ViewNotification.openWarning,
				testObserver.getNotification().remove(0));

		testModel.createConnection("Player1", "localhost:-4567");
		assertEquals("Leerer Name", ViewNotification.openWarning,
				testObserver.getNotification().remove(0));

		testModel.createConnection("Player1", "localhost:0");
		assertEquals("Leerer Name", ViewNotification.openWarning,
				testObserver.getNotification().remove(0));

		testModel.createConnection("Player1", "localhost:99999");
		assertEquals("Leerer Name", ViewNotification.openWarning,
				testObserver.getNotification().remove(0));
	}

	@Test
	public void loginTestEmptyName() {
		testModel.createConnection("", "localhost");
		assertEquals("Leerer Name", ViewNotification.openWarning,
				testObserver.getNotification().remove(0));
	}

	@Test
	public void loginTestEmptyServer() {
		testModel.createConnection("Player1", "");
		assertEquals("Leere Addresse", ViewNotification.openWarning,
				testObserver.getNotification().remove(0));
	}

	@Test
	public void loginTestEmptyArguments() {
		testModel.createConnection("", "");
		testModel.createConnection("", "");
		assertEquals("Name und Addresse leer", ViewNotification.openWarning,
				testObserver.getNotification().remove(0));
	}

	@Test (expected=IllegalArgumentException.class)
	public void loginTestPlayerNull() {
		testModel.createConnection(null, "localhost");
	}

	@Test (expected=IllegalArgumentException.class)
	public void loginTestServerNull() {
		testModel.createConnection("Player1", null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void loginTestArgumentsNull() {
		testModel.createConnection(null, null);
	}
}
