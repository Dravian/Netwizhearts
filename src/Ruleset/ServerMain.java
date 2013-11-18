package Ruleset;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;

public class ServerMain {
	static TestPlayer blue;
	static TestGameServer gameServer;
	static TestLobbyServer lobbyServer;
	static PrintWizard wizard;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("Wieso?");
		lobbyServer = new TestLobbyServer();
		blue = new TestPlayer(lobbyServer, new Socket("127.0.0.1", 5001));
		
		
		gameServer = new TestGameServer(lobbyServer,blue,"Mein Spiel",RulesetType.Wizard, 
				"",false);
		wizard = new PrintWizard(gameServer);
		
		wizard.runGame();
	}

}
