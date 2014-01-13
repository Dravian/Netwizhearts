package test;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Client.ViewNotification;

public class TestObserver implements Observer {

	private String chatMsg = "";

	private List<ViewNotification> notificationList = new LinkedList<ViewNotification>();

	public String getChatMessage() {
		return chatMsg;
	}

	public List<ViewNotification> getNotification() {
		return notificationList;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			if (arg.getClass() == String.class) {
				chatMsg = (String) arg;
			} else if (arg.getClass() == ViewNotification.class) {
				notificationList.add((ViewNotification) arg);
			}
		}
	}
}
