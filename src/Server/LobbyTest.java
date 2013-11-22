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
import Ruleset.RulesetType;
import test.TestPlayer;

public class LobbyTest {
	
	LobbyServer lobby;
	TestPlayer player1;
	TestPlayer player2;
	TestPlayer player3;
	TestPlayer player4;

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
		
		lobby.addPlayer(player1);
		lobby.addName(player1.getPlayerName());	
		lobby.addPlayer(player2);
		lobby.addName(player2.getPlayerName());
		lobby.addPlayer(player3);
		lobby.addName(player3.getPlayerName());
		lobby.addPlayer(player4);
		lobby.addName(player4.getPlayerName());	
	}

	@After
	public void tearDown() throws Exception {
		lobby = null;
		player1 = null;
		player2 = null;
		player3 = null;
		player4 = null;
	}

	@Test
	public void testClientQuit(){
		ComClientQuit quit = new ComClientQuit();
		player1.injectComObject(quit);
		assertFalse(lobby.playerSet.contains(player1));
		assertFalse(lobby.getNames().contains(player1.getPlayerName()));
		
		ComUpdatePlayerlist update = new ComUpdatePlayerlist(player1.getName(), true);
		assertTrue(player2.getServerInput().get(0).getClass().equals(update.getClass()));
		assertTrue(player3.getServerInput().get(0).getClass().equals(update.getClass()));
		assertTrue(player4.getServerInput().get(0).getClass().equals(update.getClass()));
		
		
		ComUpdatePlayerlist toPlayer2 = (ComUpdatePlayerlist) player2.getServerInput().get(0);
		assertTrue(toPlayer2.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(toPlayer2.isRemoveFlag());
		
		ComUpdatePlayerlist toPlayer3 = (ComUpdatePlayerlist) player3.getServerInput().get(0);
		assertTrue(toPlayer3.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(toPlayer3.isRemoveFlag());
		
		ComUpdatePlayerlist toPlayer4 = (ComUpdatePlayerlist) player4.getServerInput().get(0);
		assertTrue(toPlayer4.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(toPlayer4.isRemoveFlag());
	}
	
	@Test
	public void testChat(){
		ComChatMessage chat = new ComChatMessage("Hallo!");
		player1.injectComObject(chat);
		assertTrue(player1.getServerInput().get(0).getClass().equals(chat.getClass()));
		assertTrue(player2.getServerInput().get(0).getClass().equals(chat.getClass()));
		assertTrue(player3.getServerInput().get(0).getClass().equals(chat.getClass()));
		assertTrue(player4.getServerInput().get(0).getClass().equals(chat.getClass()));
		
		ComChatMessage toPlayer1 = (ComChatMessage) player2.getServerInput().get(0);
		assertTrue(toPlayer1.getChatMessage().equals("Hallo!"));
		
		ComChatMessage toPlayer2 = (ComChatMessage) player2.getServerInput().get(0);
		assertTrue(toPlayer2.getChatMessage().equals("Hallo!"));
		
		ComChatMessage toPlayer3 = (ComChatMessage) player3.getServerInput().get(0);
		assertTrue(toPlayer3.getChatMessage().equals("Hallo!"));
		
		ComChatMessage toPlayer4 = (ComChatMessage) player4.getServerInput().get(0);
		assertTrue(toPlayer4.getChatMessage().equals("Hallo!"));
	}
	
	@Test
	public void testCreateGame(){
		ComCreateGameRequest create = new ComCreateGameRequest("Markus' Spiel", RulesetType.Hearts, false, null);
		player1.injectComObject(create);
		
		assertFalse(lobby.playerSet.contains(player1));
		assertTrue(lobby.getNames().contains(player1.getPlayerName()));
		
		List<String> playerList = new ArrayList<String>();
		ComInitGameLobby comInit = new ComInitGameLobby(playerList);
		assertTrue(player1.getServerInput().get(1).getClass().equals(comInit.getClass()));
		
		ComInitGameLobby toPlayer1 = (ComInitGameLobby) player1.getServerInput().get(1);
		assertTrue(toPlayer1.getPlayerList().get(0) ==  player1.getPlayerName());
		
		ComUpdatePlayerlist updatePlayer = new ComUpdatePlayerlist(player1.getName(), true);
		assertTrue(player2.getServerInput().get(0).getClass().equals(updatePlayer.getClass()));
		assertTrue(player3.getServerInput().get(0).getClass().equals(updatePlayer.getClass()));
		assertTrue(player4.getServerInput().get(0).getClass().equals(updatePlayer.getClass()));
		
		
		ComUpdatePlayerlist to2player = (ComUpdatePlayerlist) player2.getServerInput().get(0);
		assertTrue(to2player.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(to2player.isRemoveFlag());
		
		ComUpdatePlayerlist to3player = (ComUpdatePlayerlist) player3.getServerInput().get(0);
		assertTrue(to3player.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(to3player.isRemoveFlag());
		
		ComUpdatePlayerlist to4player = (ComUpdatePlayerlist) player4.getServerInput().get(0);
		assertTrue(to4player.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(to4player.isRemoveFlag());
		
		ComLobbyUpdateGamelist updateGame = new ComLobbyUpdateGamelist(false, new GameServerRepresentation(player1.getPlayerName(), "Markus' Spiel", 4, 1, RulesetType.Hearts, false));
		assertTrue(player2.getServerInput().get(1).getClass().equals(updateGame.getClass()));
		assertTrue(player3.getServerInput().get(1).getClass().equals(updateGame.getClass()));
		assertTrue(player4.getServerInput().get(1).getClass().equals(updateGame.getClass()));
		
		
		ComLobbyUpdateGamelist to2game = (ComLobbyUpdateGamelist) player2.getServerInput().get(1);
		assertTrue(to2game.getGameServer().getGameMasterName().equals(player1.getPlayerName()));
		assertTrue(to2game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to2game.getGameServer().getMaxPlayers() == 4);
		assertTrue(to2game.getGameServer().getCurrentPlayers() == 1);
		assertTrue(to2game.getGameServer().getRuleset().equals(RulesetType.Hearts));
		assertFalse(to2game.getGameServer().hasPassword());
		assertFalse(to2game.isRemoveFlag());
		
		ComLobbyUpdateGamelist to3game = (ComLobbyUpdateGamelist) player3.getServerInput().get(1);
		assertTrue(to3game.getGameServer().getGameMasterName().equals(player1.getPlayerName()));
		assertTrue(to3game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to3game.getGameServer().getMaxPlayers() == 4);
		assertTrue(to3game.getGameServer().getCurrentPlayers() == 1);
		assertTrue(to3game.getGameServer().getRuleset().equals(RulesetType.Hearts));
		assertFalse(to3game.getGameServer().hasPassword());
		assertFalse(to3game.isRemoveFlag());
		
		ComLobbyUpdateGamelist to4game = (ComLobbyUpdateGamelist) player4.getServerInput().get(1);
		assertTrue(to4game.getGameServer().getGameMasterName().equals(player1.getPlayerName()));
		assertTrue(to4game.getGameServer().getName().equals("Markus' Spiel"));
		assertTrue(to4game.getGameServer().getMaxPlayers() == 4);
		assertTrue(to4game.getGameServer().getCurrentPlayers() == 1);
		assertTrue(to4game.getGameServer().getRuleset().equals(RulesetType.Hearts));
		assertFalse(to4game.getGameServer().hasPassword());
		assertFalse(to4game.isRemoveFlag());
	}
	
	@Test
	public void testJoinGame(){
		ComCreateGameRequest create = new ComCreateGameRequest("Markus' Spiel", RulesetType.Hearts, false, null);
		player1.injectComObject(create);
		
		ComJoinRequest join = new ComJoinRequest("Markus", null);
		player2.injectComObject(join);
		
		assertFalse(lobby.playerSet.contains(player1));
		assertTrue(lobby.getNames().contains(player1.getPlayerName()));
		
		List<String> playerList = new ArrayList<String>();
		ComInitGameLobby comInit = new ComInitGameLobby(playerList);
		assertTrue(player2.getServerInput().get(2).getClass().equals(comInit.getClass()));
		
		ComInitGameLobby toPlayer2 = (ComInitGameLobby) player2.getServerInput().get(2);
		assertTrue(toPlayer2.getPlayerList().contains(player1.getPlayerName()));
		assertTrue(toPlayer2.getPlayerList().contains(player2.getPlayerName()));
		
		ComUpdatePlayerlist updatePlayer = new ComUpdatePlayerlist(null, true);
		assertTrue(player1.getServerInput().get(2).getClass().equals(updatePlayer.getClass()));	
		assertTrue(player3.getServerInput().get(2).getClass().equals(updatePlayer.getClass()));
		assertTrue(player4.getServerInput().get(2).getClass().equals(updatePlayer.getClass()));
		
		ComUpdatePlayerlist toPlayer1 = (ComUpdatePlayerlist) player1.getServerInput().get(2);
		assertTrue(toPlayer1.getPlayerName() == player2.getPlayerName());
		assertFalse(toPlayer1.isRemoveFlag());
		
		ComUpdatePlayerlist toPlayer3 = (ComUpdatePlayerlist) player3.getServerInput().get(2);
		assertTrue(toPlayer3.getPlayerName() == player2.getPlayerName());
		assertTrue(toPlayer3.isRemoveFlag());
		
		ComUpdatePlayerlist toPlayer4 = (ComUpdatePlayerlist) player4.getServerInput().get(2);
		assertTrue(toPlayer4.getPlayerName() == player2.getPlayerName());
		assertTrue(toPlayer4.isRemoveFlag());		
	}
}
