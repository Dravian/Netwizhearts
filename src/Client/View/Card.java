/**
 * 
 */
package Client.View;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/** 
 * Card ist die View-seitige Repräsentation einer Karte. 
 * Sie wird verwendet um einzelne Karten auf das Spielfeld zu zeichnen.
 * Dazu enthält sie die Pfadangabe zu dem Ordner, in dem die Bilder der
 * Karten gespeichert sind, und eine ID, um das genaue Bild zu spezifizieren.
 * 
 * @author m4nkey
 */
public class Card extends JLabel{

	private static final long serialVersionUID = 8733682958484899430L;
	
	protected static String path;
	
	/**
	 * Erstellt eine neue Karte für die Anzeige und zeichnet dafür
	 * das Bild, das durch die Pfadangabe s angegeben ist.
	 * 
	 * @param s Pfadangabe zum zu zeichnenden Bild
	 */
	public Card(String s) {
		super(new ImageIcon(s));
	}
}