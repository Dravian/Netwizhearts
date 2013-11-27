package Server;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ComObjects.ComClientQuit;
import ComObjects.ComLoginRequest;

import test.TestPlayer;

public class LoginTest {
	
	TestPlayer player;
	LoginServer login;
	LobbyServer lobby;

	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();
		login = new LoginServer(lobby);		
		player = new TestPlayer(login);
		player.setServer(login);
		login.addPlayer(player);
	}

	@After
	public void tearDown() throws Exception {
		lobby = null;
		login = null;		
		player = null;
	}
	
	@Test
	public void testLogin(){
		ComLoginRequest login = new ComLoginRequest("Klaus");
		player.injectComObject(login);
		assertTrue(player.getServerInput().get(0).getClass().equals(lobby.initLobby().getClass()));
		assertTrue(lobby.playerSet.contains(player));
		assertTrue(lobby.initLobby().getPlayerList().contains(player.getPlayerName()));
		assertTrue(lobby.getNames().contains(player.getPlayerName()));
	}
}
