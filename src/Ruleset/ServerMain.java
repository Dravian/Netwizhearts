package Ruleset;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import ComObjects.ComRuleset;
import ComObjects.MsgUser;
import ComObjects.RulesetMessage;

import test.TestGameServer;
import test.TestLobbyServer;
import test.TestPlayer;

public class ServerMain {
	static TestPlayer blue;
	static TestPlayer red;
	static TestPlayer green;
	static TestGameServer gameServer;
	static TestLobbyServer lobbyServer;
	static PrintWizard wizard;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException{
		lobbyServer = new TestLobbyServer();
		
		blue = new TestPlayer(lobbyServer);	
		blue.setPlayerName("Blue");
		red = new TestPlayer(lobbyServer);	
		red.setPlayerName("Red");
		green = new TestPlayer(lobbyServer);	
		green.setPlayerName("Green");
		
		gameServer = new TestGameServer(lobbyServer, "Blue" ,"Mein Spiel",RulesetType.Wizard, 
				"",false);
		wizard = new PrintWizard(gameServer);
		wizard.runGame();
		wizard.calculateRoundOutcome();
	}

}
