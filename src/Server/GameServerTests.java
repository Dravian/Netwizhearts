package Server;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComChatMessage;
import ComObjects.ComClientLeave;
import ComObjects.ComInitLobby;
import ComObjects.ComJoinRequest;
import ComObjects.ComKickPlayerRequest;
import ComObjects.ComWarning;
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
		
		game = new GameServer(lobby, player1, "Markus' Spiel", RulesetType.Hearts, "", false);
		lobby.removePlayer(player1);
		player1.changeServer(game);
		lobby.addGameServer(game);
		
		ComJoinRequest join = new ComJoinRequest("Markus", "");
		player2.injectComObject(join);
		player3.injectComObject(join);
		player4.injectComObject(join);
		
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

	//GameLobbyTests
	@Test
	public void testChat(){
		ComChatMessage chat = new ComChatMessage("Hallo!");
		player1.injectComObject(chat);

		assertTrue(player1.getServerInput().get(3).getClass().equals(chat.getClass()));
		assertTrue(player2.getServerInput().get(3).getClass().equals(chat.getClass()));
		assertTrue(player3.getServerInput().get(4).getClass().equals(chat.getClass()));
		assertTrue(player4.getServerInput().get(5).getClass().equals(chat.getClass()));
		
		ComChatMessage toPlayer1 = (ComChatMessage) player1.getServerInput().get(3);
		assertTrue(toPlayer1.getChatMessage().equals("Hallo!"));
		
		ComChatMessage toPlayer2 = (ComChatMessage) player2.getServerInput().get(3);
		assertTrue(toPlayer2.getChatMessage().equals("Hallo!"));
		
		ComChatMessage toPlayer3 = (ComChatMessage) player3.getServerInput().get(4);
		assertTrue(toPlayer3.getChatMessage().equals("Hallo!"));
		
		ComChatMessage toPlayer4 = (ComChatMessage) player4.getServerInput().get(5);
		assertTrue(toPlayer4.getChatMessage().equals("Hallo!"));
	}
	
	@Test
	public void testKickPlayer(){
		ComKickPlayerRequest kick = new ComKickPlayerRequest("Klaus");
		player1.injectComObject(kick);
		assertTrue(lobby.playerSet.contains(player3));
		
		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player3.getServerInput().get(4).getClass().equals(init.getClass()));
		
		ComWarning warning = new ComWarning("");
		assertTrue(player3.getServerInput().get(5).getClass().equals(warning.getClass()));
		
		ComWarning toPlayer2 = (ComWarning) player3.getServerInput().get(5);
		assertTrue(toPlayer2.getWarning().equals("Kicked out of Game!"));
	}
	
	@Test
	public void testKickPlayerNotInGame(){
		ComKickPlayerRequest kick = new ComKickPlayerRequest("Peter");
		player1.injectComObject(kick);
		
		ComWarning warning = new ComWarning("");
		assertTrue(player1.getServerInput().get(3).getClass().equals(warning.getClass()));
		
		ComWarning toPlayer1 = (ComWarning) player1.getServerInput().get(3);
		assertTrue(toPlayer1.getWarning().equals("Couldn't kick player!"));
	}
	
	@Test
	public void testKickGameMaster(){
		ComKickPlayerRequest kick = new ComKickPlayerRequest("Markus");
		player1.injectComObject(kick);
		assertFalse(lobby.playerSet.contains(player1));
		
		ComWarning warning = new ComWarning("");
		assertTrue(player1.getServerInput().get(3).getClass().equals(warning.getClass()));
		
		ComWarning toPlayer1 = (ComWarning) player1.getServerInput().get(3);
		assertTrue(toPlayer1.getWarning().equals("Couldn't kick player!"));
	}
	
	@Test
	public void testClientLeave(){
		ComClientLeave leave = new ComClientLeave();
		player2.injectComObject(leave);
		assertTrue(lobby.playerSet.contains(player2));
		
		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player2.getServerInput().get(3).getClass().equals(init.getClass()));
	}
	
	@Test
	public void testGameMasterLeave(){
		ComClientLeave leave = new ComClientLeave();
		player1.injectComObject(leave);
		assertTrue(lobby.playerSet.contains(player1));
		assertTrue(lobby.playerSet.contains(player2));
		assertTrue(lobby.playerSet.contains(player3));
		assertTrue(lobby.playerSet.contains(player4));
		assertTrue(lobby.initLobby().getGameList().size() == 0);
		
		ComInitLobby init = new ComInitLobby(null, null);
		assertTrue(player1.getServerInput().get(3).getClass().equals(init.getClass()));
		assertTrue(player2.getServerInput().get(3).getClass().equals(init.getClass()));
		assertTrue(player3.getServerInput().get(4).getClass().equals(init.getClass()));
		assertTrue(player4.getServerInput().get(5).getClass().equals(init.getClass()));
		
		ComWarning warning = new ComWarning("");
		assertTrue(player1.getServerInput().get(4).getClass().equals(warning.getClass()));
		assertTrue(player2.getServerInput().get(4).getClass().equals(warning.getClass()));
		assertTrue(player3.getServerInput().get(5).getClass().equals(warning.getClass()));
		assertTrue(player4.getServerInput().get(6).getClass().equals(warning.getClass()));
		
		ComWarning toPlayer1 = (ComWarning) player1.getServerInput().get(4);
		assertTrue(toPlayer1.getWarning().equals("Game has been disbanded!"));

		ComWarning toPlayer2 = (ComWarning) player2.getServerInput().get(4);
		assertTrue(toPlayer2.getWarning().equals("Game has been disbanded!"));

		ComWarning toPlayer3 = (ComWarning) player3.getServerInput().get(5);
		assertTrue(toPlayer3.getWarning().equals("Game has been disbanded!"));

		ComWarning toPlayer4 = (ComWarning) player4.getServerInput().get(6);
		assertTrue(toPlayer4.getWarning().equals("Game has been disbanded!"));
	}
	
	@Test
	public void testDisconnectPlayer(){
		game.disconnectPlayer(player1);
		assertFalse(lobby.playerSet.contains(player1));
		assertFalse(lobby.getNames().contains(player1));
		
		assertTrue(lobby.playerSet.contains(player2));
		assertTrue(lobby.playerSet.contains(player3));
		assertTrue(lobby.playerSet.contains(player4));
		
		assertTrue(lobby.initLobby().getGameList().size() == 0);
		
		ComWarning warning = new ComWarning("");
		assertTrue(player2.getServerInput().get(4).getClass().equals(warning.getClass()));
		assertTrue(player3.getServerInput().get(5).getClass().equals(warning.getClass()));
		assertTrue(player4.getServerInput().get(6).getClass().equals(warning.getClass()));
		
		ComWarning toPlayer2 = (ComWarning) player2.getServerInput().get(4);
		assertTrue(toPlayer2.getWarning().equals("Game Disbanded!"));
		
		ComWarning toPlayer3 = (ComWarning) player3.getServerInput().get(5);
		assertTrue(toPlayer3.getWarning().equals("Game Disbanded!"));
		
		ComWarning toPlayer4 = (ComWarning) player4.getServerInput().get(6);
		assertTrue(toPlayer4.getWarning().equals("Game Disbanded!"));		
	}
	
	//GameTests
}
