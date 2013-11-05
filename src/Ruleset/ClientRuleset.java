/**
 * 
 */
package Ruleset;

import Client.ClientModel;

/** 
 * Die abstrakte Klasse für das Regelwerk auf Clientseite
 */
public abstract class ClientRuleset {
	/** 
	 * Das Model in dem sich befindet
	 */
	private ClientModel client;
	
	/** 
	 * Der Spielzustand aus der Sicht des Models
	 */
	private GameClientUpdate gameState;

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void resolveMessage() {
		// begin-user-code
		// TODO Auto-generated method stub

		// end-user-code
	}
	
	/** 
	 * Prüft ob ein gemachter Zug in einem Spiel gültig war
	 */
	public abstract boolean isValidMove(Card card);
}