/**
 * 
 */
package Client.View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Ruleset.Card;
import Ruleset.HeartsCard;
import Ruleset.RulesetType;

/** 
 * GamePanel. Das GamePanel ist die Komponente des Game-Fensters, 
 * welche das eigentliche Spiel darstellt. Es besteht aus veschiedenen Panelobjekten, 
 * welche je nach Regelwerk auf das Spielfeld gezeichnet werden. 
 * Dazu gehoeren die eigenen Karten, eventuell ausgewaehlte Karten, 
 * ein Textfeld z.B. zur Anzeige der Anzahl der restlichen Karten der Mitspieler 
 * und den Ablagestapel. Nach jeder Runde wird der Punktestand aktualisiert.
 */
public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = -1041218552426155968L;
	
	
	private OwnHand ownHand;
	
	private Object ownScoreLabel;
	
	private List<OtherPlayer> otherHands;
	
	private DrawDeck drawDeck;
	
	private List<DiscardPile> discardPiles;
	
	private BufferedImage background;
	
	/**
	 * Erstellt ein GamePanel
	 * 
	 * @param names Namen der Mitspieler
	 * @param infos Informationen zu den Mitspielern
	 */
	public GamePanel(List<String> names, List<String> infos) {
		ownHand = new OwnHand();
		
		//Test
		List<Card> karten = new LinkedList<Card>();
		karten.add(HeartsCard.Herz2);
		karten.add(HeartsCard.HerzAss);
		karten.add(HeartsCard.Herz4);
		ownHand.setHand(karten);		
		
		//
		
		otherHands = new LinkedList<OtherPlayer>();
		for (int i = 0; i < names.size(); i++) {
			otherHands.add(i, new OtherPlayer(names.get(i), infos.get(i)));
		}
		int playercount = otherHands.size()+1;
		switch (playercount) {
		case 3:
			makeTrickGameBoardThreePlayers();
			break;
		case 4:
			makeTrickGameBoardFourPlayers();
			break;
		case 5:
			makeTrickGameBoardFivePlayers();
			break;
		case 6:
			makeTrickGameBoardSixPlayers();
			break;
		default:
			break;
		}
	}
	
	public void updateOwnCards(List<Card> cards) {
		//TODO
	}
	
	public void updateCardsPlayed(List<Card> cards) {
		//TODO
	}
	
	/**
	 * Setzt die Zusatzinformationen der Spieler
	 * 
	 * @param data
	 */
	public void updateOtherData(List<String> data) {
		for (int i = 0; i < otherHands.size(); i++) {
			otherHands.get(i).setInfo(data.get(i));
		}
	}
		
	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 3 Spielern
	 */
	private void makeTrickGameBoardThreePlayers() {
		otherHands.get(0).setPosition(100, 70);
		otherHands.get(1).setPosition(650, 70);
	}
	
	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 4 Spielern
	 */
	private void makeTrickGameBoardFourPlayers() {
		otherHands.get(0).setPosition(10, 200);
		otherHands.get(1).setPosition(450, 10);
		otherHands.get(2).setPosition(820, 200);
	}

	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 5 Spielern
	 */
	private void makeTrickGameBoardFivePlayers() {
		otherHands.get(0).setPosition(10, 200);
		otherHands.get(1).setPosition(250, 10);
		otherHands.get(2).setPosition(600, 10);
		otherHands.get(3).setPosition(820, 200);
	}

	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 6 Spielern
	 */
	private void makeTrickGameBoardSixPlayers() {
		otherHands.get(0).setPosition(10, 220);
		otherHands.get(1).setPosition(200, 10);
		otherHands.get(2).setPosition(430, 10);
		otherHands.get(3).setPosition(650, 10);
		otherHands.get(4).setPosition(830, 220);
	}
	
	
	
	@Override
	public void paint(Graphics g) {
        //super.paintComponent(g);
		//g.drawImage(background, 0, 0, null);
		ownHand.paint(g);
		for (OtherPlayer o : otherHands) {
			o.paint(g);
		}
        super.paint(g);
    }
}