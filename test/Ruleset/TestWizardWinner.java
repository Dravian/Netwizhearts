package Ruleset;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import chat.TestPlayer;
import Server.GameServer;
import Server.LobbyServer;

/**
 * Testet ob der richtige Sieger ermittelt wird und ob jedem Mitspieler
 * der richtige Sieger mitgeteilt wird
 */
public class TestWizardWinner {

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
	public void testGetWinner() {
		
		server = new GameServer(lserver, blue, "Some Game", RulesetType.Wizard, "", false);
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
