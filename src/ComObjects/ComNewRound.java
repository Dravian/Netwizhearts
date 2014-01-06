package ComObjects;

import java.io.Serializable;

import Client.ClientModel;
import Server.Player;
import Server.Server;

public class ComNewRound implements ComObject, Serializable{

	private boolean newRound;

	public ComNewRound(boolean result) {
		this.newRound = result;
	}

	public boolean getResult() {
		return newRound;
	}

	@Override
	public void process(ClientModel model) {
		// model.receiveMessage(this);
		throw new UnsupportedOperationException();
	}

	@Override
	public void process(Player player, Server server) {
		server.receiveMessage(player, this);
	}

}
