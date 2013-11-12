/**
 * 
 */
package Client.View;

import java.util.Observable;
import java.util.Observer;


/** 
 * ChooseItem. Dieses Fenster ermoeglicht es dem Spieler aus einer Liste von Items eines auszuwaehlen.
 */
public class ChooseItem implements Observer{
	
	private Object itemComboBox;

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openChooseItem, chooseItemSuccessful
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}