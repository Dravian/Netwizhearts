/**
 * 
 */
package Client.View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Ruleset.Card;
import Ruleset.RulesetType;

/** 
 * ViewCard. ViewCard ist die View-seitige Repraesentation einer Karte. 
 * Sie wird verwendet um einzelne Karten auf das Spielfeld zu zeichnen.
 * Dazu enthaelt sie die Pfadangabe zu dem Ordner, in dem die Bilder der
 * Karten gespeichert sind, und eine ID, um das genaue Bild zu spezifizieren.
 */
public class ViewCard extends JPanel{

	private static final long serialVersionUID = 8733682958484899430L;
	private static String DATAPATH = "Data/";
	
	private RulesetType ruleset;
	private Card card;
	private BufferedImage face;
	private int xPos;
	private int yPos;
	
	/**
	 * Erstellt eine neue Karte fuer die Anzeige und zeichnet dafuer
	 * das Bild
	 * 
	 * @param c Karte
	 */
	public ViewCard(Card c) {
		//TODO
		ruleset = c.getRuleset();
		card = c;
		xPos = 0;
		yPos = 0;
		try {                
	          face = ImageIO.read(new File(DATAPATH + ruleset.toString().toLowerCase() + "/"+  c.toString() +".jpg"));
	       } catch (IOException ex) {
	            //TODO
	       }
	}
	
	/**
	 * Gibt die Karte zurueck
	 * 
	 * @return Karte
	 */
	public Card getCard() {
		return card;
	}
	
	/**
	 * Setzt die Position des Objekts
	 * 
	 * @param x X-Position
	 * @param y Y-Position
	 */
	public void setPosition(int x, int y) {
		xPos = x;
		yPos = y;
	}
	
	@Override
	public void paint(Graphics g) {
        //TODO
        g.drawImage(face, xPos, yPos, null);
        super.paint(g);
    }
}