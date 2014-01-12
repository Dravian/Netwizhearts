package Ruleset;

import Client.ClientModel;
import ComObjects.*;
import Server.GameServerRepresentation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.TestMessageListenerThread;
import test.TestObserver;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by AlinaAdmin on 06.01.14.
 */
public class TestClientHeartsIsValidMove {
    ClientModel testModel;
    TestObserver testObserver;
    TestMessageListenerThread testNetIO;
    String one = "one";
    String two = "two";
    String three = "three";
    String four = "four";
    GameClientUpdate gameState;
    PlayerState player1 = new PlayerState(one, RulesetType.Hearts);
    OtherData otherData1 = player1.getOtherData();

    PlayerState player2 = new PlayerState(two, RulesetType.Hearts);
    OtherData otherData2 = player2.getOtherData();

    PlayerState player3 = new PlayerState(three, RulesetType.Hearts);
    OtherData otherData3 = player3.getOtherData();

    PlayerState player4 = new PlayerState(four, RulesetType.Hearts);
    OtherData otherData4 = player4.getOtherData();
    List<String> players;

    @Before
    public void setUp() throws Exception {
        testNetIO = new TestMessageListenerThread();
        testModel = new ClientModel(testNetIO);
        testObserver = new TestObserver();
        testNetIO.setModel(testModel);
        testModel.addObserver(testObserver);
        testModel.createConnection("Player1", "localhost");
        players = new LinkedList<String>();
        players.add("Player2");
        Set<GameServerRepresentation> games =
                new HashSet<GameServerRepresentation>();
        ComInitLobby initLobby = new ComInitLobby(players, games);
        testNetIO.injectComObject(initLobby);
        testModel.hostGame("Hearts123", false, "", RulesetType.Hearts);
        players = new LinkedList<String>();
        players.add("Player1");
        ComInitGameLobby gameLobbyInit = new ComInitGameLobby(players);
        testNetIO.injectComObject(gameLobbyInit);
        ComUpdatePlayerlist updatePlayerList =
                new ComUpdatePlayerlist("Player2", false);
        testNetIO.injectComObject(updatePlayerList);
        testModel.getPlayerlist().contains("Player2");
        updatePlayerList = new ComUpdatePlayerlist("Player3", false);
        testNetIO.injectComObject(updatePlayerList);
        updatePlayerList = new ComUpdatePlayerlist("Player4", false);
        testNetIO.injectComObject(updatePlayerList);
        testModel.startGame();
        testNetIO.injectComObject(new ComStartGame());
        testNetIO.getModelInput().clear();
        testObserver.getNotification().clear();

        player1.getHand().add(HeartsCard.Kreuz2);
        player1.getHand().add(HeartsCard.Kreuz3);
        player1.getHand().add(HeartsCard.Kreuz4);
        player1.getHand().add(HeartsCard.Caro4);
        player1.getHand().add(HeartsCard.Caro10);
        player1.getHand().add(HeartsCard.CaroBube);
        player1.getHand().add(HeartsCard.CaroAss);
        player1.getHand().add(HeartsCard.Pik3);
        player1.getHand().add(HeartsCard.Pik10);
        player1.getHand().add(HeartsCard.PikBube);
        player1.getHand().add(HeartsCard.Herz2);
        player1.getHand().add(HeartsCard.Herz6);
        player1.getHand().add(HeartsCard.HerzKoenig);

        player2.getHand().add(HeartsCard.Kreuz8);
        player2.getHand().add(HeartsCard.KreuzBube);
        player2.getHand().add(HeartsCard.KreuzDame);
        player2.getHand().add(HeartsCard.Caro3);
        player2.getHand().add(HeartsCard.Caro6);
        player2.getHand().add(HeartsCard.CaroDame);
        player2.getHand().add(HeartsCard.Pik6);
        player2.getHand().add(HeartsCard.Pik9);
        player2.getHand().add(HeartsCard.PikAss);
        player2.getHand().add(HeartsCard.Herz3);
        player2.getHand().add(HeartsCard.Herz8);
        player2.getHand().add(HeartsCard.HerzBube);
        player2.getHand().add(HeartsCard.HerzAss);


        player3.getHand().add(HeartsCard.Kreuz5);
        player3.getHand().add(HeartsCard.Kreuz6);
        player3.getHand().add(HeartsCard.Kreuz10);
        player3.getHand().add(HeartsCard.KreuzKoenig);
        player3.getHand().add(HeartsCard.KreuzAss);
        player3.getHand().add(HeartsCard.Kreuz7);
        player3.getHand().add(HeartsCard.Kreuz9);
        player3.getHand().add(HeartsCard.Caro8);
        player3.getHand().add(HeartsCard.Pik4);
        player3.getHand().add(HeartsCard.Pik7);
        player3.getHand().add(HeartsCard.PikKoenig);
        player3.getHand().add(HeartsCard.Herz5);
        player3.getHand().add(HeartsCard.HerzDame);

        player4.getHand().add(HeartsCard.Caro2);
        player4.getHand().add(HeartsCard.Caro9);
        player4.getHand().add(HeartsCard.Caro5);
        player4.getHand().add(HeartsCard.Caro7);
        player4.getHand().add(HeartsCard.CaroKoenig);
        player4.getHand().add(HeartsCard.Pik2);
        player4.getHand().add(HeartsCard.Pik5);
        player4.getHand().add(HeartsCard.Pik8);
        player4.getHand().add(HeartsCard.PikDame);
        player4.getHand().add(HeartsCard.Herz7);
        player4.getHand().add(HeartsCard.Herz4);
        player4.getHand().add(HeartsCard.Herz9);
        player4.getHand().add(HeartsCard.Herz10);
    }

