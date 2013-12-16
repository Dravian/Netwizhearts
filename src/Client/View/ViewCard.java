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
	protected static int WIDTH = 48;
	protected static int HEIGHT = 76;
	protected static Image BACKSIDEIMAGE = null;
	
	protected static void updateBackside() {
		try {
			ViewCard.BACKSIDEIMAGE = ImageIO.read(new File(Game.IMAGEPATH + "cards/" + Game.BACKSIDE))
				  .getScaledInstance(ViewCard.WIDTH, ViewCard.HEIGHT, UNDEFINED_CONDITION);
		} catch (IOException e) {
			//TODO
		}
	}
	
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
		card = c;
		try {
			ViewCard.BACKSIDEIMAGE = ImageIO.read(new File(Game.IMAGEPATH + "cards/" + Game.BACKSIDE))
				  .getScaledInstance(ViewCard.WIDTH, ViewCard.HEIGHT, UNDEFINED_CONDITION);
		} catch (IOException e) {
			//TODO
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
		card = c;
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
		if (card != null) {
			ruleset = card.getRuleset();
			try {                
				face = ImageIO.read(new File(Game.IMAGEPATH + ruleset.toString().toLowerCase() + "/"+  card.toString() +".jpg"))
		        		  .getScaledInstance(ViewCard.WIDTH, ViewCard.HEIGHT, UNDEFINED_CONDITION);
				
		       } catch (IOException ex) {
		            face = null;
		            g.drawString("Card missing", 10, 10);
		       }
			g.drawImage(face, 0, 0, null);
			} else {
				g.drawImage(ViewCard.BACKSIDEIMAGE, 0, 0, null);
			}
        
        
    }
}