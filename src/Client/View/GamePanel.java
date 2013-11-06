/**
 * 
 */
package Client.View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/** 
 * Das Panel ist die Komponente des Game-Fensters, 
 * welche das eigentliche Spiel darstellt. Es besteht aus veschiedenen Panelobjekten, 
 * welche je nach Regelwerk auf das Spielfeld gezeichnet werden. 
 * Dazu gehören die eigenen Karten, eventuell ausgewählte Karten, 
 * ein Textfeld z.B. zur Anzeige der Anzahl der restlichen Karten der Mitspieler 
 * und den Ablagestapel (/L194/). Nach jeder Runde wird der Punktestand  aktualisiert.
 * 
 * @author m4nkey
 */
public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = -1041218552426155968L;
	
	/** 
	 */
	private OwnHand ownHand;
	/** 
	 */
	private Object ownScoreLabel;
	/** 
	 */
	private Set<OtherPlayer> otherPlayer;
	/** 
	 */
	private DrawDeck drawDeck;
	/** 
	 */
	private DiscardPile discardPile;
	
	private BufferedImage background;
	
	/**
	 * Erstellt das GamePanel
	 */
	public GamePanel() {
	       try {                
	          background = ImageIO.read(new File("./src/game.png"));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	    }
	@Override
	public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        g.drawImage(background, 0, 0, null); // see javadoc for more info on the parameters 
        super.paintComponent(g);
    }
}