/**
 * 
 */
package Client.View;

import java.util.Observable;
import java.util.Observer;


/** 
 * Dieses Fenster ermöglicht es dem Spieler aus einer Liste von Items eines auszuwählen.
 */
public class ChooseItem implements Observer{
	
	private Object itemComboBox;

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * übergebenen Befehl wird ein Update des Fensters ausgeführt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openChooseItem
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}