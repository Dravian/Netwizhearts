package test;

import java.util.LinkedList;
import java.util.List;

import Client.ClientModel;
import Client.MessageListenerThread;
import ComObjects.ComObject;

public class MockMessageListenerThread extends MessageListenerThread{

	List<ComObject> inputComObjectList = new LinkedList<ComObject>();

	ClientModel model;

	public void send(ComObject com) {
		inputComObjectList.add(com);
	}

	public void startConnection(ClientModel model, String host, int port) {
		this.model = model;
	}

	public void closeConnection() {

	}

	public List<ComObject> getModelInput() {
		return inputComObjectList;
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
