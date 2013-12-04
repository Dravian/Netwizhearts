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
	
	private static String IMAGEPATH = "Data/";
	
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
	public GamePanel(List<String> names, List<String> infos, JPanel contentPane) {
		try {
			background = ImageIO.read(new File(IMAGEPATH + "background.jpg"));
		} catch (IOException e) {
			background = null;
		}
		ownHand = new OwnHand(contentPane);
		ownHand.setBounds(10, 440, 800, 105);
		//ownHand.setOpaque(true);
		contentPane.add(ownHand);
		
		otherHands = new LinkedList<OtherPlayer>();
		discardPiles = new LinkedList<DiscardPile>();
		for (int i = 0; i < names.size(); i++) {
			otherHands.add(i, new OtherPlayer(names.get(i), infos.get(i)));
			discardPiles.add(i, new DiscardPile());
			contentPane.add(otherHands.get(i));
			contentPane.add(discardPiles.get(i));
		}
		discardPiles.add(otherHands.size(), new DiscardPile());
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
	
	/**
	 * Setzt die Hand des Spielers
	 * 
	 * @param cards Handkarten des Spielers
	 */
	public void updateOwnCards(final List<Card> cards) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				ownHand.setHand(cards);
				//ownHand.setOpaque(true);
				//repaint();
			}
		});
		
	}
	
	public void updateCardsPlayed(List<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			discardPiles.get(i).addCard(cards.get(i));
		}
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
		otherHands.get(0).setBounds(100, 70, otherHands.get(0).getWidth(), otherHands.get(0).getHeight());
		otherHands.get(1).setBounds(750, 70, otherHands.get(1).getWidth(), otherHands.get(1).getHeight());
		discardPiles.get(0).setBounds(270, 160, discardPiles.get(0).getWidth(), discardPiles.get(0).getHeight());
		discardPiles.get(1).setBounds(660, 160, discardPiles.get(1).getWidth(), discardPiles.get(1).getHeight());
		discardPiles.get(2).setBounds(465, 310, discardPiles.get(2).getWidth(), discardPiles.get(2).getHeight());
	}
	
	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 4 Spielern
	 */
	private void makeTrickGameBoardFourPlayers() {
		otherHands.get(0).setBounds(10, 200, otherHands.get(0).getWidth(), otherHands.get(0).getHeight());
		otherHands.get(1).setBounds(425, 10, otherHands.get(1).getWidth(), otherHands.get(1).getHeight());
		otherHands.get(2).setBounds(820, 200, otherHands.get(2).getWidth(), otherHands.get(2).getHeight());
		discardPiles.get(0).setBounds(200, 210, discardPiles.get(0).getWidth(), discardPiles.get(0).getHeight());
		discardPiles.get(1).setBounds(465, 145, discardPiles.get(1).getWidth(), discardPiles.get(1).getHeight());
		discardPiles.get(2).setBounds(710, 210, discardPiles.get(2).getWidth(), discardPiles.get(2).getHeight());
		discardPiles.get(3).setBounds(465, 310, discardPiles.get(3).getWidth(), discardPiles.get(3).getHeight());
	}

	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 5 Spielern
	 */
	private void makeTrickGameBoardFivePlayers() {
		otherHands.get(0).setBounds(10, 200, otherHands.get(0).getWidth(), otherHands.get(0).getHeight());
		otherHands.get(1).setBounds(250, 10, otherHands.get(1).getWidth(), otherHands.get(1).getHeight());
		otherHands.get(2).setBounds(600, 10, otherHands.get(2).getWidth(), otherHands.get(2).getHeight());
		otherHands.get(3).setBounds(820, 200, otherHands.get(3).getWidth(), otherHands.get(3).getHeight());
		discardPiles.get(0).setBounds(200, 210, discardPiles.get(0).getWidth(), discardPiles.get(0).getHeight());
		discardPiles.get(1).setBounds(365, 145, discardPiles.get(1).getWidth(), discardPiles.get(1).getHeight());
		discardPiles.get(2).setBounds(570, 145, discardPiles.get(2).getWidth(), discardPiles.get(2).getHeight());
		discardPiles.get(3).setBounds(710, 210, discardPiles.get(3).getWidth(), discardPiles.get(3).getHeight());
		discardPiles.get(4).setBounds(465, 310, discardPiles.get(4).getWidth(), discardPiles.get(4).getHeight());
	}

	/**
	 * Erstellt eine Spielfeld fuer ein Stich-Kartenspiel
	 * mit 6 Spielern
	 */
	private void makeTrickGameBoardSixPlayers() {
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
		discardPiles.get(5).setBounds(465, 310, discardPiles.get(5).getWidth(), discardPiles.get(5).getHeight());
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
        for (OtherPlayer o : otherHands) {
        	//o.paintComponent(g);
        }
    }
}