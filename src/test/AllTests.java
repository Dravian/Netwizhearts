package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Client.ClientInGameHeartsTest;
import Client.ClientInGameLobbyTest;
import Client.ClientInGameWizardTest;
import Client.ClientInServerLobbyTest;
import Client.ClientLoginTest;
import Ruleset.ClientViewTestEnd;
import Ruleset.TestClientHeartsAreValidChoosenCards;
import Ruleset.TestClientHeartsIsValidMove;
import Ruleset.TestClientRulesetIllegalArguments;
import Ruleset.TestClientWizardIsValidColour;
import Ruleset.TestClientWizardIsValidMove;
import Ruleset.TestClientWizardIsValidNumber;
import Ruleset.TestHeartsCalculateTricks;
import Ruleset.TestHeartsIsValidMove;
import Ruleset.TestHeartsIsValidMoveOnly;
import Ruleset.TestHeartsResolveMsgCard;
import Ruleset.TestHeartsRoundEnd;
import Ruleset.TestHeartsStart;
import Ruleset.TestHeartsSwap;
import Ruleset.TestHeartsWinner;
import Ruleset.TestWizardCalculateTricks;
import Ruleset.TestWizardIsValidMove;
import Ruleset.TestWizardResolveColour;
import Ruleset.TestWizardResolveMsgCard;
import Ruleset.TestWizardResolveMsgNumber;
import Ruleset.TestWizardRoundEnd;
import Ruleset.TestWizardStart;
import Server.GameServerCheatTest;
import Server.GameServerDurabilityTest100;
import Server.GameServerDurabilityTest250;
import Server.GameServerDurabilityTest500;
import Server.GameServerTests;
import Server.LobbyCheatTest;
import Server.LobbyDurabilityTest1000;
import Server.LobbyDurabilityTest2000;
import Server.LobbyDurabilityTest500;
import Server.LobbyTest;
import Server.LoginTest;
import Server.LoginTestNegativ;

@RunWith(Suite.class)
@SuiteClasses({ClientInGameHeartsTest.class, ClientInGameLobbyTest.class,
	ClientInGameWizardTest.class, ClientInServerLobbyTest.class,
	ClientLoginTest.class, TestClientHeartsAreValidChoosenCards.class, 
	TestClientHeartsIsValidMove.class, TestClientRulesetIllegalArguments.class,
	TestClientWizardIsValidColour.class, TestClientWizardIsValidMove.class,
	TestClientWizardIsValidNumber.class, TestHeartsCalculateTricks.class, 
	TestHeartsIsValidMove.class, TestHeartsIsValidMoveOnly.class,
	TestHeartsResolveMsgCard.class, TestHeartsRoundEnd.class, 
	TestHeartsStart.class, TestHeartsSwap.class, TestHeartsWinner.class,
	TestWizardCalculateTricks.class, TestWizardIsValidMove.class,
	TestWizardResolveColour.class, TestWizardResolveMsgCard.class,
	TestWizardResolveMsgNumber.class, TestWizardRoundEnd.class, 
	TestWizardStart.class, GameServerCheatTest.class, LobbyCheatTest.class,
	LoginTest.class, LoginTestNegativ.class, GameServerTests.class, 
	LobbyTest.class, LobbyDurabilityTest1000.class,
	LobbyDurabilityTest2000.class , LobbyDurabilityTest500.class, 
	GameServerDurabilityTest100.class, GameServerDurabilityTest250.class,
	GameServerDurabilityTest500.class, ClientViewTestEnd.class})
public class AllTests {

}
