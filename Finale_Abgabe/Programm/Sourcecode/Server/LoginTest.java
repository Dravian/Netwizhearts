package Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ComObjects.ComLoginRequest;

import test.MockPlayer;

public class LoginTest {

	MockPlayer player;
	LoginServer login;
	LobbyServer lobby;

	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();
		login = new LoginServer(lobby, 2222);
		player = new MockPlayer(login);
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
	public void testLogin() {
		ComLoginRequest login = new ComLoginRequest("Klaus");
		player.injectComObject(login);
		assertTrue(player.getServerInput().get(0).getClass()
				.equals(lobby.initLobby().getClass()));
		assertTrue(lobby.playerSet.contains(player));
		assertTrue(lobby.initLobby().getPlayerList()
				.contains(player.getPlayerName()));
		assertTrue(lobby.getNames().contains(player.getPlayerName()));
	}
}
