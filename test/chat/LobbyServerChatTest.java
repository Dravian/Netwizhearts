package chat;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testKlassen.TestPlayer;
import Server.LobbyServer;
import ComObjects.ComChatMessage;

public class LobbyServerChatTest {

	ComChatMessage testMessage;
	
	LobbyServer testServer;

	TestPlayer player1;
	
	TestPlayer player2;
	
	TestPlayer player3;
	
	String testText1;
	
	String testText2;
	
	String testText3;

	@Before
	public void setUp() {
		testMessage = new ComChatMessage("Hello Test!");
		testServer = new LobbyServer();
		player1 = new TestPlayer(testServer, null, null);
		player2 = new TestPlayer(testServer, null, null);
		player3 = new TestPlayer(testServer, null, null);
	}

	@After
	public void tearDown() {
		testMessage = null;
		testServer = null;
		player1 = null;
		player2 = null;
		player3 = null;
		testText1 = null;
		testText2 = null;
		testText3 = null;
	}

	@Test
	public void testReceiveMessagePlayerComChatMessage() {
		String messageToMatch = testMessage.getChatMessage();
		testServer.addPlayer(player1);
		testServer.addPlayer(player2);
		testServer.addPlayer(player3);
		player1.injectComObject(testMessage);
		testText1 = ((ComChatMessage) player1.getServerInput().get(0)).getChatMessage();
		testText2 = ((ComChatMessage) player2.getServerInput().get(0)).getChatMessage();
		testText3 = ((ComChatMessage) player3.getServerInput().get(0)).getChatMessage();
		assertEquals("Nachricht an Spieler 1", messageToMatch, testText1);
		assertEquals("Nachricht an Spieler 2", messageToMatch, testText2);
		assertEquals("Nachricht an Spieler 3", messageToMatch, testText3);
	}
}
