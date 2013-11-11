package Server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import org.junit.Test;

import ComObjects.ComClientQuit;
import Ruleset.RulesetType;
import Server.GameServer;
import Server.LobbyServer;

import junit.framework.*;

public class QuitGameTest extends TestCase{

	public QuitGameTest(String name){
		super(name);
	}
	
	@Test
	public void testPlayerQuitGame() throws IOException{
		LobbyServer lobby = new LobbyServer(); 
		Player player1 = new Player(lobby, new ObjectOutputStream(System.out), new ObjectInputStream(new BufferedInputStream(System.in)));
		player1.setName("MrBlue");
		lobby.addPlayer(player1);
		GameServer game = new GameServer(lobby, player1, "MrBluesGame", RulesetType.Hearts, null, false);
		
		player1.changeServer(game);
		assertTrue(game.initLobby().getPlayerList().contains(player1.getName()));
		
		Player player2 = new Player(lobby, new ObjectOutputStream(System.out), new ObjectInputStream(new BufferedInputStream(System.in)));
		player2.setName("MrWhite");
		Player player3 = new Player(lobby, new ObjectOutputStream(System.out), new ObjectInputStream(new BufferedInputStream(System.in)));
		player3.setName("MrPink");
		Player player4 = new Player(lobby, new ObjectOutputStream(System.out), new ObjectInputStream(new BufferedInputStream(System.in)));
		player4.setName("MrRed");
		
		game.addPlayer(player2);
		game.addPlayer(player3);
		game.addPlayer(player4);
		
		ComClientQuit quit = new ComClientQuit();
		game.receiveMessage(player1, quit);

		assertFalse(lobby.initLobby().getGameList().contains(game));
		assertTrue(lobby.initLobby().getPlayerList().contains(player1.getName()));
		assertTrue(lobby.initLobby().getPlayerList().contains(player1.getName()));
		assertTrue(lobby.initLobby().getPlayerList().contains(player1.getName()));
		assertTrue(lobby.initLobby().getPlayerList().contains(player1.getName()));
	}
}
