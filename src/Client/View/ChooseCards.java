/**
 * 
 */
package Client.View;

import java.util.Observable;
import java.util.Observer;


/** 
 * In diesem Fenster muss der Benutzer eine vorbestimmte Menge Karten auswählen.
 */
public class ChooseCards implements Observer{
	
	private OwnHand playerHandPanel;

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * übergebenen Befehl wird ein Update des Fensters ausgeführt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openChooseCards
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}