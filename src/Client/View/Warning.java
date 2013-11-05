/**
 * 
 */
package Client.View;

import java.util.Observable;
import java.util.Observer;

/** 
 * Das Warning-Fenster zeigt dem Benutzer Fehlermeldungen bzw. Hinweise an, 
 * welche vom ClientModel übergeben wurden. Es wird nur im Fehlerfall angezeigt.
 * 
 * @author m4nkey
 */
public class Warning implements Observer{
	
	private Object warningText;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}