package Server;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComClientQuit;
import ComObjects.ComUpdatePlayerlist;
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
		
		
		ComUpdatePlayerlist fromPlayer2 = (ComUpdatePlayerlist) player2.getServerInput().get(0);
		assertTrue(fromPlayer2.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(fromPlayer2.isRemoveFlag());
		
		ComUpdatePlayerlist fromPlayer3 = (ComUpdatePlayerlist) player3.getServerInput().get(0);
		assertTrue(fromPlayer3.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(fromPlayer3.isRemoveFlag());
		
		ComUpdatePlayerlist fromPlayer4 = (ComUpdatePlayerlist) player4.getServerInput().get(0);
		assertTrue(fromPlayer4.getPlayerName().equals(player1.getPlayerName()));
		assertTrue(fromPlayer4.isRemoveFlag());
	}
}
