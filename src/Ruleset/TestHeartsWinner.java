package Ruleset;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.TestPlayer;

import Server.GameServer;
import Server.LobbyServer;
import Server.Player;

public class TestHeartsWinner {

	LobbyServer lserver;
	
	GameServer server;
	
	TestPlayer blue;
	
	TestPlayer white;
	
	TestPlayer orange;
	
	TestPlayer brown;
	
	@Before
	public void setUp() {
		lserver = new LobbyServer();
		blue = new TestPlayer(lserver, null, null);
		white = new TestPlayer(lserver, null, null);
		orange = new TestPlayer(lserver, null, null);
		brown = new TestPlayer(lserver, null, null);
	}
	
	@After
	public void tearDown() {	
		blue = null;
		white = null;
		orange = null;
		brown = null;
		server = null;
		lserver = null;
	}
	
	@Test
	public void testGetWinner() throws IOException {
		
		server = new GameServer(lserver, blue, "Some Game", RulesetType.Hearts, "", false);
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
		heartsdatblue.setPoints(80);
		
		OtherData datewhite = hearts.getPlayerState("Mr. White").getOtherData();
		HeartsData heartsdatwhite = (HeartsData) datewhite;
		heartsdatwhite.setPoints(200);
		
		OtherData dateorange = hearts.getPlayerState("Mr. Orange").getOtherData();
		HeartsData heartsdatorange = (HeartsData) dateorange;
		heartsdatorange.setPoints(130);
		
		OtherData datebrown = hearts.getPlayerState("Mr. Brown").getOtherData();
		HeartsData heartsdatbrown = (HeartsData) datebrown;
		heartsdatbrown.setPoints(240);
		
		assertTrue(hearts.getWinner().compareTo("Mr. Brown") == 0);
	}

}
