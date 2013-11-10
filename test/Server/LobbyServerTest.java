package Server;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ComObjects.ComChatMessage;

public class LobbyServerTest {

	ComChatMessage testMessage;
	
	LobbyServer testServer;

	@Mock
	Player player1;
	
	@Mock
	Player player2;
	
	@Mock
	Player player3;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		testMessage = new ComChatMessage("Hello Test!");
		testServer = new LobbyServer();
	}

	@After
	public void tearDown() {
		testMessage = null;
		testServer = null;
		player1 = null;
		player2 = null;
		player3 = null;
	}

	@Test
	public void testReceiveMessagePlayerComChatMessage() throws IOException {
		testServer.addPlayer(player1);
		testServer.addPlayer(player2);
		testServer.addPlayer(player3);
		testMessage.process(player1, testServer);
		Mockito.verify(player1).send(testMessage);
		Mockito.verify(player2).send(testMessage);
		Mockito.verify(player3).send(testMessage);
	}
}
