package Server;


import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;

import ComObjects.*;
import ComObjects.ComWarning;
import Ruleset.RulesetType;

public class QuitGameTest {

	TestLobbyServer lobby;
	
	TestPlayer player1; 
	
	TestPlayer player2; 
	TestPlayer player3;
	TestPlayer player4;
	
	TestGameServer game;
	
	ComClientQuit quit;
	
	@Before
	public void setUp() throws Exception {
		lobby = new TestLobbyServer();
		
		player1 = new TestPlayer(lobby, null, null);
		player1.setName("MrBlue");
		lobby.addPlayer(player1);
		
		player2 = new TestPlayer(lobby, null, null);
		player2.setName("MrWhite");
		player3 = new TestPlayer(lobby, null, null);
		player3.setName("MrPink");
		player4 = new TestPlayer(lobby, null, null);
		player4.setName("MrRed");
		
		game = new TestGameServer(lobby, player1, "MrBluesGame", RulesetType.Hearts, null, false);
		
		game.addPlayer(player2);
		game.addPlayer(player3);
		game.addPlayer(player4);
		
		quit = new ComClientQuit();
	}

	@After
	public void tearDown() throws Exception {
		lobby = null;
		player1 = null;
		player2 = null;
		player3 = null;
		player4 = null;
		game = null;
	}
	
	@Test
	public void testPlayerQuitGame() throws IOException{ 
		
		player1.changeServer(game);
		assertTrue(game.initLobby().getPlayerList().contains(player1.getName()));
		
		player1.injectComObject(quit);

		assertFalse(lobby.initLobby().getGameList().contains(game));
		assertTrue(lobby.initLobby().getPlayerList().contains(player1.getName()));
		assertTrue(lobby.initLobby().getPlayerList().contains(player1.getName()));
		assertTrue(lobby.initLobby().getPlayerList().contains(player1.getName()));
		assertTrue(lobby.initLobby().getPlayerList().contains(player1.getName()));
		ComInitLobby initLobby = lobby.initLobby();
		ComWarning warning = new ComWarning("Ein Spieler hat das Spiel verlassen");
		assertTrue(player1.getServerInput().get(0).getClass() == initLobby.getClass());
		assertTrue(player1.getServerInput().get(1).getClass() == warning.getClass());
		assertTrue(player2.getServerInput().get(0).getClass() == initLobby.getClass());
		assertTrue(player2.getServerInput().get(1).getClass() == warning.getClass());
		assertTrue(player3.getServerInput().get(0).getClass() == initLobby.getClass());
		assertTrue(player3.getServerInput().get(1).getClass() == warning.getClass());
		assertTrue(player4.getServerInput().get(0).getClass() == initLobby.getClass());
		assertTrue(player4.getServerInput().get(1).getClass() == warning.getClass());
	}
}
