package Server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.MockPlayer;

import ComObjects.ComLoginRequest;
import ComObjects.ComWarning;
import ComObjects.WarningMsg;

public class LoginTestNegativ {

	MockPlayer player;
	MockPlayer player2;
	LoginServer login;
	LobbyServer lobby;

	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();
		login = new LoginServer(lobby, 1234);
		player = new MockPlayer(login);
		player.setServer(login);
		player2 = new MockPlayer(login);
		player2.setServer(login);
		login.addPlayer(player);
		login.addPlayer(player2);
	}

	@After
	public void tearDown() throws Exception {
		lobby = null;
		login = null;
		player = null;
		player2 = null;
	}

	@Test
	public void testLoginUsedName() {
		ComLoginRequest login = new ComLoginRequest("Klaus");
		player.injectComObject(login);
		player2.injectComObject(login);

		ComWarning warning = new ComWarning(null);
		assertTrue(player2.getServerInput().get(0).getClass()
				.equals(warning.getClass()));
		ComWarning playerWarning = (ComWarning) player2.getServerInput().get(0);
		assertTrue(playerWarning.getWarning().equals(WarningMsg.LoginError));
		assertFalse(lobby.playerSet.contains(player2));
		assertFalse(lobby.getNames().contains(player2.getPlayerName()));
	}
}
