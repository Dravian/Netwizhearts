package Server;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComInitLobby;
import ComObjects.ComLoginRequest;
import ComObjects.ComWarning;

public class receiveTest {

	Server lobby;
	Player player;
	Socket socket;
	
	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();
		socket = new Socket();
		player = new Player(lobby, socket);
	}

	@After
	public void tearDown() throws Exception {
		lobby = null;
		player = null;
	}
	
	@Test
	public void testPlayerQuitGame() throws IOException{ 
		ComLoginRequest com = new ComLoginRequest("Mark");
		lobby.receiveMessage(player, com);
		assertTrue(lobby.playerSet.size() == 1);
	}

}
