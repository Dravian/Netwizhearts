package testKlassen;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ComObjects.ComObject;
import Server.Player;
import Server.Server;

public class TestPlayer extends Player {

	private Server server;
	
	private List<ComObject> inputComObjectList  = new ArrayList<ComObject>();
	
	public TestPlayer(Server lobbyServer, ObjectOutputStream output,
			ObjectInputStream input) {
		super(lobbyServer, output, input);
		this.server = lobbyServer;
	}

	public List<ComObject> getServerInput() {
		return inputComObjectList;
	}
	
	public void injectComObject(ComObject object) {
		object.process(this, server);
	}
	
	public void send(ComObject com) {
		inputComObjectList.add(com);
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public void run() {
		
	}
}
