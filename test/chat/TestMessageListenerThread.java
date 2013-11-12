package chat;

import java.net.Socket;

import Client.ClientModel;
import Client.MessageListenerThread;
import ComObjects.ComObject;

public class TestMessageListenerThread extends MessageListenerThread{

	ComObject inputComObject;
	
	ClientModel model;
	
	public void send(ComObject com) {
		inputComObject = com;
	}
	
	public void startConnection(ClientModel model, Socket connection) {
		this.model = model;
	}
	
	public void closeConnection() {
		
	}
	
	public ComObject getModelInput() {
		return inputComObject;
	}
	
	public void injectComObject(ComObject object) {
		object.process(model);
	}
	
	public void setModel(ClientModel model) {
		this.model = model;
	}
	
	public void run() {
		
	}
}
