/**
 * 
 */
package Client.View;

import java.util.Observable;
import java.util.Observer;

/** 
 * In diesem Fenster, kann der Benutzer eine Zahl eingeben.
 */
public class InputNumber implements Observer{
	
	private Object numberTextfield;

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * �bergebenen Befehl wird ein Update des Fensters ausgef�hrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: openInputNumber, inputNumberSuccessful
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}