package Server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ComObjects.ComChatMessage;
import ComObjects.ComClientQuit;
import ComObjects.ComCreateGameRequest;
import ComObjects.ComJoinRequest;
import Ruleset.RulesetType;

import test.MockPlayer;

public class LobbyDurabilityTest2000 {
	
	LobbyServer lobby;
	List<MockPlayer> players = new ArrayList<MockPlayer>();
	List<MockPlayer> playersCreate = new ArrayList<MockPlayer>();
	
	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();
		for (int i=0; i<2000; i++){
			MockPlayer player = new MockPlayer(lobby);
			player.setPlayerName(i +" ");
			player.setServer(lobby);
			players.add(player);
			lobby.addPlayer(player);
			lobby.addName(player.getPlayerName());
		}
	}
	
	@After
	public void tearDown() throws Exception {
		lobby = null;
	}
	
	@Test
	public void testChat() {		
		players.get(0).injectComObject(new ComChatMessage("Hi"));
	}
	
	@Test
	public void test2000Chat() {		
		for (MockPlayer player : players) {
			player.injectComObject(new ComChatMessage("Hi"));						
		}
	}
	
	@Test
	public void test2000CreateGame() {
		for (MockPlayer player : players) {
			player.injectComObject(new ComCreateGameRequest("", RulesetType.Wizard, false, new String()));						
		}
	}
	
	@Test
	public void test2000JoinGame() {
		for (int i=0; i<2000; i++){
			MockPlayer player = new MockPlayer(lobby);
			player.setPlayerName(i +"a");
			player.setServer(lobby);
			playersCreate.add(player);
			lobby.addPlayer(player);
			lobby.addName(player.getPlayerName());
		}
		for (MockPlayer player : playersCreate) {
			player.injectComObject(new ComCreateGameRequest(player.getPlayerName(), RulesetType.Wizard, false, new String()));						
		}
		for (int i=0; i<2000; i++){
			players.get(i).injectComObject(new ComJoinRequest(i +"a", new String()));
		}
	}
	
	@Test
	public void test2000Quit() {
		for (MockPlayer player : players) {
			player.injectComObject(new ComClientQuit());						
		}
	}
}
