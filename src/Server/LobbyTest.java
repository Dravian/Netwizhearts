package Server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComChatMessage;
import ComObjects.ComClientQuit;
import ComObjects.ComCreateGameRequest;
import ComObjects.ComInitGameLobby;
import ComObjects.ComJoinRequest;
import ComObjects.ComLobbyUpdateGamelist;
import ComObjects.ComUpdatePlayerlist;
import ComObjects.ComWarning;
import ComObjects.WarningMsg;
import Ruleset.RulesetType;
import test.TestPlayer;

public class LobbyTest {

	LobbyServer lobby;
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

	}

	@After
	public void tearDown() throws Exception {
		lobby = null;
		player1 = null;
		player2 = null;
		player3 = null;
		player4 = null;
		player5 = null;
	}

	@Test
	public void testClientQuit() {
		ComClientQuit quit = new ComClientQuit();
		player1.injectComObject(quit);
		assertFalse(lobby.playerSet.contains(player1));
		assertFalse(lobby.getNames().contains(player1.getPlayerName()));

		ComUpdatePlayerlist update = new ComUpdatePlayerlist(player1.getName(),
				true);
		assertTrue(player2.getServerInput().get(0).getClass()
				.equals(update.getClass()));
		assertTrue(player3.getServerInput().get(0).getClass()
				.equals(update.getClass()));
		assertTrue(player4.getServerInput().get(0).getClass()
				.equals(update.getClass()));

		ComUpdatePlayerlist toPlayer2 = (ComUpdatePlayerlist) player2
				.getServerInput().get(0);
		assertTrue(toPlayer2.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(toPlayer2.isRemoveFlag());

		ComUpdatePlayerlist toPlayer3 = (ComUpdatePlayerlist) player3
				.getServerInput().get(0);
		assertTrue(toPlayer3.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(toPlayer3.isRemoveFlag());

		ComUpdatePlayerlist toPlayer4 = (ComUpdatePlayerlist) player4
				.getServerInput().get(0);
		assertTrue(toPlayer4.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(toPlayer4.isRemoveFlag());
	}

	@Test
	public void testChat() {
		ComChatMessage chat = new ComChatMessage("Hallo!");
		player1.injectComObject(chat);
		assertTrue(player1.getServerInput().get(0).getClass()
				.equals(chat.getClass()));
		assertTrue(player2.getServerInput().get(0).getClass()
				.equals(chat.getClass()));
		assertTrue(player3.getServerInput().get(0).getClass()
				.equals(chat.getClass()));
		assertTrue(player4.getServerInput().get(0).getClass()
				.equals(chat.getClass()));

		ComChatMessage toPlayer1 = (ComChatMessage) player1.getServerInput()
				.get(0);
		assertTrue(toPlayer1.getChatMessage().contains("Hallo!"));

		ComChatMessage toPlayer2 = (ComChatMessage) player2.getServerInput()
				.get(0);
		assertTrue(toPlayer2.getChatMessage().contains("Hallo!"));

		ComChatMessage toPlayer3 = (ComChatMessage) player3.getServerInput()
				.get(0);
		assertTrue(toPlayer3.getChatMessage().contains("Hallo!"));

		ComChatMessage toPlayer4 = (ComChatMessage) player4.getServerInput()
				.get(0);
		assertTrue(toPlayer4.getChatMessage().contains("Hallo!"));
	}

	@Test
	public void testCreateGame() {
		ComCreateGameRequest create = new ComCreateGameRequest("Markus' Spiel",
				RulesetType.Hearts, false, new String());
		player1.injectComObject(create);

		assertTrue(lobby.initLobby().getGameList().size() == 1);

		assertFalse(lobby.playerSet.contains(player1));
		assertTrue(lobby.getNames().contains(player1.getPlayerName()));

		ComInitGameLobby comInit = new ComInitGameLobby(null);
		assertTrue(player1.getServerInput().get(0).getClass()
				.equals(comInit.getClass()));

		ComInitGameLobby toPlayer1 = (ComInitGameLobby) player1
				.getServerInput().get(0);
		assertTrue(toPlayer1.getPlayerList().get(0) == player1.getPlayerName());

		ComUpdatePlayerlist updatePlayer = new ComUpdatePlayerlist(
				player1.getName(), true);
		assertTrue(player2.getServerInput().get(0).getClass()
				.equals(updatePlayer.getClass()));
		assertTrue(player3.getServerInput().get(0).getClass()
				.equals(updatePlayer.getClass()));
		assertTrue(player4.getServerInput().get(0).getClass()
				.equals(updatePlayer.getClass()));

		ComUpdatePlayerlist to2player = (ComUpdatePlayerlist) player2
				.getServerInput().get(0);
		assertTrue(to2player.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(to2player.isRemoveFlag());

		ComUpdatePlayerlist to3player = (ComUpdatePlayerlist) player3
				.getServerInput().get(0);
		assertTrue(to3player.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(to3player.isRemoveFlag());

		ComUpdatePlayerlist to4player = (ComUpdatePlayerlist) player4
				.getServerInput().get(0);
		assertTrue(to4player.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(to4player.isRemoveFlag());

		ComLobbyUpdateGamelist updateGame = new ComLobbyUpdateGamelist(false,
				new GameServerRepresentation(player1.getPlayerName(),
						"Markus' Spiel", 4, 1, RulesetType.Hearts, false, false));
		assertTrue(player2.getServerInput().get(1).getClass()
				.equals(updateGame.getClass()));
		assertTrue(player3.getServerInput().get(1).getClass()
				.equals(updateGame.getClass()));
		assertTrue(player4.getServerInput().get(1).getClass()
				.equals(updateGame.getClass()));

		ComLobbyUpdateGamelist to2game = (ComLobbyUpdateGamelist) player2
				.getServerInput().get(1);
		assertTrue(to2game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to2game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to2game.getGameServer().getMaxPlayers() == 4);
		assertTrue(to2game.getGameServer().getCurrentPlayers() == 1);
		assertTrue(to2game.getGameServer().getRuleset()
				.equals(RulesetType.Hearts));
		assertFalse(to2game.getGameServer().hasPassword());
		assertFalse(to2game.isRemoveFlag());

		ComLobbyUpdateGamelist to3game = (ComLobbyUpdateGamelist) player3
				.getServerInput().get(1);
		assertTrue(to3game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to3game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to3game.getGameServer().getMaxPlayers() == 4);
		assertTrue(to3game.getGameServer().getCurrentPlayers() == 1);
		assertTrue(to3game.getGameServer().getRuleset()
				.equals(RulesetType.Hearts));
		assertFalse(to3game.getGameServer().hasPassword());
		assertFalse(to3game.isRemoveFlag());

		ComLobbyUpdateGamelist to4game = (ComLobbyUpdateGamelist) player4
				.getServerInput().get(1);
		assertTrue(to4game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to4game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to4game.getGameServer().getMaxPlayers() == 4);
		assertTrue(to4game.getGameServer().getCurrentPlayers() == 1);
		assertTrue(to4game.getGameServer().getRuleset()
				.equals(RulesetType.Hearts));
		assertFalse(to4game.getGameServer().hasPassword());
		assertFalse(to4game.isRemoveFlag());
	}

	@Test
	public void testJoinGame() {
		ComCreateGameRequest create = new ComCreateGameRequest("Markus' Spiel",
				RulesetType.Hearts, false, new String());
		player1.injectComObject(create);

		ComJoinRequest join = new ComJoinRequest("Markus", new String());
		player2.injectComObject(join);

		assertFalse(lobby.playerSet.contains(player1));
		assertTrue(lobby.getNames().contains(player1.getPlayerName()));

		List<String> playerList = new ArrayList<String>();
		ComInitGameLobby comInit = new ComInitGameLobby(playerList);
		assertTrue(player2.getServerInput().get(3).getClass()
				.equals(comInit.getClass()));

		ComInitGameLobby toPlayer2 = (ComInitGameLobby) player2
				.getServerInput().get(3);
		assertTrue(toPlayer2.getPlayerList().contains(player1.getPlayerName()));
		assertTrue(toPlayer2.getPlayerList().contains(player2.getPlayerName()));

		ComUpdatePlayerlist updatePlayer = new ComUpdatePlayerlist(null, true);
		assertTrue(player1.getServerInput().get(1).getClass()
				.equals(updatePlayer.getClass()));
		assertTrue(player3.getServerInput().get(3).getClass()
				.equals(updatePlayer.getClass()));
		assertTrue(player4.getServerInput().get(3).getClass()
				.equals(updatePlayer.getClass()));

		ComUpdatePlayerlist toPlayer1 = (ComUpdatePlayerlist) player1
				.getServerInput().get(1);
		assertTrue(toPlayer1.getPlayerName() == player2.getPlayerName());
		assertFalse(toPlayer1.isRemoveFlag());

		ComUpdatePlayerlist toPlayer3 = (ComUpdatePlayerlist) player3
				.getServerInput().get(3);
		assertTrue(toPlayer3.getPlayerName() == player2.getPlayerName());
		assertTrue(toPlayer3.isRemoveFlag());

		ComUpdatePlayerlist toPlayer4 = (ComUpdatePlayerlist) player4
				.getServerInput().get(3);
		assertTrue(toPlayer4.getPlayerName() == player2.getPlayerName());
		assertTrue(toPlayer4.isRemoveFlag());
		
		ComLobbyUpdateGamelist to3game = (ComLobbyUpdateGamelist) player3
				.getServerInput().get(4);
		assertTrue(to3game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to3game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to3game.getGameServer().getCurrentPlayers() == 2);
		assertFalse(to3game.isRemoveFlag());
		
		ComLobbyUpdateGamelist to4game = (ComLobbyUpdateGamelist) player4
				.getServerInput().get(4);
		assertTrue(to4game.getGameServer().getGameMasterName()
				.equals(player1.getPlayerName()));
		assertTrue(to4game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to4game.getGameServer().getCurrentPlayers() == 2);
		assertFalse(to4game.isRemoveFlag());
	}

	@Test
	public void testJoinGameWithPassword() {
		ComCreateGameRequest create = new ComCreateGameRequest("Markus' Spiel",
				RulesetType.Hearts, true, "Passwort");
		player1.injectComObject(create);

		ComJoinRequest join = new ComJoinRequest("Markus", "Passwort");
		player2.injectComObject(join);

		assertFalse(lobby.playerSet.contains(player2));
		assertTrue(lobby.getNames().contains(player2.getPlayerName()));

		List<String> playerList = new ArrayList<String>();
		ComInitGameLobby comInit = new ComInitGameLobby(playerList);
		assertTrue(player2.getServerInput().get(3).getClass()
				.equals(comInit.getClass()));

		ComInitGameLobby toPlayer2 = (ComInitGameLobby) player2
				.getServerInput().get(3);
		assertTrue(toPlayer2.getPlayerList().contains(player1.getPlayerName()));
		assertTrue(toPlayer2.getPlayerList().contains(player2.getPlayerName()));

		ComUpdatePlayerlist updatePlayer = new ComUpdatePlayerlist(null, true);
		assertTrue(player1.getServerInput().get(1).getClass()
				.equals(updatePlayer.getClass()));
		assertTrue(player3.getServerInput().get(3).getClass()
				.equals(updatePlayer.getClass()));
		assertTrue(player4.getServerInput().get(3).getClass()
				.equals(updatePlayer.getClass()));

		ComUpdatePlayerlist toPlayer1 = (ComUpdatePlayerlist) player1
				.getServerInput().get(1);
		assertTrue(toPlayer1.getPlayerName() == player2.getPlayerName());
		assertFalse(toPlayer1.isRemoveFlag());

		ComUpdatePlayerlist toPlayer3 = (ComUpdatePlayerlist) player3
				.getServerInput().get(3);
		assertTrue(toPlayer3.getPlayerName() == player2.getPlayerName());
		assertTrue(toPlayer3.isRemoveFlag());

		ComUpdatePlayerlist toPlayer4 = (ComUpdatePlayerlist) player4
				.getServerInput().get(3);
		assertTrue(toPlayer4.getPlayerName() == player2.getPlayerName());
		assertTrue(toPlayer4.isRemoveFlag());
	}

	@Test
	public void testJoinGameWithWrongPassword() {
		ComCreateGameRequest create = new ComCreateGameRequest("Markus' Spiel",
				RulesetType.Hearts, true, "Passwort");
		player1.injectComObject(create);

		ComJoinRequest join = new ComJoinRequest("Markus", "Hallo");
		player2.injectComObject(join);

		assertTrue(lobby.playerSet.contains(player2));

		ComWarning warning = new ComWarning(null);
		assertTrue(player2.getServerInput().get(3).getClass()
				.equals(warning.getClass()));
		ComWarning toPlayer2 = (ComWarning) player2.getServerInput().get(3);
		assertTrue(toPlayer2.getWarning().equals(WarningMsg.WrongPassword));
	}

	@Test
	public void testJoinFullGameHearts() {
		ComCreateGameRequest create = new ComCreateGameRequest("Markus' Spiel",
				RulesetType.Hearts, false, new String());
		player1.injectComObject(create);

		ComJoinRequest join = new ComJoinRequest("Markus", new String());
		player2.injectComObject(join);
		player3.injectComObject(join);
		player4.injectComObject(join);
		player5.injectComObject(join);

		ComWarning warning = new ComWarning(null);
		assertTrue(player5.getServerInput().get(9).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer5 = (ComWarning) player5.getServerInput().get(9);
		assertTrue(toPlayer5.getWarning().equals(WarningMsg.GameFull));
	}

	@Test
	public void testJoinFullGameWizard() {
		ComCreateGameRequest create = new ComCreateGameRequest("Markus' Spiel",
				RulesetType.Wizard, false, new String());
		player1.injectComObject(create);

		ComJoinRequest join = new ComJoinRequest("Markus", new String());
		player2.injectComObject(join);
		player3.injectComObject(join);
		player4.injectComObject(join);
		player5.injectComObject(join);
		player6.injectComObject(join);
		player7.injectComObject(join);

		ComWarning warning = new ComWarning(null);
		assertTrue(player7.getServerInput().get(13).getClass()
				.equals(warning.getClass()));

		ComWarning toPlayer7 = (ComWarning) player7.getServerInput().get(13);
		assertTrue(toPlayer7.getWarning().equals(WarningMsg.GameFull));
	}

	@Test
	public void testDisconnectPlayer() {
		lobby.disconnectPlayer(player1);
		assertFalse(lobby.playerSet.contains(player1));
	}
}
