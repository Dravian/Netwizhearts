/**
 * 
 */
package Client.View;

import java.util.Observable;
import java.util.Observer;


/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author m4nkey
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class ChooseCards implements Observer{
	
	private Object playerHandPanel;

	/**
	 * Wird durch notify() im ClientModel aufgerufen. Je nach dem in arg
	 * �bergebenen Befehl wird ein Update des Fensters ausgef�hrt 
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