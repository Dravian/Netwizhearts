package test;

import java.net.Socket;
import java.util.List;

import ComObjects.ComObject;
import Server.Player;
import Server.Server;

public class TestPlayer extends Player {
	
	public TestPlayer(Server lobbyServer, Socket socket) {
		super(lobbyServer, socket);
	}
	private Server server;
	
	private List<ComObject> inputComObject;

	public List<ComObject> getServerInput() {
		return inputComObject;
	}
	
	public void injectComObject(ComObject object) {
		object.process(this, server);
	}
	
	public void send(ComObject com) {
		inputComObject.add(com);
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public void run() {
		
	}
}
