package Ruleset;

import java.util.List;
import java.util.Map;

import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import ComObjects.RulesetMessage;
import Server.GameServer;

public class TestServerWizard extends ServerWizard {
	List<PlayerState> players = getPlayers();
	Card trumpCard;
	List<RulesetMessage> sendInput;
	
	public TestServerWizard(GameServer s) {
		super(s);
	}

	public void startGame(){
		List<PlayerState> players = getPlayers();
		
		setFirstPlayer(players.get(0));
	}
	
	public void start1() {
		
		setGamePhase(GamePhase.RoundStart);
		
		getGameState().shuffleDeck();
		getGameState().dealCards(getGameState().getRoundNumber());
		
		Card trumpCard = getGameState().getTopCard();
		getGameState().setTrumpCard(trumpCard);
	}
	
	public Card getTrumpCard() {
		return trumpCard;
	}
	
	public void start2() {
		for(PlayerState player : players) {
			send(new MsgUser(generateGameClientUpdate(player)), player.getName());
		}
	}
	
	public void start3() {
		int valueOfSorcerer = 14;
		
		if(trumpCard.getValue() == valueOfSorcerer) {
			setGamePhase(GamePhase.SelectionRequest);
			
			send(new MsgSelectionRequest(), getFirstPlayer().getName());	
			
		} else {
			setGamePhase(GamePhase.TrickRequest);
			
			for(PlayerState player : players) {
				send(new MsgNumberRequest(), player.getName());
			}
		}
	}
	
	public void send(RulesetMessage message, String name) {
		sendInput.add(message);
	}
	
	public List<RulesetMessage> getSendInput() {
		return sendInput;
	}
}
