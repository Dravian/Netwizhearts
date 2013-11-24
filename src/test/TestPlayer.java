package test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ComObjects.ComObject;
import ComObjects.ComRuleset;
import ComObjects.RulesetMessage;
import Server.Player;
import Server.Server;

public class TestPlayer extends Player {
	private List<ComObject> inputComObject;
	
	public TestPlayer(Server lobbyServer) {
		super(lobbyServer);
		server = lobbyServer;
		inputComObject = new ArrayList<ComObject>();
	}

	public List<ComObject> getServerInput() {
		return inputComObject;
	}
	
	public void injectComObject(ComObject object) {
		object.process(this, server);
	}
	
	public void send(ComObject com) {
		//System.out.println(((ComRuleset) com).getRulesetMessage().getClass()
		//		+ getPlayerName());
		inputComObject.add(com);
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public void closeConnection(){
		
	}
	
	public void run() {
		
	}
}