    @After
    public void tearDown() throws Exception {
        testNetIO = null;
        testModel = null;
        testObserver = null;
    }

    @Test
    public void testIsVaildFirstMove() {
        List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
        List<OtherData> enemyData = new ArrayList<OtherData>();

        enemyData.add(otherData2);
        enemyData.add(otherData3);
        enemyData.add(otherData4);

        String currentPlayer = one;
        int roundNumber = 1;

        gameState = new GameClientUpdate(player1, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        MsgUser game = new MsgUser(gameState);
        testModel.receiveMessage(new ComRuleset(game));

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Kreuz2);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Kreuz2);
        testNetIO.getModelInput().clear();

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Kreuz3);
        assertTrue(testNetIO.getModelInput().isEmpty());
        testNetIO.getModelInput().clear();
    }

    @Test
    public void testPlaySecondCard() {
        List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
        discardPile.add(new DiscardedCard(one, HeartsCard.Kreuz2));

        List<OtherData> enemyData = new ArrayList<OtherData>();

        enemyData.add(otherData1);
        enemyData.add(otherData3);
        enemyData.add(otherData4);

        String currentPlayer = two;
        int roundNumber = 1;

        gameState = new GameClientUpdate(player2, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        MsgUser game = new MsgUser(gameState);
        testModel.receiveMessage(new ComRuleset(game));

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Kreuz8);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Kreuz8);
        testNetIO.getModelInput().clear();

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.CaroDame);
        assertTrue(testNetIO.getModelInput().isEmpty());
        testNetIO.getModelInput().clear();
    }

    @Test
    public void testHeartFirstCircle() {
        List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
        discardPile.add(new DiscardedCard(one, HeartsCard.Kreuz2));
        discardPile.add(new DiscardedCard(two, HeartsCard.Kreuz8));
        discardPile.add(new DiscardedCard(three, HeartsCard.Kreuz10));

        List<OtherData> enemyData = new ArrayList<OtherData>();

        enemyData.add(otherData1);
        enemyData.add(otherData2);
        enemyData.add(otherData3);

        String currentPlayer = four;
        int roundNumber = 1;

        gameState = new GameClientUpdate(player4, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        MsgUser game = new MsgUser(gameState);
        testModel.receiveMessage(new ComRuleset(game));

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Herz5);
        assertTrue(testNetIO.getModelInput().isEmpty());
        testNetIO.getModelInput().clear();

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Pik8);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Pik8);
        testNetIO.getModelInput().clear();
    }

    @Test
    public void testHeartInSecondCircle() {
        player1.getHand().remove(HeartsCard.Kreuz2);
        player2.getHand().remove(HeartsCard.Kreuz8);
        player3.getHand().remove(HeartsCard.Kreuz10);
        player4.getHand().remove(HeartsCard.Pik5);

        List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
        discardPile.add(new DiscardedCard(one, HeartsCard.Kreuz4));
        discardPile.add(new DiscardedCard(two, HeartsCard.KreuzBube));
        discardPile.add(new DiscardedCard(three, HeartsCard.KreuzKoenig));


        List<OtherData> enemyData = new ArrayList<OtherData>();

        enemyData.add(otherData1);
        enemyData.add(otherData2);
        enemyData.add(otherData3);

        String currentPlayer = four;
        int roundNumber = 1;

        gameState = new GameClientUpdate(player4, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        MsgUser game = new MsgUser(gameState);
        testModel.receiveMessage(new ComRuleset(game));

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Herz10);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Herz10);
        testNetIO.getModelInput().clear();

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Pik8);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Pik8);
        testNetIO.getModelInput().clear();
    }


    @Test
    public void testBrokenHearts() {
        player1.getHand().remove(HeartsCard.Kreuz2);
        List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();

        List<OtherData> enemyData = new ArrayList<OtherData>();

        enemyData.add(otherData2);
        enemyData.add(otherData3);
        enemyData.add(otherData4);

        String currentPlayer = one;
        int roundNumber = 1;

        gameState = new GameClientUpdate(player1, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        MsgUser game = new MsgUser(gameState);
        testModel.receiveMessage(new ComRuleset(game));

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Herz6);
        assertTrue(testNetIO.getModelInput().isEmpty());
        testNetIO.getModelInput().clear();

        testNetIO.injectComObject(new ComRuleset(new MsgBoolean(true)));

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Herz6);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Herz6);
        testNetIO.getModelInput().clear();
        
        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Caro4);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Caro4);
        testNetIO.getModelInput().clear();
        
        
        player1.getHand().remove(HeartsCard.Kreuz3);
        player1.getHand().remove(HeartsCard.Kreuz4);
        player1.getHand().remove(HeartsCard.Caro4);
        player1.getHand().remove(HeartsCard.Caro10);
        player1.getHand().remove(HeartsCard.CaroBube);
        player1.getHand().remove(HeartsCard.CaroAss);
        player1.getHand().remove(HeartsCard.Pik3);
        player1.getHand().remove(HeartsCard.Pik10);
        player1.getHand().remove(HeartsCard.PikBube);
        player1.getHand().remove(HeartsCard.Herz2);
        player1.getHand().remove(HeartsCard.Herz6);
        player1.getHand().remove(HeartsCard.HerzKoenig);
        
        player1.getHand().add(HeartsCard.Herz2);
        player1.getHand().add(HeartsCard.Herz3);
        player1.getHand().add(HeartsCard.Herz4);
        player1.getHand().add(HeartsCard.Herz5);
        player1.getHand().add(HeartsCard.Herz6);
        player1.getHand().add(HeartsCard.Herz7);
        player1.getHand().add(HeartsCard.Herz8);
        player1.getHand().add(HeartsCard.Herz9);
        player1.getHand().add(HeartsCard.HerzAss);
        player1.getHand().add(HeartsCard.HerzBube);
        player1.getHand().add(HeartsCard.HerzDame);
        player1.getHand().add(HeartsCard.HerzKoenig);
        
        discardPile = new ArrayList<DiscardedCard>();    

        enemyData = new ArrayList<OtherData>();

        enemyData.add(otherData2);
        enemyData.add(otherData3);
        enemyData.add(otherData4);

        currentPlayer = one;
        roundNumber = 1;

        gameState = new GameClientUpdate(player1, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        game = new MsgUser(gameState);
        testModel.receiveMessage(new ComRuleset(game));
        
        testNetIO.injectComObject(new ComRuleset(new MsgBoolean(false)));
        
        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Herz6);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Herz6);
        testNetIO.getModelInput().clear();
    }
    
    @Test
    public void testOnlyHearts() {
        player1.getHand().remove(HeartsCard.Kreuz2);
        player1.getHand().remove(HeartsCard.Kreuz3);
        player1.getHand().remove(HeartsCard.Kreuz4);
        player1.getHand().remove(HeartsCard.Caro4);
        player1.getHand().remove(HeartsCard.Caro10);
        player1.getHand().remove(HeartsCard.CaroBube);
        player1.getHand().remove(HeartsCard.CaroAss);
        player1.getHand().remove(HeartsCard.Pik3);
        player1.getHand().remove(HeartsCard.Pik10);
        player1.getHand().remove(HeartsCard.PikBube);
        player1.getHand().remove(HeartsCard.Herz2);
        player1.getHand().remove(HeartsCard.Herz6);
        player1.getHand().remove(HeartsCard.HerzKoenig);
        
        player1.getHand().add(HeartsCard.Herz10);
        player1.getHand().add(HeartsCard.Herz2);
        player1.getHand().add(HeartsCard.Herz3);
        player1.getHand().add(HeartsCard.Herz4);
        player1.getHand().add(HeartsCard.Herz5);
        player1.getHand().add(HeartsCard.Herz6);
        player1.getHand().add(HeartsCard.Herz7);
        player1.getHand().add(HeartsCard.Herz8);
        player1.getHand().add(HeartsCard.Herz9);
        player1.getHand().add(HeartsCard.HerzAss);
        player1.getHand().add(HeartsCard.HerzBube);
        player1.getHand().add(HeartsCard.HerzDame);
        player1.getHand().add(HeartsCard.PikDame);
        
        
        List<DiscardedCard> discardPile = new ArrayList<DiscardedCard>();
        discardPile.add(new DiscardedCard("two",HeartsCard.Kreuz2));

        List<OtherData> enemyData = new ArrayList<OtherData>();

        enemyData.add(otherData2);
        enemyData.add(otherData3);
        enemyData.add(otherData4);

        String currentPlayer = one;
        int roundNumber = 1;

        gameState = new GameClientUpdate(player1, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        MsgUser game = new MsgUser(gameState);
        testModel.receiveMessage(new ComRuleset(game));

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Herz6);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.Herz6);
        testNetIO.getModelInput().clear();
        
        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.PikDame);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.PikDame);
        testNetIO.getModelInput().clear();
        
        
        
        player1.getHand().remove(HeartsCard.Herz2);
        player1.getHand().add(HeartsCard.CaroBube);
        
        discardPile = new ArrayList<DiscardedCard>();
        discardPile.add(new DiscardedCard("two",HeartsCard.Kreuz2));

        enemyData = new ArrayList<OtherData>();

        enemyData.add(otherData2);
        enemyData.add(otherData3);
        enemyData.add(otherData4);

        currentPlayer = one;
        roundNumber = 1;

        gameState = new GameClientUpdate(player1, discardPile,
                enemyData, currentPlayer, roundNumber, null);
        game = new MsgUser(gameState);
        testModel.receiveMessage(new ComRuleset(game));

        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.CaroBube);
        assertTrue(((MsgCard) ((ComRuleset) testNetIO.getModelInput().get(0))
                .getRulesetMessage()).getCard() == HeartsCard.CaroBube);
        testNetIO.getModelInput().clear();
        
        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.Herz10);
        assertTrue(testNetIO.getModelInput().size() == 0);
        testNetIO.getModelInput().clear();
        
        testModel.receiveMessage(new ComRuleset(new MsgCardRequest()));
        testModel.makeMove(HeartsCard.PikDame);
        assertTrue(testNetIO.getModelInput().size() == 0);
        testNetIO.getModelInput().clear();
    }
}
