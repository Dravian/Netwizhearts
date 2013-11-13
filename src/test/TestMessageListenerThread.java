package test;

import java.net.Socket;
import java.util.List;

import Client.ClientModel;
import Client.MessageListenerThread;
import ComObjects.ComObject;

public class TestMessageListenerThread extends MessageListenerThread{

	List<ComObject> inputComObject;
	
	ClientModel model;
	
	public void send(ComObject com) {
		inputComObject.add(com);
	}
	
	public void startConnection(ClientModel model, Socket connection) {
		this.model = model;
	}
	
	public void closeConnection() {
		
	}
	
	public List<ComObject> getModelInput() {
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
