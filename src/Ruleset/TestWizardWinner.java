package Ruleset;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.Test;

import ComObjects.ComChatMessage;
import ComObjects.ComObject;
import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

/**
 * Testet ob der richtige Sieger ermittelt wird und ob jedem Mitspieler
 * der richtige Sieger mitgeteilt wird
 */
public class TestWizardWinner {

	@Test
	public void testGetWinner() throws IOException{
		LobbyServer lserver = new LobbyServer();
		Socket socket1 = new Socket();
		Socket socket2 = new Socket();
		Socket socket3 = new Socket();
		Socket socket4 = new Socket();
		OutputStream out1 = socket1.getOutputStream();
		out1.flush();
		OutputStream out2 = socket2.getOutputStream();
		out2.flush();
		OutputStream out3 = socket3.getOutputStream();
		out3.flush();
		OutputStream out4 = socket4.getOutputStream();
		out4.flush();
		ObjectInputStream in1 = new ObjectInputStream(socket1.getInputStream());
		ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());
		ObjectInputStream in3 = new ObjectInputStream(socket3.getInputStream());
		ObjectInputStream in4 = new ObjectInputStream(socket4.getInputStream());
		Player blue = new Player(lserver, new ObjectOutputStream(out1), in1);
		Player white = new Player(lserver, new ObjectOutputStream(out2), in2);
		Player orange = new Player(lserver, new ObjectOutputStream(out2), in3);
		Player brown = new Player(lserver, new ObjectOutputStream(out3), in4);
		GameServer server = new GameServer(lserver, blue, "Some Game", RulesetType.Wizard, "", false);
		server.addPlayer(white);
		server.addPlayer(orange);
		server.addPlayer(brown);
		ServerWizard wiz = new ServerWizard(server);
		wiz.addPlayerToGame("Mr. Blue");
		wiz.addPlayerToGame("Mr. White");
		wiz.addPlayerToGame("Mr. Orange");
		wiz.addPlayerToGame("Mr. Brown");
			
		OtherData dateblue = wiz.getPlayerState("Mr. Blue").getOtherData();
		WizData wizdatblue = (WizData) dateblue;
		wizdatblue.setPoints(80);
		
		OtherData datewhite = wiz.getPlayerState("Mr. White").getOtherData();
		WizData wizdatwhite = (WizData) datewhite;
		wizdatwhite.setPoints(200);
		
		OtherData dateorange = wiz.getPlayerState("Mr. Orange").getOtherData();
		WizData wizdatorange = (WizData) dateorange;
		wizdatorange.setPoints(130);
		
		OtherData datebrown = wiz.getPlayerState("Mr. Brown").getOtherData();
		WizData wizdatbrown = (WizData) datebrown;
		wizdatbrown.setPoints(240);
		
		
		boolean bmsg1 = false;
		boolean bmsg2 = false;
		boolean bmsg3 = false;
		boolean bmsg4 = false;
		boolean gotall = false;
		int i = 0;
		while (!gotall) {
			ComChatMessage msg1;
			try {
				msg1 = (ComChatMessage)(in1.readObject());
				if (msg1.getChatMessage().compareTo("Mr. Brown has won") == 0) bmsg1 = true;
			} catch (ClassNotFoundException e) {
				
			}
			ComChatMessage msg2;
			try {
				msg2 = (ComChatMessage)(in1.readObject());
				if (msg2.getChatMessage().compareTo("Mr. Brown has won") == 0) bmsg2 = true;
			} catch (ClassNotFoundException e) {
				
			}
			ComChatMessage msg3;
			try {
				msg3 = (ComChatMessage)(in1.readObject());
				if (msg3.getChatMessage().compareTo("Mr. Brown has won") == 0) bmsg3 = true;
			} catch (ClassNotFoundException e) {
				
			}
			ComChatMessage msg4;
			try {
				msg4 = (ComChatMessage)(in1.readObject());
				if (msg4.getChatMessage().compareTo("Mr. Brown has won") == 0) bmsg4 = true;
			} catch (ClassNotFoundException e) {
				
			}
			gotall = bmsg1 && bmsg2 && bmsg3 && bmsg4;
			i++;
			if (i > 200) {break;}
		}
		
		assertTrue(wiz.getWinner().compareTo("Mr. Brown") == 0);
		assertTrue(gotall);
	}

}
