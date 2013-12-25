package Server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComChatMessage;
import ComObjects.ComClientLeave;
import ComObjects.ComClientQuit;
import ComObjects.ComInitLobby;
import ComObjects.ComJoinRequest;
import ComObjects.ComKickPlayerRequest;
import ComObjects.ComLobbyUpdateGamelist;
import ComObjects.ComRuleset;
import ComObjects.ComStartGame;
import ComObjects.ComWarning;
import ComObjects.WarningMsg;
import Ruleset.RulesetType;

import test.TestPlayer;

public class GameServerTests {

	LobbyServer lobby;
	GameServer game;
	TestPlayer player1;
	TestPlayer player2;
	TestPlayer player3;
	TestPlayer player4;
	TestPlayer player5;
	TestPlayer player6;
	TestPlayer player7;

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

		player4 = new TestPlayer(lobby);
		player4.setPlayerName("Fritz");
		player4.setServer(lobby);

		player5 = new TestPlayer(lobby);
		player5.setPlayerName("Günther");
		player5.setServer(lobby);

		player6 = new TestPlayer(lobby);
		player6.setPlayerName("Anna");
		player6.setServer(lobby);

		player7 = new TestPlayer(lobby);
		player7.setPlayerName("Peter");
		player7.setServer(lobby);

		lobby.addPlayer(player1);
		lobby.addName(player1.getPlayerName());
		lobby.addPlayer(player2);
		lobby.addName(player2.getPlayerName());
		lobby.addPlayer(player3);
		lobby.addName(player3.getPlayerName());
		lobby.addPlayer(player4);
		lobby.addName(player4.getPlayerName());
		lobby.addPlayer(player5);
		lobby.addName(player5.getPlayerName());
		lobby.addPlayer(player6);
		lobby.addName(player6.getPlayerName());
		lobby.addPlayer(player7);
		lobby.addName(player7.getPlayerName());

		game = new GameServer(lobby, player1, "Markus' Spiel",
				RulesetType.Wizard, "", false);
		lobby.removePlayer(player1);
		player1.changeServer(game);
		lobby.addGameServer(game);

