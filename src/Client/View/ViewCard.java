/**
 * 
 */
package Client.View;

import java.awt.Color;
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
	private boolean clicked;
	
	/**
	 * Erstellt eine neue Karte fuer die Anzeige und zeichnet dafuer
	 * das Bild
	 * 
	 * @param c Karte
	 */
	public ViewCard(Card c) {
		//TODO
		clicked = false;
		
		if (c != null) {
		card = c;
		ruleset = card.getRuleset();
		try {                
	          face = ImageIO.read(new File(DATAPATH + ruleset.toString().toLowerCase() + "/"+  card.toString() +".jpg"));
	       } catch (IOException ex) {
	            //TODO
	       }	
		} else {
			try {                
		          face = ImageIO.read(new File(DATAPATH + "backside.jpg"));
		       } catch (IOException ex) {
		            //TODO
		       }	
		}
		
	}
	
	/**
	 * Setzt den 'clicked' Zustand der Karte
	 * 
	 * @param b true, wenn Karte angeklickt wurde, false sonst
	 */
	public void setClicked(boolean b) {
		clicked = b;
	}
	
	/**
	 * Gibt zurueck, ob die Karte bereits angeklickt wurde
	 * @return
	 */
	public boolean isClicked() {
		return clicked;
	}
	
	/**
	 * Gibt die Karte zurueck
	 * 
	 * @return Karte
	 */
	public Card getCard() {
		return card;
	}
	
	public void setCard(Card c) {
		if (c != null) {
			card = c;
			ruleset = card.getRuleset();
			try {                
		          face = ImageIO.read(new File(DATAPATH + ruleset.toString().toLowerCase() + "/"+  card.toString() +".jpg"));
		       } catch (IOException ex) {
		            //TODO
		       }	
			} else {
				try {                
			          face = ImageIO.read(new File(DATAPATH + "backside.jpg"));
			       } catch (IOException ex) {
			            //TODO
			       }	
			}
	}
	
	@Override
	public int getWidth() {
		return face.getWidth();
	}
	@Override
	public int getHeight() {
		return face.getHeight();
	}
	
	@Override
	public void paintComponent(Graphics g) {
        //TODO
		super.paintComponent(g);
        g.drawImage(face, 0, 0, null);
        if (clicked) {
        	g.setColor(Color.YELLOW);
        	g.fillRect(0, 0, face.getWidth()-1, face.getHeight()-1);
        }
        
    }
}