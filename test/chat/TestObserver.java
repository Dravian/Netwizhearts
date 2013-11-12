package chat;

import java.util.Observable;
import java.util.Observer;

public class TestObserver implements Observer {
	
	private String chatMsg;
	
	public String getChatMessage() {
		return chatMsg;
	}

	@Override
	public void update(Observable o, Object arg) {
		chatMsg = (String) arg;	
	}

}
