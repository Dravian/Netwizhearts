package chat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ComObjects.ComObject;
import Server.Player;
import Server.Server;

public class TestPlayer extends Player {

	private Server server;
	
	private ComObject inputComObject;
	
	public TestPlayer(Server server, ObjectOutputStream output,
			ObjectInputStream input) {
		
		this.server = server;
	}
	
	public ComObject getServerInput() {
		return inputComObject;
	}
	
	public void injectComObject(ComObject object) {
		object.process(this, server);
	}
	
	public void send(ComObject com) {
		inputComObject = com;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public void run() {
		
	}
}
