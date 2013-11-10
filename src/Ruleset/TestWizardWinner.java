package Ruleset;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
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
		ObjectOutputStream out1 = new ObjectOutputStream(socket1.getOutputStream());
		out1.flush();
		ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
		out2.flush();
		ObjectOutputStream out3 = new ObjectOutputStream(socket3.getOutputStream());
		out3.flush();
		ObjectOutputStream out4 = new ObjectOutputStream(socket4.getOutputStream());
		out4.flush();
		ObjectInputStream in1 = new ObjectInputStream(socket1.getInputStream());
		ObjectInputStream in2 = new ObjectInputStream(socket2.getInputStream());
		ObjectInputStream in3 = new ObjectInputStream(socket3.getInputStream());
		ObjectInputStream in4 = new ObjectInputStream(socket4.getInputStream());
		Player blue = new Player(lserver, out1, in1);
		Player white = new Player(lserver, out2, in2);
		Player orange = new Player(lserver, out3, in3);
		Player brown = new Player(lserver, out4, in4);
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
		
		assertTrue(wiz.getWinner().compareTo("Mr. Brown") == 0);
	}

}