		ComJoinRequest join = new ComJoinRequest("Markus", "");
		player2.injectComObject(join);
		player3.injectComObject(join);
		player4.injectComObject(join);
		player5.injectComObject(join);
		player6.injectComObject(join);
	}

	@After
	public void tearDown() throws Exception {
		lobby = null;
		game = null;
		player1 = null;
		player2 = null;
		player3 = null;
		player4 = null;
		player5 = null;
	}

	// GameLobbyTests
	@Test
	public void testChat() {
		ComChatMessage chat = new ComChatMessage("Hallo!");
		player1.injectComObject(chat);

		assertTrue(player1.getServerInput().get(5).getClass()
				.equals(chat.getClass()));
		assertTrue(player2.getServerInput().get(5).getClass()
				.equals(chat.getClass()));
		assertTrue(player3.getServerInput().get(6).getClass()
				.equals(chat.getClass()));
		assertTrue(player4.getServerInput().get(7).getClass()
				.equals(chat.getClass()));
		assertTrue(player5.getServerInput().get(8).getClass()
				.equals(chat.getClass()));
		assertTrue(player6.getServerInput().get(9).getClass()
				.equals(chat.getClass()));

		ComChatMessage toPlayer1 = (ComChatMessage) player1.getServerInput()
				.get(5);
		assertTrue(toPlayer1.getChatMessage().contains("Hallo!"));

		ComChatMessage toPlayer2 = (ComChatMessage) player2.getServerInput()
				.get(5);
		assertTrue(toPlayer2.getChatMessage().contains("Hallo!"));

		ComChatMessage toPlayer3 = (ComChatMessage) player3.getServerInput()
				.get(6);
		assertTrue(toPlayer3.getChatMessage().contains("Hallo!"));

		ComChatMessage toPlayer4 = (ComChatMessage) player4.getServerInput()
				.get(7);
		assertTrue(toPlayer4.getChatMessage().contains("Hallo!"));

		ComChatMessage toPlayer5 = (ComChatMessage) player5.getServerInput()
				.get(8);
		assertTrue(toPlayer5.getChatMessage().contains("Hallo!"));

		ComChatMessage toPlayer6 = (ComChatMessage) player6.getServerInput()
				.get(9);
		assertTrue(toPlayer6.getChatMessage().contains("Hallo!"));
	}

	@Test
	public void testKickPlayer() {
		ComKickPlayerRequest kick = new ComKickPlayerRequest("Klaus");
		player1.injectComObject(kick);
		assertTrue(lobby.playerSet.contains(player3));

		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player3.getServerInput().get(6).getClass()
				.equals(init.getClass()));

		ComWarning warning = new ComWarning(null);
		assertTrue(player3.getServerInput().get(7).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer3 = (ComWarning) player3.getServerInput().get(7);
		assertTrue(toPlayer3.getWarning().equals(WarningMsg.BeenKicked));
		
		ComLobbyUpdateGamelist to3game = (ComLobbyUpdateGamelist) player3
				.getServerInput().get(8);
		assertTrue(to3game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to3game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to3game.getGameServer().getCurrentPlayers() == 5);
		assertFalse(to3game.isRemoveFlag());
	}

	@Test
	public void testKickPlayerNotInGame() {
		ComKickPlayerRequest kick = new ComKickPlayerRequest("Peter");
		player1.injectComObject(kick);

		ComWarning warning = new ComWarning(null);
		assertTrue(player1.getServerInput().get(5).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer1 = (ComWarning) player1.getServerInput().get(5);
		assertTrue(toPlayer1.getWarning().equals(WarningMsg.CouldntKick));
	}

	@Test
	public void testKickGameMaster() {
		ComKickPlayerRequest kick = new ComKickPlayerRequest("Markus");
		player1.injectComObject(kick);
		assertFalse(lobby.playerSet.contains(player1));

		ComWarning warning = new ComWarning(null);
		assertTrue(player1.getServerInput().get(5).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer1 = (ComWarning) player1.getServerInput().get(5);
		assertTrue(toPlayer1.getWarning().equals(WarningMsg.CouldntKick));
	}

	@Test
	public void testClientLeave() {
		ComClientLeave leave = new ComClientLeave();
		player2.injectComObject(leave);
		assertTrue(lobby.playerSet.contains(player2));

		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player2.getServerInput().get(5).getClass()
				.equals(init.getClass()));
		
		ComLobbyUpdateGamelist to7game = (ComLobbyUpdateGamelist) player7
				.getServerInput().get(11);
		assertTrue(to7game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to7game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to7game.getGameServer().getCurrentPlayers() == 5);
		assertFalse(to7game.isRemoveFlag());
	}

	@Test
	public void testGameMasterLeave() {
		ComClientLeave leave = new ComClientLeave();
		player1.injectComObject(leave);
		assertTrue(lobby.playerSet.contains(player1));
		assertTrue(lobby.playerSet.contains(player2));
		assertTrue(lobby.playerSet.contains(player3));
		assertTrue(lobby.playerSet.contains(player4));
		assertTrue(lobby.playerSet.contains(player5));
		assertTrue(lobby.playerSet.contains(player6));
		assertTrue(lobby.initLobby().getGameList().size() == 0);

		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player1.getServerInput().get(5).getClass()
				.equals(init.getClass()));
		assertTrue(player2.getServerInput().get(5).getClass()
				.equals(init.getClass()));
		assertTrue(player3.getServerInput().get(6).getClass()
				.equals(init.getClass()));
		assertTrue(player4.getServerInput().get(7).getClass()
				.equals(init.getClass()));
		assertTrue(player5.getServerInput().get(8).getClass()
				.equals(init.getClass()));
		assertTrue(player6.getServerInput().get(9).getClass()
				.equals(init.getClass()));

		ComWarning warning = new ComWarning(null);
		assertTrue(player2.getServerInput().get(6).getClass()
				.equals(warning.getClass()));
		assertTrue(player3.getServerInput().get(7).getClass()
				.equals(warning.getClass()));
		assertTrue(player4.getServerInput().get(8).getClass()
				.equals(warning.getClass()));
		assertTrue(player5.getServerInput().get(9).getClass()
				.equals(warning.getClass()));
		assertTrue(player6.getServerInput().get(10).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer2 = (ComWarning) player2.getServerInput().get(6);
		assertTrue(toPlayer2.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer3 = (ComWarning) player3.getServerInput().get(7);
		assertTrue(toPlayer3.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer4 = (ComWarning) player4.getServerInput().get(8);
		assertTrue(toPlayer4.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer5 = (ComWarning) player5.getServerInput().get(9);
		assertTrue(toPlayer5.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer6 = (ComWarning) player6.getServerInput().get(10);
		assertTrue(toPlayer6.getWarning().equals(WarningMsg.GameDisbanded));
		
		ComLobbyUpdateGamelist to7game = (ComLobbyUpdateGamelist) player7
				.getServerInput().get(16);
		assertTrue(to7game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to7game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to7game.isRemoveFlag());
	}

	@Test
	public void testDisconnectPlayer() {
		game.disconnectPlayer(player1);
		assertFalse(lobby.playerSet.contains(player1));
		assertFalse(lobby.getNames().contains(player1));

		assertTrue(lobby.playerSet.contains(player2));
		assertTrue(lobby.playerSet.contains(player3));
		assertTrue(lobby.playerSet.contains(player4));
		assertTrue(lobby.playerSet.contains(player5));
		assertTrue(lobby.playerSet.contains(player6));

		assertTrue(lobby.initLobby().getGameList().size() == 0);

		ComWarning warning = new ComWarning(null);
		assertTrue(player2.getServerInput().get(6).getClass()
				.equals(warning.getClass()));
		assertTrue(player3.getServerInput().get(7).getClass()
				.equals(warning.getClass()));
		assertTrue(player4.getServerInput().get(8).getClass()
				.equals(warning.getClass()));
		assertTrue(player5.getServerInput().get(9).getClass()
				.equals(warning.getClass()));
		assertTrue(player6.getServerInput().get(10).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer2 = (ComWarning) player2.getServerInput().get(6);
		assertTrue(toPlayer2.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer3 = (ComWarning) player3.getServerInput().get(7);
		assertTrue(toPlayer3.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer4 = (ComWarning) player4.getServerInput().get(8);
		assertTrue(toPlayer4.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer5 = (ComWarning) player5.getServerInput().get(9);
		assertTrue(toPlayer5.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer6 = (ComWarning) player6.getServerInput().get(10);
		assertTrue(toPlayer6.getWarning().equals(WarningMsg.GameDisbanded));
	}


	@Test
	public void testStartGame() {
		ComStartGame start = new ComStartGame();
		player1.injectComObject(start);

		ComRuleset ruleset = new ComRuleset(null);
		assertTrue(player1.getServerInput().get(6).getClass()
				.equals(ruleset.getClass()));
		assertTrue(player2.getServerInput().get(6).getClass()
				.equals(ruleset.getClass()));
		assertTrue(player3.getServerInput().get(7).getClass()
				.equals(ruleset.getClass()));
		assertTrue(player4.getServerInput().get(8).getClass()
				.equals(ruleset.getClass()));
		assertTrue(player5.getServerInput().get(9).getClass()
				.equals(ruleset.getClass()));
		assertTrue(player6.getServerInput().get(10).getClass()
				.equals(ruleset.getClass()));
		
		ComLobbyUpdateGamelist to7game = (ComLobbyUpdateGamelist) player7
				.getServerInput().get(10);
		assertTrue(to7game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to7game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to7game.getGameServer().isHasStarted());
		assertFalse(to7game.isRemoveFlag());
	}

	@Test
	public void testStartGameWithToManyPlayers() {
		lobby.removePlayer(player7);
		game.addPlayer(player7);
		ComStartGame start = new ComStartGame();
		player1.injectComObject(start);

		assertTrue(lobby.playerSet.contains(player1));
		assertTrue(lobby.playerSet.contains(player2));
		assertTrue(lobby.playerSet.contains(player3));
		assertTrue(lobby.playerSet.contains(player4));
		assertTrue(lobby.playerSet.contains(player5));
		assertTrue(lobby.playerSet.contains(player6));
		assertTrue(lobby.playerSet.contains(player7));
		assertTrue(lobby.initLobby().getGameList().size() == 0);

		ComWarning warning = new ComWarning(null);
		assertTrue(player1.getServerInput().get(6).getClass()
				.equals(warning.getClass()));
		assertTrue(player2.getServerInput().get(6).getClass()
				.equals(warning.getClass()));
		assertTrue(player3.getServerInput().get(7).getClass()
				.equals(warning.getClass()));
		assertTrue(player4.getServerInput().get(8).getClass()
				.equals(warning.getClass()));
		assertTrue(player5.getServerInput().get(9).getClass()
				.equals(warning.getClass()));
		assertTrue(player6.getServerInput().get(10).getClass()
				.equals(warning.getClass()));
		assertTrue(player7.getServerInput().get(11).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer1 = (ComWarning) player1.getServerInput().get(6);
		assertTrue(toPlayer1.getWarning().equals(WarningMsg.CouldntStart));

		ComWarning toPlayer2 = (ComWarning) player2.getServerInput().get(6);
		assertTrue(toPlayer2.getWarning().equals(WarningMsg.CouldntStart));

		ComWarning toPlayer3 = (ComWarning) player3.getServerInput().get(7);
		assertTrue(toPlayer3.getWarning().equals(WarningMsg.CouldntStart));

		ComWarning toPlayer4 = (ComWarning) player4.getServerInput().get(8);
		assertTrue(toPlayer4.getWarning().equals(WarningMsg.CouldntStart));

		ComWarning toPlayer5 = (ComWarning) player5.getServerInput().get(9);
		assertTrue(toPlayer5.getWarning().equals(WarningMsg.CouldntStart));

		ComWarning toPlayer6 = (ComWarning) player6.getServerInput().get(10);
		assertTrue(toPlayer6.getWarning().equals(WarningMsg.CouldntStart));

		ComWarning toPlayer7 = (ComWarning) player7.getServerInput().get(11);
		assertTrue(toPlayer7.getWarning().equals(WarningMsg.CouldntStart));

		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player1.getServerInput().get(7).getClass()
				.equals(init.getClass()));
		assertTrue(player2.getServerInput().get(7).getClass()
				.equals(init.getClass()));
		assertTrue(player3.getServerInput().get(8).getClass()
				.equals(init.getClass()));
		assertTrue(player4.getServerInput().get(9).getClass()
				.equals(init.getClass()));
		assertTrue(player5.getServerInput().get(10).getClass()
				.equals(init.getClass()));
		assertTrue(player6.getServerInput().get(11).getClass()
				.equals(init.getClass()));
		assertTrue(player7.getServerInput().get(12).getClass()
				.equals(init.getClass()));
	}

	@Test
	public void testStartGameWithToFewPlayers() {
		ComKickPlayerRequest kick1 = new ComKickPlayerRequest("Anna");
		player1.injectComObject(kick1);

		ComKickPlayerRequest kick2 = new ComKickPlayerRequest("Hans");
		player1.injectComObject(kick2);

		ComKickPlayerRequest kick3 = new ComKickPlayerRequest("Klaus");
		player1.injectComObject(kick3);

		ComKickPlayerRequest kick4 = new ComKickPlayerRequest("Fritz");
		player1.injectComObject(kick4);

		ComStartGame start = new ComStartGame();
		player1.injectComObject(start);

		ComWarning warning = new ComWarning(null);
		assertTrue(player1.getServerInput().get(10).getClass()
				.equals(warning.getClass()));
		assertTrue(player5.getServerInput().get(13).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer1 = (ComWarning) player1.getServerInput().get(10);
		assertTrue(toPlayer1.getWarning().equals(WarningMsg.CouldntStart));

		ComWarning toPlayer5 = (ComWarning) player5.getServerInput().get(13);
		assertTrue(toPlayer5.getWarning().equals(WarningMsg.CouldntStart));

		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player1.getServerInput().get(11).getClass()
				.equals(init.getClass()));
		assertTrue(player5.getServerInput().get(14).getClass()
				.equals(init.getClass()));
	}

	@Test
	public void testClientQuitRunningGame() {
		game.setHasStarted(true);
		ComClientLeave leave = new ComClientLeave();
		player1.injectComObject(leave);
		assertTrue(lobby.playerSet.contains(player1));
		assertTrue(lobby.playerSet.contains(player2));
		assertTrue(lobby.playerSet.contains(player3));
		assertTrue(lobby.playerSet.contains(player4));
		assertTrue(lobby.playerSet.contains(player5));
		assertTrue(lobby.playerSet.contains(player6));
		assertTrue(lobby.playerSet.contains(player7));
		assertTrue(lobby.initLobby().getGameList().size() == 0);

		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player1.getServerInput().get(5).getClass()
				.equals(init.getClass()));
		assertTrue(player2.getServerInput().get(5).getClass()
				.equals(init.getClass()));
		assertTrue(player3.getServerInput().get(6).getClass()
				.equals(init.getClass()));
		assertTrue(player4.getServerInput().get(7).getClass()
				.equals(init.getClass()));
		assertTrue(player5.getServerInput().get(8).getClass()
				.equals(init.getClass()));
		assertTrue(player6.getServerInput().get(9).getClass()
				.equals(init.getClass()));

		ComWarning warning = new ComWarning(null);
		assertTrue(player2.getServerInput().get(6).getClass()
				.equals(warning.getClass()));
		assertTrue(player3.getServerInput().get(7).getClass()
				.equals(warning.getClass()));
		assertTrue(player4.getServerInput().get(8).getClass()
				.equals(warning.getClass()));
		assertTrue(player5.getServerInput().get(9).getClass()
				.equals(warning.getClass()));
		assertTrue(player6.getServerInput().get(10).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer2 = (ComWarning) player2.getServerInput().get(6);
		assertTrue(toPlayer2.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer3 = (ComWarning) player3.getServerInput().get(7);
		assertTrue(toPlayer3.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer4 = (ComWarning) player4.getServerInput().get(8);
		assertTrue(toPlayer4.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer5 = (ComWarning) player5.getServerInput().get(9);
		assertTrue(toPlayer5.getWarning().equals(WarningMsg.GameDisbanded));

		ComWarning toPlayer6 = (ComWarning) player6.getServerInput().get(10);
		assertTrue(toPlayer6.getWarning().equals(WarningMsg.GameDisbanded));
	}
}
