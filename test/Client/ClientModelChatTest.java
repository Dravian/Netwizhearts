package Client;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ComObjects.ComChatMessage;

public class ClientModelChatTest {

	ComChatMessage testMessage;
	
	ClientModel testModel;

	@Mock
	MessageListenerThread netIO;
	
	@Before  
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		testMessage = new ComChatMessage("Hello Test!");
		testModel = new ClientModel(); 
    }  
  
    @After  
    public void tearDown() { 
    	netIO = null;
    	testMessage = null;
    	testModel = null;
    }  
	
	@Test
	public void testSendChatMessage() {
		testModel.setNetIO(netIO);
		testModel.sendMessage(testMessage);
		Mockito.verify(netIO).send(testMessage);
	}
	
	@Test
	public void testReceiveChatMessage() {
		testMessage.process(testModel);
		assertTrue(testModel.getChatMessage().equals(testMessage.getChatMessage()));
	}

}
