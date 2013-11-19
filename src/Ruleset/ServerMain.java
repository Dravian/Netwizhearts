package Ruleset;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import ComObjects.ComRuleset;
import ComObjects.RulesetMessage;

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
		lobbyServer = new TestLobbyServer();
		
		blue = new TestPlayer(lobbyServer);
		
		
		gameServer = new TestGameServer(lobbyServer,blue,"Mein Spiel",RulesetType.Wizard, 
				"",false);
		wizard = new PrintWizard(gameServer);
		RulesetMessage message =((ComRuleset)blue.getServerInput().get(0)).getRulesetMessage();
		(MsgUser)message.getGameClientUpdate
		wizard.runGame();
	}

}
