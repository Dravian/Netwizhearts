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

/** 
 * Card ist die View-seitige Repr�sentation einer Karte. 
 * Sie wird verwendet um einzelne Karten auf das Spielfeld zu zeichnen.
 * Dazu enth�lt sie die Pfadangabe zu dem Ordner, in dem die Bilder der
 * Karten gespeichert sind, und eine ID, um das genaue Bild zu spezifizieren.
 */
public class ViewCard extends JPanel{

	private static final long serialVersionUID = 8733682958484899430L;
	
	private String path;
	private int id;
	private BufferedImage face;
	
	/**
	 * Erstellt eine neue Karte f�r die Anzeige und zeichnet daf�r
	 * das Bild, das durch die Pfadangabe s und seine Kardinaliät n 
	 * im Ordner angegeben ist. Die Pfadangabe wird durch das Regelwerk
	 * bestimmt.
	 * 
	 * @param s Pfadangabe zum zu zeichnenden Bild
	 * @param n ID der Karte
	 */
	public ViewCard(String s, int n) {
		//TODO
		path = s;
		id = n;
		try {                
	          face = ImageIO.read(new File(path + "/Karte" + id));//TODO
	       } catch (IOException ex) {
	            //TODO
	       }
	}
	
	/**
	 * Gibt die ID der Karte zurück
	 * 
	 * @return ID der Karte
	 */
	public int getID() {
		return id;
	}
	
	@Override
	public void paintComponent(Graphics g) {
        //TODO
		//super.paintComponent(g);
        g.drawImage(face, 0, 0, null); // see javadoc for more info on the parameters 
        super.paintComponent(g);
    }
}