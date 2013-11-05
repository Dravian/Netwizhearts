/**
 * 
 */
package Client.View;

import Ruleset.HeartsID;

/** 
 * HeartsCard ist die View-seitige Repräsentation einer Hearts-Karte. 
 * Sie wird verwendet um einzelne Karten auf das Spielfeld zu zeichnen.
 * Dazu enthält sie die Pfadangabe zu dem Ordner, in dem die Bilder der
 * Karten gespeichert sind, und eine ID, um das genaue Bild zu spezifizieren.
 * 
 * @author m4nkey
 */
public class HeartsCard extends Card {
	
	private static final long serialVersionUID = 2461630198563677934L;

	private HeartsID cardID;
	
	private static String path = ".src/heartscards/"; //TODO
	
	
	/**
	 * Erstellt eine neue Hearts Karte für die Anzeige und zeichnet das Bild, 
	 * das durch id spezifiziert ist.
	 * 
	 * @param id HeartsID der Karte
	 */
	public HeartsCard(HeartsID id) {
		super(path + id.toString());
		cardID = id;
	}


	/**
	 * Gibt die HeartsID der Karte zurück
	 * 
	 * @return HeartsID der Karte
	 */
	public HeartsID getCardID() {
		return cardID;
	}
}