package Server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComClientLeave;
import ComObjects.ComClientQuit;
import ComObjects.ComCreateGameRequest;
import ComObjects.ComJoinRequest;
import ComObjects.ComKickPlayerRequest;
import ComObjects.ComLoginRequest;
import ComObjects.ComNewRound;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComWarning;
import ComObjects.MsgCard;
import ComObjects.RulesetMessage;
import ComObjects.WarningMsg;
import Ruleset.ClientRuleset;
import Ruleset.RulesetType;
import Ruleset.ServerRuleset;

import test.MockPlayer;

public class LobbyCheatTest {
	
	LobbyServer lobby;
	MockPlayer player1;
	MockPlayer player2;
	MockPlayer player3;
	MockPlayer cheater;
	
	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();

		player1 = new MockPlayer(lobby);
		player1.setPlayerName("Markus");
		player1.setServer(lobby);

		player2 = new MockPlayer(lobby);
		player2.setPlayerName("Hans");
		player2.setServer(lobby);

		player3 = new MockPlayer(lobby);
		player3.setPlayerName("Klaus");
		player3.setServer(lobby);

		cheater = new MockPlayer(lobby);
		cheater.setPlayerName("cheater");
		cheater.setServer(lobby);

		lobby.addPlayer(player1);
		lobby.addName(player1.getPlayerName());
		lobby.addPlayer(player2);
		lobby.addName(player2.getPlayerName());
		lobby.addPlayer(player3);
		lobby.addName(player3.getPlayerName());
		lobby.addPlayer(cheater);
		lobby.addName(cheater.getPlayerName());
	}
	
	@After
	public void tearDown() throws Exception {
		lobby = null;
		player1 = null;
		player2 = null;
		player3 = null;
		cheater = null;
	}
	
	@Test
	public void sendRulesetMessageTest() {
		ComRuleset message = new ComRuleset(null);
		cheater.injectComObject(message);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void sendKickTest() {
		ComKickPlayerRequest message = new ComKickPlayerRequest("Markus");
		cheater.injectComObject(message);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void sendLoginTest() {
		ComLoginRequest login = new ComLoginRequest("Markus");
		cheater.injectComObject(login);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void sendLeaveTest() {
		ComClientLeave leave = new ComClientLeave();
		cheater.injectComObject(leave);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void sendStartTest() {
		ComStartGame start = new ComStartGame();		
		cheater.injectComObject(start);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void sendNewRoud() {
		ComNewRound start = new ComNewRound(true);		
		cheater.injectComObject(start);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void joinInexistentGameTest() {
		ComJoinRequest message= new ComJoinRequest("Markus", null);
		cheater.injectComObject(message);
		ComWarning tocheater = (ComWarning) cheater.getServerInput().get(1);
		assertTrue(tocheater.getWarning().equals(WarningMsg.GameNotExistent));
	}
	
	@Test
	public void createInvalidGameTest() {
		ComCreateGameRequest message = new ComCreateGameRequest("Markus Game", null, false, null);
		cheater.injectComObject(message);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void createInvalidGameTest2() {
		ComCreateGameRequest message = new ComCreateGameRequest("Markus Game", RulesetType.Hearts, false, null);
		cheater.injectComObject(message);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void createInvalidGameTest3() {
		ComCreateGameRequest message = new ComCreateGameRequest(null, RulesetType.Hearts, false, new String());
		cheater.injectComObject(message);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
	
	@Test
	public void createInvalidGameTest4() {
		ComCreateGameRequest message = new ComCreateGameRequest("Markus Game", RulesetType.Hearts, true, new String());
		cheater.injectComObject(message);
		assertFalse(lobby.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater.getPlayerName()));
	}
}
