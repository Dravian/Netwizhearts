/**
 * 
 */
package Client.View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Ruleset.Card;
import Ruleset.RulesetType;

/** 
 * ViewCard. ViewCard ist die View-seitige Repraesentation einer Karte. 
 * Sie wird verwendet um einzelne Karten auf das Spielfeld zu zeichnen.
 * Dazu enthaelt sie die Pfadangabe zu dem Ordner, in dem die Bilder der
 * Karten gespeichert sind, und eine ID, um das genaue Bild zu spezifizieren.
 */
public class ViewCard extends JPanel{
	private static int WIDTH = 70;
	private static int HEIGHT = 105;
	
	private RulesetType ruleset;
	private Card card;
	private Image face;
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
	          face = ImageIO.read(new File(Game.IMAGEPATH + ruleset.toString().toLowerCase() + "/"+  card.toString() +".jpg"))
	        		  .getScaledInstance(WIDTH, HEIGHT, UNDEFINED_CONDITION);
	       } catch (IOException ex) {
	            //TODO
	       }	
		} else {
			try {                
		          face = ImageIO.read(new File(Game.IMAGEPATH + "cards/" + Game.BACKSIDE));
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
		if (clicked) {
		this.setBorder(new LineBorder(Color.ORANGE, 3));
		} else {
			this.setBorder(null);
		}
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
				face = ImageIO.read(new File(Game.IMAGEPATH + ruleset.toString().toLowerCase() + "/"+  card.toString() +".jpg"))
		        		  .getScaledInstance(WIDTH, HEIGHT, UNDEFINED_CONDITION);
		       } catch (IOException ex) {
		            //TODO
		       }	
			} else {
				try {                
			          face = ImageIO.read(new File(Game.IMAGEPATH + "cards/" + Game.BACKSIDE));
			       } catch (IOException ex) {
			            //TODO
			       }	
			}
		repaint();
	}
	
	@Override
	public int getWidth() {
		return WIDTH;
	}
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawImage(face, 0, 0, null);
        
    }
}