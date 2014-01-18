package Ruleset;

import static org.junit.Assert.*;

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
import Client.ClientModel;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.MsgCard;
import ComObjects.MsgCardRequest;
import ComObjects.MsgNumberRequest;
import ComObjects.MsgUser;
import Ruleset.GameClientUpdate;
import Server.GameServerRepresentation;

public class TestClientWizardIsValidMove {
	ClientModel testModel;
	MockObserver testObserver;
	ClientRuleset ruleset;
	MockMessageListenerThread testNetIO;
	String blue = "Blue";
	String white = "White";
	String red = "Red";
	GameClientUpdate gameState;
	PlayerState player = new PlayerState(blue, RulesetType.Wizard);
	String testText;

	List<String> players;
	
	@Before
	public void setUp() throws Exception {
		testNetIO = new MockMessageListenerThread();
		testModel = new ClientModel(testNetIO);
		testObserver = new MockObserver();
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
	
		player.getHand().add(WizardCard.DreiGruen);
		player.getHand().add(WizardCard.ZaubererRot);
		player.getHand().add(WizardCard.ZweiBlau);
		player.getHand().add(WizardCard.NarrBlau);
		
	}

	@After
	public void tearDown() throws Exception {
		testNetIO = null;
		testModel = null;
		ruleset = null;
		testObserver = null;
	}
	
	@Test
	public void testFirstCard() {
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
		
		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		
		
		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		OtherData otherData2 = player3.getOtherData();
		
		List<OtherData> enemyData = new ArrayList<OtherData>();
		
		enemyData.add(otherData1);
		enemyData.add(otherData2);
		
		String currentPlayer = blue;
		int roundNumber = 4;
		Card trumpCard = EmptyCard.Empty;
		
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, trumpCard);
		MsgUser game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.DreiGruen);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.DreiGruen);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZaubererRot);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZaubererRot);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZweiBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZweiBlau);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.NarrBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.NarrBlau);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZweiGelb);
		assertTrue(testNetIO.getModelInput().size() == 0);
		testNetIO.getModelInput().clear();		
		
		testModel.receiveMessage(new ComRuleset(new MsgNumberRequest()));
		testModel.makeMove(WizardCard.NarrBlau);			
	}
	
	
	
	@Test
	public void testNoColour() {
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
		discardPile.add(new DiscardedCard(white,WizardCard.ZweiGelb));
		
		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		
		
		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		OtherData otherData2 = player3.getOtherData();
		
		List<OtherData> enemyData = new ArrayList<OtherData>();
		
		enemyData.add(otherData1);
		enemyData.add(otherData2);
		
		String currentPlayer = blue;
		int roundNumber = 4;
		Card trumpCard = WizardCard.DreizehnGruen;
		
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, trumpCard);
		MsgUser game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.DreiGruen);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.DreiGruen);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZaubererRot);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZaubererRot);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZweiBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZweiBlau);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.NarrBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.NarrBlau);
		testNetIO.getModelInput().clear();		
	}
	
	@Test
	public void testSorcerer() {
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
		discardPile.add(new DiscardedCard(white,WizardCard.ZaubererRot));
		
		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		
		
		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		OtherData otherData2 = player3.getOtherData();
		
		List<OtherData> enemyData = new ArrayList<OtherData>();
		
		enemyData.add(otherData1);
		enemyData.add(otherData2);
		
		String currentPlayer = blue;
		int roundNumber = 4;
		Card trumpCard = WizardCard.DreizehnGruen;
		
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, trumpCard);
		MsgUser game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.DreiGruen);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.DreiGruen);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZaubererRot);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZaubererRot);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZweiBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZweiBlau);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.NarrBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.NarrBlau);
		testNetIO.getModelInput().clear();		
	}
	
	@Test
	public void testSorcererSecondPlayed() {
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
		discardPile.add(new DiscardedCard(white,WizardCard.EinsGruen));
		discardPile.add(new DiscardedCard(white,WizardCard.ZaubererGelb));
		
		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		
		
		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		OtherData otherData2 = player3.getOtherData();
		
		List<OtherData> enemyData = new ArrayList<OtherData>();
		
		enemyData.add(otherData1);
		enemyData.add(otherData2);
		
		String currentPlayer = blue;
		int roundNumber = 4;
		Card trumpCard = WizardCard.DreizehnGruen;
		
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, trumpCard);
		MsgUser game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.DreiGruen);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.DreiGruen);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZaubererRot);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZaubererRot);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZweiBlau);
		assertTrue(testNetIO.getModelInput().size() == 0);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.NarrBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.NarrBlau);
		testNetIO.getModelInput().clear();		
	}
	
	@Test
	public void testColourInHand() {
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
		discardPile.add(new DiscardedCard(white,WizardCard.ZweiGruen));
		
		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		
		
		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		OtherData otherData2 = player3.getOtherData();
		
		List<OtherData> enemyData = new ArrayList<OtherData>();
		
		enemyData.add(otherData1);
		enemyData.add(otherData2);
		
		String currentPlayer = blue;
		int roundNumber = 4;
		Card trumpCard = WizardCard.DreizehnGruen;
		
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, trumpCard);
		MsgUser game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.DreiGruen);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.DreiGruen);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZaubererRot);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZaubererRot);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZweiBlau);
		assertTrue(testNetIO.getModelInput().size() == 0);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.NarrBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.NarrBlau);
		testNetIO.getModelInput().clear();		
	}
	
	@Test
	public void testFool() {
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
		discardPile.add(new DiscardedCard(white,WizardCard.NarrGelb));
		
		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		
		
		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		OtherData otherData2 = player3.getOtherData();
		
		List<OtherData> enemyData = new ArrayList<OtherData>();
		
		enemyData.add(otherData1);
		enemyData.add(otherData2);
		
		String currentPlayer = blue;
		int roundNumber = 4;
		Card trumpCard = WizardCard.DreizehnGruen;
		
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, trumpCard);
		MsgUser game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.DreiGruen);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.DreiGruen);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZaubererRot);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZaubererRot);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZweiBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZweiBlau);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.NarrBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.NarrBlau);
		testNetIO.getModelInput().clear();		
	}
	
	@Test 
	public void testFoolOnFool() {
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
		discardPile.add(new DiscardedCard(white,WizardCard.NarrGelb));
		discardPile.add(new DiscardedCard(red,WizardCard.EinsGruen));
		
		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		
		
		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		OtherData otherData2 = player3.getOtherData();
		
		List<OtherData> enemyData = new ArrayList<OtherData>();
		
		enemyData.add(otherData1);
		enemyData.add(otherData2);
		
		String currentPlayer = blue;
		int roundNumber = 4;
		Card trumpCard = WizardCard.DreizehnGruen;
		
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, trumpCard);
		MsgUser game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.DreiGruen);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.DreiGruen);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZaubererRot);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.ZaubererRot);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.ZweiBlau);
		assertTrue(testNetIO.getModelInput().size() == 0);
		testNetIO.getModelInput().clear();
		
		testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));		
		testModel.makeMove(WizardCard.NarrBlau);
		assertTrue(((MsgCard)((ComRuleset)testNetIO.getModelInput().get(0))
				.getRulesetMessage()).getCard() == WizardCard.NarrBlau);
		testNetIO.getModelInput().clear();
		
	}
}
