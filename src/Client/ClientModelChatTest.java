package Client;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import test.TestMessageListenerThread;
import test.TestObserver;
import Client.ClientModel;
import ComObjects.ComChatMessage;

public class ClientModelChatTest {

	ComChatMessage testMessage;

	ClientModel testModel;

	TestObserver testObserver;

	TestMessageListenerThread testNetIO;

	String testText;

	@Before
    public void setUp() throws Exception {
		testNetIO = new TestMessageListenerThread();
		testObserver = new TestObserver();
		testMessage = new ComChatMessage("Hello Test!");
		testModel = new ClientModel((MessageListenerThread) testNetIO);
		testNetIO.setModel(testModel);
		testModel.addObserver(testObserver);
    }
 
    @After
    public void tearDown() throws Exception { 
    	testNetIO = null;
    	testMessage = null;
    	testModel = null;
    	testObserver = null;
    }

	@Test
	public void testSendChatMessage() {
		String inputText = "Hello Test!";
		testModel.sendChatMessage(inputText);
		testText = ((ComChatMessage) testNetIO.getModelInput().get(0)).getChatMessage();
		assertTrue("Vergleich der gesendeten Chatnachrichten", testText.contains(inputText));
	}

	@Test
	public void testReceiveChatMessage() {
		testNetIO.injectComObject(testMessage);
		assertTrue("Vergleich der empfangenen Chatnachrichten", 
				testObserver.getChatMessage().equals(testMessage.getChatMessage()));
	}
}
