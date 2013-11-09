package Client;

public enum LoginError {

	/**
	 * Der Benutzername ist bereits vergeben.
	 */
	nameInUse,
	
	/**
	 * Der Server wurde unter der angegeben Adresse
	 * nicht gefunden.
	 */
	serverNotFound,
}
