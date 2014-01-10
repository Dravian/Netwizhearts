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
import Server.GameServerDurability100Test;
import Server.GameServerDurability250Test;
import Server.GameServerDurability500Test;
import Server.GameServerTest;
import Server.LobbyCheatTest;
import Server.LobbyDurability1000Test;
import Server.LobbyDurability2000Test;
import Server.LobbyDurability500Test;
import Server.LobbyTest;
import Server.LoginTest;
import Server.LoginNegativTest;

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
	LoginTest.class, LoginNegativTest.class, GameServerTest.class, 
	LobbyTest.class, LobbyDurability1000Test.class,
	LobbyDurability2000Test.class , LobbyDurability500Test.class, 
	GameServerDurability100Test.class, GameServerDurability250Test.class,
	GameServerDurability500Test.class, ClientViewTestEnd.class})
public class AllTests {

}
