/**
 * 
 */
package Client.View;

import java.util.Observable;
import java.util.Observer;

/** 
 * Warning. Das Warning-Fenster zeigt dem Benutzer Fehlermeldungen bzw. Hinweise an, 
 * welche vom ClientModel uebergeben wurden. Es wird nur im Fehlerfall angezeigt.
 */
public class Warning implements Observer{
	
	private String warningText;
	
	/**
	 * Setzt den Warnhinweis des Fensters.
	 * 
	 * @param text Warnhinweis, der angezeigt werden soll
	 */
	public void setText(String text) {
		warningText = text;
	}

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * übergebenen Befehl wird ein Update des Fensters ausgeführt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openWarning
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}