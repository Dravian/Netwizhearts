package Ruleset;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.junit.Test;

import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestHeartsWinner {

	@Test
	public void testGetWinner() throws IOException {
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
		GameServer server = new GameServer(lserver, blue, "Some Game", RulesetType.Hearts, "", false);
		server.addPlayer(white);
		server.addPlayer(orange);
		server.addPlayer(brown);
		ServerHearts hearts = new ServerHearts(server);
		hearts.addPlayerToGame("Mr. Blue");
		hearts.addPlayerToGame("Mr. White");
		hearts.addPlayerToGame("Mr. Orange");
		hearts.addPlayerToGame("Mr. Brown");
			
		OtherData dateblue = hearts.getPlayerState("Mr. Blue").getOtherData();
		HeartsData heartsdatblue = (HeartsData) dateblue;
		heartsdatblue.setCompletePoints(80);
		
		OtherData datewhite = hearts.getPlayerState("Mr. White").getOtherData();
		HeartsData heartsdatwhite = (HeartsData) datewhite;
		heartsdatwhite.setCompletePoints(200);
		
		OtherData dateorange = hearts.getPlayerState("Mr. Orange").getOtherData();
		HeartsData heartsdatorange = (HeartsData) dateorange;
		heartsdatorange.setCompletePoints(130);
		
		OtherData datebrown = hearts.getPlayerState("Mr. Brown").getOtherData();
		HeartsData heartsdatbrown = (HeartsData) datebrown;
		heartsdatbrown.setCompletePoints(240);
		
		assertTrue(hearts.getWinner().compareTo("Mr. Brown") == 0);
	}

}
