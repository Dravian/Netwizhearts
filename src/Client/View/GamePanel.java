/**
 * 
 */
package Client.View;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Ruleset.Card;
import Ruleset.Colour;
import Ruleset.DiscardedCard;
import Ruleset.GameClientUpdate;
import Ruleset.HeartsCard;
import Ruleset.OtherData;
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
	
	private static String IMAGEPATH = "Data/";
	
	private OwnHand ownHand;
	
	private OwnOtherData ownScore;
	
	private List<OtherPlayer> otherHands;
	
	private List<DiscardPile> discardPiles;
	
	private BufferedImage background;
	
	private DrawDeck deck;
	
	private TrumpColour trumpColour;
	
	/**
	 * Erstellt ein GamePanel
	 * 
	 * @param playerCount Anzahl der Mitspieler
	 */
	public GamePanel(int playerCount) {
		try {
			background = ImageIO.read(new File(IMAGEPATH + "background.jpg"));
		} catch (IOException e) {
			background = null;
		}
		
		this.setLayout(null);
		
		ownHand = new OwnHand();
		ownHand.setBounds(5, 385, 750, 105);
		this.add(ownHand);
		
		ownScore = new OwnOtherData();
		ownScore.setBounds(760, 440, 100, 50);
		this.add(ownScore);
		
		deck = new DrawDeck();
		deck.setBounds(920, 385, 70, 105);
		this.add(deck);
		
		trumpColour = new TrumpColour();
		trumpColour.setBounds(868, 440, 50, 50);
		this.add(trumpColour);
		
		otherHands = new LinkedList<OtherPlayer>();		
		discardPiles = new LinkedList<DiscardPile>();
		for (int i = 0; i < playerCount; i++) {
			otherHands.add(i, new OtherPlayer());
			discardPiles.add(i, new DiscardPile());
			this.add(otherHands.get(i));
			this.add(discardPiles.get(i));
		}
		discardPiles.add(otherHands.size(), new DiscardPile());
		this.add(discardPiles.get(otherHands.size()));
		
		switch (playerCount+1) {
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
	
	/**
	 * Aktualisiert alle Spielelemente
	 * 
	 * @param update GameClientUpdate mit dem aktuellen Zustand des Spiels
	 */
	public void updateGame(final GameClientUpdate update) {
		updateOtherData(update.getOtherPlayerData());
		updateOwnOtherData(update.getOwnData());
		updateOwnCards(update.getOwnHand());
		clearCardsPlayed();
		updateCardsPlayed(update.getPlayedCards());
		setUncoveredCard(update.getUncoveredCard());
	}
	
	/**
	 * Aktualisiert die Trumpffarbe, die angezeigt wird
	 * 
	 * @param col Farbe
	 */
	public void updateTrumpColour(final Colour col) {
		trumpColour.setTrumpColour(col);
	}
	
	private void setUncoveredCard(Card c) {
		deck.setShownCard(c);
	}
	
	private void updateOwnOtherData(OtherData ownData) {
		ownScore.setData(ownData.toString());
	}
	
	private void updateOwnCards(List<Card> cards) {
		ownHand.setHand(cards);
	}
	
	private void clearCardsPlayed() {
		for (DiscardPile d : discardPiles) {
			d.addCard(null);
		}
	}
	
	private void updateCardsPlayed(List<DiscardedCard> cards) {
		for (DiscardedCard c : cards) {
			boolean wasOtherPlayer = false;
			for (OtherPlayer p : otherHands) {
				if (c.getOwnerName().compareTo(p.getName()) == 0) {
					int index = otherHands.indexOf(p);
					discardPiles.get(index).addCard(c.getCard());
					wasOtherPlayer = true;
				} 
			}
			if (!wasOtherPlayer) {
				discardPiles.get(discardPiles.size()-1).addCard(c.getCard());
			}
		}
	}
	
	private void updateOtherData(List<OtherData> data) {
		for (int i = 0; i < otherHands.size(); i++) {
			otherHands.get(i).setName(data.get(i).getOtherDataName());
			otherHands.get(i).setInfo(data.get(i).toString());
		}
	}

	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 3 Spielern
	 */
	private void makeTrickGameBoardThreePlayers() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				otherHands.get(0).setBounds(100, 70, otherHands.get(0).getWidth(), otherHands.get(0).getHeight());
				otherHands.get(1).setBounds(750, 70, otherHands.get(1).getWidth(), otherHands.get(1).getHeight());
				discardPiles.get(0).setBounds(270, 160, discardPiles.get(0).getWidth(), discardPiles.get(0).getHeight());
				discardPiles.get(1).setBounds(660, 160, discardPiles.get(1).getWidth(), discardPiles.get(1).getHeight());
				discardPiles.get(2).setBounds(465, 270, discardPiles.get(2).getWidth(), discardPiles.get(2).getHeight());
			}
		});
		
	}
	
	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 4 Spielern
	 */
	private void makeTrickGameBoardFourPlayers() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {				
				otherHands.get(0).setBounds(10, 200, otherHands.get(0).getWidth(), otherHands.get(0).getHeight());
				otherHands.get(1).setBounds(425, 10, otherHands.get(1).getWidth(), otherHands.get(1).getHeight());
				otherHands.get(2).setBounds(820, 200, otherHands.get(2).getWidth(), otherHands.get(2).getHeight());
				discardPiles.get(0).setBounds(200, 210, discardPiles.get(0).getWidth(), discardPiles.get(0).getHeight());
				discardPiles.get(1).setBounds(465, 145, discardPiles.get(1).getWidth(), discardPiles.get(1).getHeight());
				discardPiles.get(2).setBounds(710, 210, discardPiles.get(2).getWidth(), discardPiles.get(2).getHeight());
				discardPiles.get(3).setBounds(465, 270, discardPiles.get(3).getWidth(), discardPiles.get(3).getHeight());
			
			}
		});
		
	}

	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 5 Spielern
	 */
	private void makeTrickGameBoardFivePlayers() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				otherHands.get(0).setBounds(10, 200, otherHands.get(0).getWidth(), otherHands.get(0).getHeight());
				otherHands.get(1).setBounds(250, 10, otherHands.get(1).getWidth(), otherHands.get(1).getHeight());
				otherHands.get(2).setBounds(600, 10, otherHands.get(2).getWidth(), otherHands.get(2).getHeight());
				otherHands.get(3).setBounds(820, 200, otherHands.get(3).getWidth(), otherHands.get(3).getHeight());
				discardPiles.get(0).setBounds(200, 210, discardPiles.get(0).getWidth(), discardPiles.get(0).getHeight());
				discardPiles.get(1).setBounds(365, 145, discardPiles.get(1).getWidth(), discardPiles.get(1).getHeight());
				discardPiles.get(2).setBounds(570, 145, discardPiles.get(2).getWidth(), discardPiles.get(2).getHeight());
				discardPiles.get(3).setBounds(710, 210, discardPiles.get(3).getWidth(), discardPiles.get(3).getHeight());
				discardPiles.get(4).setBounds(465, 270, discardPiles.get(4).getWidth(), discardPiles.get(4).getHeight());
			}
		});
		
	}

	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 6 Spielern
	 */
	private void makeTrickGameBoardSixPlayers() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				otherHands.get(0).setBounds(10, 220, otherHands.get(0).getWidth(), otherHands.get(0).getHeight());
				otherHands.get(1).setBounds(200, 10, otherHands.get(1).getWidth(), otherHands.get(1).getHeight());
				otherHands.get(2).setBounds(430, 10, otherHands.get(2).getWidth(), otherHands.get(2).getHeight());
				otherHands.get(3).setBounds(650, 10, otherHands.get(3).getWidth(), otherHands.get(3).getHeight());
				otherHands.get(4).setBounds(830, 220, otherHands.get(4).getWidth(), otherHands.get(4).getHeight());
				discardPiles.get(0).setBounds(180, 230, discardPiles.get(0).getWidth(), discardPiles.get(0).getHeight());
				discardPiles.get(1).setBounds(300, 145, discardPiles.get(1).getWidth(), discardPiles.get(1).getHeight());
				discardPiles.get(2).setBounds(465, 145, discardPiles.get(2).getWidth(), discardPiles.get(2).getHeight());
				discardPiles.get(3).setBounds(630, 145, discardPiles.get(3).getWidth(), discardPiles.get(3).getHeight());
				discardPiles.get(4).setBounds(740, 230, discardPiles.get(4).getWidth(), discardPiles.get(4).getHeight());
				discardPiles.get(5).setBounds(465, 270, discardPiles.get(5).getWidth(), discardPiles.get(5).getHeight());
			}
		});
		
	}
	
	/**
	 * Fuegt einen MouseListener für die anklickbaren Karten hinzu
	 * 
	 * @param m ein MouseListener
	 */
	public void addCardMouseListener(MouseListener m) {
		ownHand.addCardMouseListener(m);
	}
	
	/**
	 * Setzt alle anklickbaren Karten auf 'nicht angeklickt'.
	 * Ruft dazu die unclickAll() Methode der OwnHand auf.
	 */
	public void unclickAll() {
		ownHand.unclickAll();

	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}