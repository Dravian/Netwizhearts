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
import ComObjects.ComClientQuit;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.MsgMultiCardsRequest;
import ComObjects.MsgNumberRequest;
import Ruleset.Card;
import Ruleset.RulesetType;
import Ruleset.UserMessages;
import Server.GameServerRepresentation;

public class ClientInGameHeartsTest {

	ClientModel testModel;

	TestObserver testObserver;

	TestMessageListenerThread testNetIO;

	String testText;
	
	LanguageInterpreter textGen;

	List<String> players;

	@Before
	public void setUp() throws Exception {
		testNetIO = new TestMessageListenerThread();
		testObserver = new TestObserver();
		testModel = new ClientModel((MessageListenerThread) testNetIO);
		textGen = new LanguageInterpreter(testModel.getLanguage());
		testNetIO.setModel(testModel);
		testModel.addObserver(testObserver);
		testModel.createConnection("Player1", "localhost");
		players = new LinkedList<String>();
		players.add("Player2");
		Set<GameServerRepresentation> games =
				new HashSet<GameServerRepresentation>();
		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);
		testModel.hostGame("My <3", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
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
		ComChatMessage testMessage = new ComChatMessage("Hello Test");
		testNetIO.injectComObject(testMessage);
		assertTrue("Vergleich der empfangenen Chatnachrichten", 
		   testObserver.getChatMessage().equals(testMessage.getChatMessage()));
	}

	 @Test
	 public void openChooseCardsWindowTest() {
		 testNetIO.injectComObject(new ComRuleset(new MsgMultiCardsRequest(3)));
			assertEquals("Karten Auswahl",
					ViewNotification.openChooseCards,
					testObserver.getNotification().remove(0));
			assertTrue("Ausgabe Text",
					testModel.getWindowText().contains(
							textGen.resolveWarning(UserMessages.ChooseCards)));
	 }

	 @Test
	public void clientQuitTest() {
	   testModel.receiveMessage(new ComClientQuit());
	}

	@Test (expected=IllegalArgumentException.class)
	public void giveChoosenCardsNullArgumentNullTest() {
		testModel.giveChosenCards(null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void giveChoosenCardsEmptyListTest() {
		testModel.giveChosenCards(new LinkedList<Card>());
	}

	@Test (expected=IllegalArgumentException.class)
	public void receiveComRulesetArgumentNullMessageTest1() {
		testModel.receiveMessage((ComRuleset) null);
	}

	@Test (expected=IllegalArgumentException.class)
	public void receiveComRulesetArgumentNullMessageTest2() {
		testModel.receiveMessage(new ComRuleset(null));
	}
}