package Server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComCreateGameRequest;
import ComObjects.ComJoinRequest;
import ComObjects.ComKickPlayerRequest;
import ComObjects.ComLoginRequest;
import ComObjects.ComStartGame;
import Ruleset.RulesetType;

import test.TestPlayer;

public class GameServerCheatTest {
	LobbyServer lobby;
	GameServer game;
	TestPlayer player1;
	TestPlayer player2;
	TestPlayer player3;
	TestPlayer cheater;
	
	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();

		player1 = new TestPlayer(lobby);
		player1.setPlayerName("Markus");
		player1.setServer(lobby);

		player2 = new TestPlayer(lobby);
		player2.setPlayerName("Hans");
		player2.setServer(lobby);

		player3 = new TestPlayer(lobby);
		player3.setPlayerName("Klaus");
		player3.setServer(lobby);

		cheater = new TestPlayer(lobby);
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

		game = new GameServer(lobby, player1, "Markus' Spiel",
				RulesetType.Wizard, "", false);
		lobby.removePlayer(player1);
		player1.changeServer(game);
		lobby.addGameServer(game);

		ComJoinRequest join = new ComJoinRequest("Markus", "");
		player2.injectComObject(join);
		player3.injectComObject(join);
		cheater.injectComObject(join);
	}

	@After
	public void tearDown() throws Exception {
		lobby = null;
		game = null;
		player1 = null;
		player2 = null;
		player3 = null;
	}
	
	@Test
	public void sendLoginTest() {
		ComLoginRequest login = new ComLoginRequest("Markus");
		cheater.injectComObject(login);
		assertFalse(game.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater));
	}
	
	@Test
	public void sendCreateTest() {
		ComCreateGameRequest create = new ComCreateGameRequest("Markus game", null, false, null);
		cheater.injectComObject(create);
		assertFalse(game.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater));
	}
	
	@Test
	public void sendJoinTest() {
		ComJoinRequest login = new ComJoinRequest("Markus game", new String());
		cheater.injectComObject(login);
		assertFalse(game.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater));
	}
	
	@Test
	public void testKickWhenNotGameMaster() {
		ComKickPlayerRequest kick = new ComKickPlayerRequest("Hans");
		cheater.injectComObject(kick);
		assertFalse(game.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater));
	}
	
	@Test
	public void testStartGameWhenNotGameMaster() {
		ComStartGame start = new ComStartGame();
		cheater.injectComObject(start);
		assertFalse(game.playerSet.contains(cheater));
		assertFalse(lobby.getNames().contains(cheater));
	}
	
	@Test
	public void testStartGameTwice() {
		ComStartGame start = new ComStartGame();
		player1.injectComObject(start);
		player1.injectComObject(start);
		assertFalse(game.playerSet.contains(player1));
		assertFalse(lobby.getNames().contains(player1));
	}
}
