package Client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Ruleset.Card;
import Ruleset.GamePhase;
import Ruleset.RulesetType;
import Server.GameServerRepresentation;
import test.MockMessageListenerThread;
import test.MockObserver;
import Client.View.Language;
import ComObjects.ComChatMessage;
import ComObjects.ComClientQuit;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComLobbyUpdateGamelist;
import ComObjects.ComLoginRequest;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.ComWarning;
import ComObjects.WarningMsg;

public class ClientLoginTest {

	ClientModel testModel;

	MockObserver testObserver;

	MockMessageListenerThread testNetIO;

	@Before
	public void setUp() throws Exception {
		testNetIO = new MockMessageListenerThread();
		testObserver = new MockObserver();
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

		testModel.createConnection("Player1", "localhost:49152");
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
		assertEquals("Name und Addresse leer", ViewNotification.openWarning,
				testObserver.getNotification().remove(0));
	}

	@Test
	public void changeLanguageTest() {
		assertEquals("Sprache Englisch", Language.English,
				testModel.getLanguage());
		testModel.setLanguage(Language.German);
		assertEquals("Sprache Deutsch", Language.German,
				testModel.getLanguage());
		testModel.setLanguage(Language.Bavarian);
		assertEquals("Sprache Bayerisch", Language.Bavarian,
				testModel.getLanguage());
	}

	@Test
	public void loginWarningTest() {
		String warning = "Login failed. Name is already in use.";
		testNetIO.injectComObject(new ComWarning(WarningMsg.LoginError));
		assertEquals("Login Warnung", ViewNotification.openWarning,
				testObserver.getNotification().get(0));
		assertTrue("Login Warnung Text",
				testModel.getWarningText().contains(warning));
	}

	@Test
	public void getAvailableRulesetsTest() {
		assertTrue("Wizard Regelwerk verfügbar",
				testModel.getRulesets().contains(RulesetType.Wizard));
		assertTrue("Hearts Regelwerk verfügbar",
				testModel.getRulesets().contains(RulesetType.Hearts));
	}

	@Test
	public void clientQuitTest() {
		testModel.receiveMessage(new ComClientQuit());
	}

	@Test (expected=IllegalArgumentException.class)
	public void setLanguageArgumentNullTest() {
		testModel.setLanguage(null);
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

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionsTest() {
		testModel.receiveMessage((ComChatMessage) null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionInitGameLobbyTest() {
		testModel.receiveMessage((ComInitGameLobby) null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionReceiveRulesetMessageTest() {
		testModel.receiveMessage((ComRuleset) null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionStartGameTest1() {
		testModel.receiveMessage((ComStartGame) null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionStartGameTest2() {
		testModel.startGame();
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionUpdatePlayerlistTest() {
		testModel.receiveMessage((ComUpdatePlayerlist) null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionReceiveChatTest() {
		testModel.receiveMessage((ComLobbyUpdateGamelist) null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionSendChatTest() {
		testModel.sendChatMessage("Hello IllegalState!");
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionHostGameTest() {
		testModel.hostGame("<3", false, "password", RulesetType.Wizard);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionJoinGameTest() {
		testModel.joinGame("<3", "");
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionKickPlayerTest() {
		testModel.kickPlayer("Player1");
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionAnnounceTurnTest() {
		testModel.announceTurn(null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionAnnounceWinnerTest() {
		testModel.announceWinner(null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionGetCardsToChooseFromTest() {
		testModel.getCardsToChooseFrom();
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionGetColoursToChooseFromTest() {
		testModel.getColoursToChooseFrom();
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionGetTrumpColourTest() {
		testModel.getTrumpColour();
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionMakeMoveTest() {
		testModel.makeMove(null);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionUpdateGameTest() {
		testModel.updateGame();
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionGetGameUpdateTest() {
		testModel.getGameUpdate();
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionVotePlayAgainTest() {
		testModel.votePlayAgain(true);
	}

	@Test (expected=IllegalStateException.class)
	public void wrongClientStateExceptionReturnToLobbyTest() {
		testModel.returnToLobby();
	}

	@Test (expected=IllegalArgumentException.class)
	public void openWarningArgumentNull() {
		testModel.openWarning(null);
	}

	@Test (expected=IllegalStateException.class)
	public void openNumberInputIllegalStateTest() {
		testModel.openNumberInputWindow(null);
	}

	@Test (expected=IllegalStateException.class)
	public void openChooseCardsIllegalStateTest() {
		testModel.openChooseCardsWindow(null);
	}

	@Test (expected=IllegalStateException.class)
	public void openChooseColourIllegalStateTest() {
		testModel.openChooseColourWindow(null);
	}

	@Test (expected=IllegalStateException.class)
	public void giveInputNumberIllegalStateTest() {
		testModel.giveInputNumber(0);
	}

	@Test
	public void getWinnerEmptyTest() {
		assertTrue("LeerString bei Gewinner", testModel.getWinner().isEmpty());
	}

	@Test
	public void closeProgramTest() {
		testModel.closeProgram();
	}

	@Test
	public void closeViewTest() {
		testModel.closeView();
		assertEquals("Warnung Ausgeben",
				ViewNotification.openWarning,
				testObserver.getNotification().remove(0));
		assertEquals("Fenster schliessen",
				ViewNotification.quitGame,
				testObserver.getNotification().remove(0));
	}
}
