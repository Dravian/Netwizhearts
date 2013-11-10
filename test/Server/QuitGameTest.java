package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


import org.junit.Test;

import ComObjects.ComClientQuit;
import Ruleset.RulesetType;

import junit.framework.*;

public class QuitGameTest extends TestCase{

	public QuitGameTest(String name){
		super(name);
	}
	@Test
	public void testPlayerQuitGame() throws IOException{		
		LobbyServer lobby = new LobbyServer(); 
		Player player1 = new Player(lobby, new PrintWriter(System.out, true), new BufferedReader(new InputStreamReader(System.in)));
		player1.setName("MrBlue");
		lobby.addPlayer(player1);
		GameServer game = new GameServer(lobby, player1, "MrBluesGame", RulesetType.Hearts, null, false);
		
		player1.changeServer(game);
		assertTrue(game.playerSet.contains(player1));
		
		Player player2 = new Player(game, new PrintWriter(System.out, true), new BufferedReader(new InputStreamReader(System.in)));
		player2.setName("MrWhite");
		Player player3 = new Player(game, new PrintWriter(System.out, true), new BufferedReader(new InputStreamReader(System.in)));
		player3.setName("MrPink");
		Player player4 = new Player(game, new PrintWriter(System.out, true), new BufferedReader(new InputStreamReader(System.in)));
		player4.setName("MrRed");
		
		game.addPlayer(player2);
		game.addPlayer(player3);
		game.addPlayer(player4);
		
		ComClientQuit quit = new ComClientQuit();
		game.receiveMessage(player1, quit);

		assertFalse(lobby.getGameServerSet().contains(game));
		assertTrue(lobby.playerSet.contains(player1));
		assertTrue(lobby.playerSet.contains(player2));
		assertTrue(lobby.playerSet.contains(player3));
		assertTrue(lobby.playerSet.contains(player4));
	}
}
