/**
 * 
 */
package Client.View;

import java.util.Observable;
import java.util.Observer;


/** 
 * ScoreWindow. Dieses Fenster zeigt den momentanen Punktestand 
 * nach jeder Runde und den Gesamtpunktestand am Ende des Spieles an.
 */
public class ScoreWindow implements Observer{

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * uebergebenen Befehl wird ein Update des Fensters ausgefuehrt 
	 * oder eine Fehlermeldung angezeigt.
	 * 
	 * @param o erwartet ein Objekt von der Klasse ClientModel
	 * @param arg erwartet: showScore
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}