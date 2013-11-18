package Ruleset;

import java.util.ArrayList;
import java.util.List;

import ComObjects.MsgNumberRequest;
import ComObjects.MsgSelectionRequest;
import ComObjects.MsgUser;
import ComObjects.RulesetMessage;
import Server.GameServer;

public class PrintWizard extends ServerWizard{
	String blue = "Blue";
	String red = "Red";
	String green = "Green";
	List<RulesetMessage> input;
	
	public PrintWizard(GameServer s) {
		super(s);
		addPlayerToGame(blue);
		addPlayerToGame(red);
		addPlayerToGame(green);
		input = new ArrayList<RulesetMessage>();
	}

	@Override
	public void runGame() throws RulesetException{
		List<PlayerState> players = getPlayers();
		System.out.println("Runde: " + getGameState().getRoundNumber());
		setFirstPlayer(players.get(0));
		System.out.println(getFirstPlayer());
		startWizardRound();
		
	}
	
	/**
	 * Wird am Anfang jeder Runde aufgerufen. Mischt und verteilt Karten und schickt
	 * Comobject mit Updates und Request f�r den Rundenanfang.
	 */
	private void startWizardRound() {
		int valueOfSorcerer = 14;
		List<PlayerState> players = getPlayers();
		
		setGamePhase(GamePhase.RoundStart);
		
		getGameState().shuffleDeck();
		getGameState().dealCards(getGameState().getRoundNumber());
		
		System.out.println("Blue: ");
			for(Card card : getPlayerState(blue).getHand()) {
				System.out.print(card.getValue() + " " + card.getColour() + "&");
			}
			System.out.println();
		System.out.println("Red: ");
			for(Card card : getPlayerState(red).getHand()) {
				System.out.print(card.getValue() + " " + card.getColour() + "&");
			}
			System.out.println();
		System.out.println("Green: ");
			for(Card card : getPlayerState(green).getHand()) {
				System.out.print(card.getValue() + " " + card.getColour() + "&");
			}
		System.out.println();
		System.out.println("Trumpffarbe");
		Card trumpCard = getGameState().getTopCard();
		getGameState().setTrumpCard(trumpCard);
		System.out.println(trumpCard.getColour());
		System.out.println();
		System.out.println();
		
		
		for(PlayerState player : players) {
			sendJo(new MsgUser(generateGameClientUpdate(player)), player.getName());
			
			System.out.println(player.getOtherData().getName());
		}
		System.out.println("Stop");
		GameClientUpdate game = ((MsgUser)input.get(1)).getGameClientUpdate();
		System.out.println(game.getOwnData().getName());
		System.out.println("Take");
		for(OtherData d : game.getOtherPlayerData()) {
			System.out.println(d.getName());
		}
		
		if(trumpCard.getValue() == valueOfSorcerer) {
			setGamePhase(GamePhase.SelectionRequest);
			
			sendJo(new MsgSelectionRequest(), getFirstPlayer().getName());	
			System.out.println(getFirstPlayer().getName());
		} else {
			setGamePhase(GamePhase.TrickRequest);
			
			for(PlayerState player : players) {
				sendJo(new MsgNumberRequest(), player.getName());
			}
		}
		System.out.println("End of Start");
	}
	
	public void sendJo(RulesetMessage message, String name) {
		input.add(message);
	}
	
}
