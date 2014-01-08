package Server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Ruleset.RulesetType;

import ComObjects.ComChatMessage;
import ComObjects.ComClientLeave;
import ComObjects.ComCreateGameRequest;
import ComObjects.ComJoinRequest;
import ComObjects.ComKickPlayerRequest;
import ComObjects.ComStartGame;

import test.TestPlayer;

public class GameServerDurabilityTest100 {

	LobbyServer lobby;
	List<TestPlayer> playersA = new ArrayList<TestPlayer>();
	List<TestPlayer> playersB= new ArrayList<TestPlayer>();
	List<TestPlayer> playersC = new ArrayList<TestPlayer>();
	
	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();
		for (int i=0; i<100; i++){
			TestPlayer player = new TestPlayer(lobby);
			player.setPlayerName(i +"a");
			player.setServer(lobby);
			playersA.add(player);
			lobby.addPlayer(player);
			lobby.addName(player.getPlayerName());
		}
		for (int i=0; i<100; i++){
			TestPlayer player = new TestPlayer(lobby);
			player.setPlayerName(i +"b");
			player.setServer(lobby);
			playersB.add(player);
			lobby.addPlayer(player);
			lobby.addName(player.getPlayerName());
		}
		for (int i=0; i<100; i++){
			TestPlayer player = new TestPlayer(lobby);
			player.setPlayerName(i +"c");
			player.setServer(lobby);
			playersC.add(player);
			lobby.addPlayer(player);
			lobby.addName(player.getPlayerName());
		}
		
		for (TestPlayer player : playersC) {
			player.injectComObject(new ComCreateGameRequest(player.getPlayerName(), RulesetType.Wizard, false, new String()));						
		}
		for (int i=0; i<100; i++){
			playersA.get(i).injectComObject(new ComJoinRequest(i +"c", new String()));
		}
		for (int i=0; i<100; i++){
			playersB.get(i).injectComObject(new ComJoinRequest(i +"c", new String()));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		lobby = null;
	}
	
	@Test
	public void testChat() {
		for (int i=0; i<100; i++){
			playersB.get(i).injectComObject(new ComChatMessage("Hi"));
		}
	}
	
	@Test
	public void test300Chat() {
		for (int i=0; i<100; i++){
			playersA.get(i).injectComObject(new ComChatMessage("Hi"));
		}
		for (int i=0; i<100; i++){
			playersB.get(i).injectComObject(new ComChatMessage("Hi"));
		}
		for (int i=0; i<100; i++){
			playersC.get(i).injectComObject(new ComChatMessage("Hi"));
		}
	}
	
	@Test
	public void test100KickPlayer() {
		for (int i=0; i<100; i++){
			playersC.get(i).injectComObject(new ComKickPlayerRequest(i + "a"));
		}
	}
	
	@Test
	public void test100GameMasterLeave() {
		for (int i=0; i<100; i++){
			playersC.get(i).injectComObject(new ComClientLeave());
		}
	}
	
	@Test
	public void test100StartGame() {
		for (int i=0; i<100; i++){
			playersC.get(i).injectComObject(new ComStartGame());
		}
	}
}
