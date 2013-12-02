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
	private int id;
	private BufferedImage face;
	private int xPos;
	private int yPos;
	
	/**
	 * Erstellt eine neue Karte fuer die Anzeige und zeichnet dafuer
	 * das Bild, das durch das Regelwerk r und seine Kardinaliaet n 
	 * im Ordner mit dem Namen des Regelwerks angegeben ist.
	 * 
	 * @param r Regelwerk
	 * @param n ID der Karte
	 */
	public ViewCard(RulesetType r, int n) {
		//TODO
		ruleset = r;
		id = n;
		xPos = 0;
		yPos = 0;
		try {                
	          face = ImageIO.read(new File(DATAPATH + ruleset.toString().toLowerCase() + "/card (" + id +" ).jpg"));
	       } catch (IOException ex) {
	            //TODO
	       }
	}
	
	/**
	 * Gibt die ID der Karte zurueck
	 * 
	 * @return ID der Karte
	 */
	public int getID() {
		return id;
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