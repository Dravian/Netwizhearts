package Client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import Ruleset.Card;
import Ruleset.Colour;
import Ruleset.DiscardedCard;
import Ruleset.EmptyCard;
import Ruleset.GameClientUpdate;
import Ruleset.OtherData;
import Ruleset.PlayerState;
import Ruleset.RulesetType;
import Ruleset.UserMessages;
import Ruleset.WizardCard;
import Server.GameServerRepresentation;

public class ClientInGameWizardTest {

	ClientModel testModel;

	MockObserver testObserver;

	MockMessageListenerThread testNetIO;

	String testText;

	LanguageInterpreter textGen;

	List<String> players;

	@Before
	public void setUp() throws Exception {
		testNetIO = new MockMessageListenerThread();
		testObserver = new MockObserver();
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
		testModel.hostGame("My <3", false, "", RulesetType.Wizard);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList =
				new ComUpdatePlayerlist("Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
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
	public void openInputNumberWindowTest() {
		testNetIO.injectComObject(new ComRuleset(new MsgNumberRequest()));
		assertEquals("Open InputNumber",
				ViewNotification.openInputNumber,
				testObserver.getNotification().remove(0));
		assertTrue("Ausgabe Text",
				testModel.getWindowText().contains(
						textGen.resolveWarning(UserMessages.ChooseNumber)));
	}

	/*
	@Test
	public void giveInputNumberTest() {
		testNetIO.injectComObject(new ComRuleset(new MsgNumberRequest()));
		testModel.giveInputNumber(1);
		assertEquals("Stich Anzahl", 1, testNetIO.getModelInput().remove(0));
	} */

    @Test
    public void getTrumpColourTest() {
    	testNetIO.injectComObject(new ComRuleset(new MsgSelection(Colour.RED)));
    	assertTrue(testModel.getTrumpColour().equals(Colour.RED));
    }

	@Test
	public void openChooseColourWindowTest() {
		testNetIO.injectComObject(new ComRuleset(new MsgSelectionRequest()));
		assertEquals("Farbe in Wizard wählen",
				ViewNotification.openChooseItem,
				testObserver.getNotification().remove(0));
		assertTrue("Ausgabe Text",
				testModel.getWindowText().contains(
						textGen.resolveWarning(UserMessages.ChooseColour)));
	}

	@Test
	public void getColoursToChooseFromTest() {
		assertTrue("Farbe Blau",
				testModel.getColoursToChooseFrom().contains(Colour.BLUE));
		assertTrue("Farbe Gruen",
				testModel.getColoursToChooseFrom().contains(Colour.GREEN));
		assertTrue("Farbe Rot",
				testModel.getColoursToChooseFrom().contains(Colour.RED));
		assertTrue("Farbe Gelb",
				testModel.getColoursToChooseFrom().contains(Colour.YELLOW));
	}

	@Test
	public void giveColourSelectionTest() {
		testNetIO.injectComObject(new ComRuleset(new MsgSelectionRequest()));
		testModel.giveColourSelection(Colour.RED);
		ComRuleset msg = (ComRuleset) testNetIO.getModelInput().remove(0);
		MsgSelection selection = (MsgSelection) msg.getRulesetMessage();
		assertEquals("Trumpffarbe Wizard vergleich",
				Colour.RED, selection.getSelection());
	}

	@Test (expected=IllegalArgumentException.class)
	public void giveColourSelectionNullTest() {
		testNetIO.injectComObject(new ComRuleset(new MsgSelectionRequest()));
		testModel.giveColourSelection(null);
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