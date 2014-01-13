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

import test.MockPlayer;

public class GameServerDurabilityTest500 {

	LobbyServer lobby;
	List<MockPlayer> playersA = new ArrayList<MockPlayer>();
	List<MockPlayer> playersB= new ArrayList<MockPlayer>();
	List<MockPlayer> playersC = new ArrayList<MockPlayer>();
	
	@Before
	public void setUp() throws Exception {
		lobby = new LobbyServer();
		for (int i=0; i<500; i++){
			MockPlayer player = new MockPlayer(lobby);
			player.setPlayerName(i +"a");
			player.setServer(lobby);
			playersA.add(player);
			lobby.addPlayer(player);
			lobby.addName(player.getPlayerName());
		}
		for (int i=0; i<500; i++){
			MockPlayer player = new MockPlayer(lobby);
			player.setPlayerName(i +"b");
			player.setServer(lobby);
			playersB.add(player);
			lobby.addPlayer(player);
			lobby.addName(player.getPlayerName());
		}
		for (int i=0; i<500; i++){
			MockPlayer player = new MockPlayer(lobby);
			player.setPlayerName(i +"c");
			player.setServer(lobby);
			playersC.add(player);
			lobby.addPlayer(player);
			lobby.addName(player.getPlayerName());
		}
		
		for (MockPlayer player : playersC) {
			player.injectComObject(new ComCreateGameRequest(player.getPlayerName(), RulesetType.Wizard, false, new String()));						
		}
		for (int i=0; i<500; i++){
			playersA.get(i).injectComObject(new ComJoinRequest(i +"c", new String()));
		}
		for (int i=0; i<500; i++){
			playersB.get(i).injectComObject(new ComJoinRequest(i +"c", new String()));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		lobby = null;
	}
	
	@Test
	public void testChat() {
		for (int i=0; i<500; i++){
			playersB.get(i).injectComObject(new ComChatMessage("Hi"));
		}
	}
	
	@Test
	public void test1500Chat() {
		for (int i=0; i<500; i++){
			playersA.get(i).injectComObject(new ComChatMessage("Hi"));
		}
		for (int i=0; i<500; i++){
			playersB.get(i).injectComObject(new ComChatMessage("Hi"));
		}
		for (int i=0; i<500; i++){
			playersC.get(i).injectComObject(new ComChatMessage("Hi"));
		}
	}
	
	@Test
	public void test500KickPlayer() {
		for (int i=0; i<500; i++){
			playersC.get(i).injectComObject(new ComKickPlayerRequest(i + "a"));
		}
	}
	
	@Test
	public void test500GameMasterLeave() {
		for (int i=0; i<500; i++){
			playersC.get(i).injectComObject(new ComClientLeave());
		}
	}
	
	@Test
	public void test500StartGame() {
		for (int i=0; i<500; i++){
			playersC.get(i).injectComObject(new ComStartGame());
		}
	}
}
