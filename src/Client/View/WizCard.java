/**
 * 
 */
package Client.View;

import Ruleset.WizID;


/** 
 * WizCard ist die View-seitige Repräsentation einer Wizard-Karte. 
 * Sie wird verwendet um einzelne Karten auf das Spielfeld zu zeichnen.
 * Dazu enthält sie die Pfadangabe zu dem Ordner, in dem die Bilder der
 * Karten gespeichert sind, und eine ID, um das genaue Bild zu spezifizieren.
 * 
 * @author m4nkey
 */
public class WizCard extends Card{
	
	private static final long serialVersionUID = 6366715087911461378L;
	
	private WizID cardID;
	
	private static String path = ".src/wizardcards/"; //TODO
	
	
	/**
	 * Erstellt eine neue Wizard Karte für die Anzeige und zeichnet das Bild, 
	 * das durch id spezifiziert ist.
	 * 
	 * @param id WizID der Karte
	 */
	public WizCard(WizID id) {
		super(path + id.toString() + ".png"); //TODO
		cardID = id;
	}


	/**
	 * Gibt die WizID der Karte zurück
	 * 
	 * @return WizID der Karte
	 */
	public WizID getCardID() {
		return cardID;
	}
	
}