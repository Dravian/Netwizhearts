package Ruleset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Server.GameServerRepresentation;
import test.MockMessageListenerThread;
import test.MockObserver;
import Client.ClientModel;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.MsgBoolean;
import ComObjects.MsgCard;
import ComObjects.MsgMultiCards;
import ComObjects.MsgMultiCardsRequest;
import ComObjects.MsgNumber;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelection;
import ComObjects.MsgSelectionRequest;

public class TestClientRulesetIllegalArguments {
	ClientModel testModel;
	MockObserver testObserver;
	MockMessageListenerThread testNetIO;
	String one = "one";
	String two = "two";
	String three = "three";
	String four = "four";
	List<String> players;

	@Before
	public void setUp() throws Exception {
		testNetIO = new MockMessageListenerThread();
		testModel = new ClientModel(testNetIO);

		testNetIO = new MockMessageListenerThread();
		testModel = new ClientModel(testNetIO);
		testObserver = new MockObserver();
		testNetIO.setModel(testModel);
		testModel.addObserver(testObserver);
		testModel.createConnection("Player1", "localhost");
		players = new LinkedList<String>();
		players.add("Player2");
		Set<GameServerRepresentation> games = new HashSet<GameServerRepresentation>();
		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);
	}

	@After
	public void tearDown() throws Exception {
		testNetIO = null;
		testModel = null;
		testObserver = null;
	}

	@Test(expected = IllegalArgumentException.class) 
	public void testWizard1() {
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
		
		testNetIO.injectComObject(new ComRuleset(new MsgMultiCardsRequest(3)));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testWizard2() {
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
		
		Set<Card> cards = new HashSet<Card>();
		cards.add(WizardCard.AchtBlau);
		cards.add(WizardCard.AchtGelb);
		cards.add(WizardCard.AchtGelb);
		testNetIO.injectComObject(new ComRuleset(new MsgMultiCards(cards)));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testWizard3() {
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
		
		testNetIO.injectComObject(new ComRuleset(new MsgBoolean(true)));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testWizard4() {
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
		
		testNetIO.injectComObject(new ComRuleset(new MsgCard(WizardCard.AchtBlau)));
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void testWizard5() {
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
		
		List<Card> cards = new ArrayList<Card>();
		cards.add(WizardCard.AchtBlau);
		cards.add(WizardCard.AchtGelb);
		cards.add(WizardCard.AchtGelb);
		testModel.giveChosenCards(cards);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testWizard6() {
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
		
		testNetIO.injectComObject(new ComRuleset(new MsgNumber(4)));
	}

	@Test(expected = IllegalArgumentException.class) 
	public void testHearts1() {
		testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist(
				"Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
		
		testNetIO.injectComObject(new ComRuleset(new MsgCard(HeartsCard.Caro10)));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testHearts2() {
		testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist(
				"Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
		
		Set<Card> cards = new HashSet<Card>();
		cards.add(HeartsCard.Caro10);
		cards.add(HeartsCard.Caro2);
		cards.add(HeartsCard.Caro3);
		testNetIO.injectComObject(new ComRuleset(new MsgMultiCards(cards)));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testHearts3() {
		testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist(
				"Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
		
		testNetIO.injectComObject(new ComRuleset(new MsgNumber(1)));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testHearts4() {
		testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist(
				"Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
		
		testNetIO.injectComObject(new ComRuleset(new MsgNumberRequest()));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testHearts5() {
		testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist(
				"Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
		
		testNetIO.injectComObject(new ComRuleset(new MsgSelection(Colour.DIAMOND)));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testHearts6() {
		testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist(
				"Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
		
		testNetIO.injectComObject(new ComRuleset(new MsgSelectionRequest()));
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void testHearts7() {
		testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist(
				"Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
		
		testModel.giveColourSelection(Colour.CLUB);
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void testHearts8() {
		testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
		players = new LinkedList<String>();
		players.add("Player1");
		ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
		testNetIO.injectComObject(gameLobbyInit);
		ComUpdatePlayerlist updatePlayerList = new ComUpdatePlayerlist(
				"Player2", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.getPlayerlist().contains("Player2");
		updatePlayerList = new ComUpdatePlayerlist("Player3", false);
		testNetIO.injectComObject(updatePlayerList);
		updatePlayerList = new ComUpdatePlayerlist("Player4", false);
		testNetIO.injectComObject(updatePlayerList);
		testModel.startGame();
		testNetIO.injectComObject(new ComStartGame());
		testNetIO.getModelInput().clear();
		testObserver.getNotification().clear();
		
		testModel.giveInputNumber(1);
	}
}
