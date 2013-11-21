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
	public void runGame() {
		List<PlayerState> players = getPlayers();	
		setFirstPlayer(players.get(1));
		
		System.out.println("Runde: " + getGameState().getRoundNumber());
		
		System.out.println("Erster Spieler: " + getFirstPlayer().getOtherData().toString());
		startWizardRound();
		
	}
	
	/**
	 * Wird am Anfang jeder Runde aufgerufen. Mischt und verteilt Karten und schickt
	 * Comobject mit Updates und Request für den Rundenanfang.
	 */
	private void startWizardRound() {
		int valueOfSorcerer = 14;
		List<PlayerState> players = getPlayers();
		
		setGamePhase(GamePhase.RoundStart);
		
		getGameState().shuffleDeck();
		getGameState().dealCards(getGameState().getRoundNumber());
		
		System.out.println("Blue's Karten: ");
			for(Card card : getPlayerState(blue).getHand()) {
				System.out.print(card.getValue() + " " + card.getColour() + " ");
			}
			System.out.println();
			
		System.out.println("Red's Karten: ");
			for(Card card : getPlayerState(red).getHand()) {
				System.out.print(card.getValue() + " " + card.getColour() + " ");
			}
			System.out.println();
			
		System.out.println("Green's Karten: ");
			for(Card card : getPlayerState(green).getHand()) {
				System.out.print(card.getValue() + " " + card.getColour() + " ");
			}
		System.out.println();
		
		Card trumpCard = getGameState().getTopCard();
		getGameState().setTrumpCard(trumpCard);
		System.out.println("Trumpffarbe: " + trumpCard.getColour());
		System.out.println();
		System.out.println();
		
		
		for(PlayerState player : players) {
			sendJo(new MsgUser(generateGameClientUpdate(player)), player.getName());
			
			System.out.println("Spieler im Spiel: " + player.getOtherData().getName());
		}
		
		System.out.println("Aus Sicht von Red:");
		
		GameClientUpdate game = ((MsgUser)input.get(1)).getGameClientUpdate();
		System.out.println(game.getOwnData().toString());

		System.out.println();
		
		for(int i = 0; i < game.getOtherPlayerData().size(); i++) {
			System.out.println(game.getOtherPlayerData().get(i).toString());
		}
		
		System.out.println("Aus Sicht von Blue:");
		
		game = ((MsgUser)input.get(0)).getGameClientUpdate();
		System.out.println(game.getOwnData().toString() );

		System.out.println();
		
		for(int i = 0; i < game.getOtherPlayerData().size(); i++) {
			System.out.println(game.getOtherPlayerData().get(i).toString());
		}
		
		System.out.println("Aus Sicht von Green: ");
		
		game = ((MsgUser)input.get(2)).getGameClientUpdate();
		System.out.println(game.getOwnData().toString());

		System.out.println();
		
		for(int i = 0; i < game.getOtherPlayerData().size(); i++) {
			System.out.println(game.getOtherPlayerData().get(i).toString());
		}
		
		if(trumpCard.getValue() == valueOfSorcerer) {
			setGamePhase(GamePhase.SelectionRequest);
			
			sendJo(new MsgSelectionRequest(), getFirstPlayer().getName());	
			System.out.println(getFirstPlayer().getName() + " darf eine TrumpFarbe waehlen.");
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
