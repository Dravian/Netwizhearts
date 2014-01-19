package Ruleset;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockMessageListenerThread;
import Client.ClientController;
import Client.ClientModel;
import Client.MessageListenerThread;
import ComObjects.ComClientQuit;
import ComObjects.ComInitGameLobby;
import ComObjects.ComInitLobby;
import ComObjects.ComNewRound;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.MsgGameEnd;
import ComObjects.MsgUser;
import Server.GameServerRepresentation;

public class ClientViewTestEnd {

	ClientModel testModel;

	MockMessageListenerThread testNetIO;

	String testText;

	ClientController controller;

	List<String> players;
	
	String blue = "Blue";
	String white = "White";
	String red = "Red";
	GameClientUpdate gameState;
	PlayerState player = new PlayerState(blue, RulesetType.Wizard);

	@Before
	public void setUp() throws Exception {
		testNetIO = new MockMessageListenerThread();
		testModel = new ClientModel((MessageListenerThread) testNetIO);
		testNetIO.setModel(testModel);
		controller = new ClientController(testModel);
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
	}

	@After
	public void tearDown() throws Exception {
		testNetIO = null;
    	testModel = null;
    	testText = null;
	}

	@Test
	public void openScoreWindowTest() throws InterruptedException {

		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();

		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		

		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		player3.getOtherData().setPoints(20);
		OtherData otherData2 = player3.getOtherData();

		List<OtherData> enemyData = new ArrayList<OtherData>();

		enemyData.add(otherData1);
		enemyData.add(otherData2);

		String currentPlayer = blue;
		int roundNumber = 4;
		Card trumpCard = WizardCard.DreizehnGruen;

		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, EmptyCard.Empty);
		MsgUser game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		//Thread.sleep(7000);
		player.getHand().add(WizardCard.EinsRot);
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, trumpCard);
		game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));

		//Thread.sleep(7000);

		player.getHand().add(WizardCard.AchtBlau);
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, EmptyCard.Empty);
		game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));
		//Thread.sleep(7000); 

		player.getHand().add(WizardCard.AchtBlau);
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, WizardCard.AchtGelb);
		game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));
		//Thread.sleep(7000); 

		player.getHand().remove(WizardCard.AchtBlau);
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, EmptyCard.Empty);
		game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));
		//Thread.sleep(7000); 

		List<String> winners = new ArrayList<String>();
		winners.add(red);
		testNetIO.injectComObject(new ComRuleset(new MsgGameEnd(winners)));
		testNetIO.getModelInput().clear();

		//Thread.sleep(10000);
		testModel.votePlayAgain(true);

		testNetIO.injectComObject(new ComStartGame());
		player.getHand().add(WizardCard.DreiGruen);
		gameState = new GameClientUpdate(player, discardPile,
				enemyData, currentPlayer, roundNumber, EmptyCard.Empty);
		game = new MsgUser(gameState);
		testModel.receiveMessage(new ComRuleset(game));
		assertEquals("Neue Runde", true,
				((ComNewRound) testNetIO.getModelInput().get(0)).getResult());
		//Thread.sleep(10000);
		//testNetIO.injectComObject(new ComClientQuit());
	}

	@Test
	public void returnToLobbyTest() throws InterruptedException {
		List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();

		PlayerState player2 = new PlayerState(white, RulesetType.Wizard);
		OtherData otherData1 = player2.getOtherData();		

		PlayerState player3 = new PlayerState(red, RulesetType.Wizard);
		player3.getOtherData().setPoints(20);
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
		
		List<String> winners = new ArrayList<String>();
		winners.add(red);
		testNetIO.injectComObject(new ComRuleset(new MsgGameEnd(winners)));
		/* im Rahmen des Tests eine Exception in der View,
		   da der Test eine Auswahl trifft,
		   noch bevor die View das Fenster gezeichnet hat. */
		//Thread.sleep(10000);
		testNetIO.getModelInput().clear();

		testModel.votePlayAgain(false);
		assertEquals("Neue Runde", false,
				((ComNewRound) testNetIO.getModelInput().get(0)).getResult());

		List<String> players = new LinkedList<String>();
		players.add("Player1");
		players.add("player2");
		players.add("Player3");
		Set<GameServerRepresentation> games =
				new HashSet<GameServerRepresentation>();
		ComInitLobby testInitLobby = new ComInitLobby(players, games);
		testNetIO.injectComObject(testInitLobby);
		//Thread.sleep(10000);
		//testNetIO.injectComObject(new ComClientQuit());
	}
}
