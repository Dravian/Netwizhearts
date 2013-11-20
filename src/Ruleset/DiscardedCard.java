package Ruleset;

/**
 * Eine Karte die von einem Spieler auf den Ablegestapel gelegt wird und als
 * Atrribute die Karte die gespielt wurde und den Spieler der sie gespielt hat 
 * enthält.
 */
public class DiscardedCard {
	private String name;
	private Card card;
	
	/**
	 * Erstelle eine abgelegt Karte
	 * @param name Der Name des Spielers der sie ablegt
	 * @param card Die Karte die abgelegt wurde
	 */
	public DiscardedCard(String name,  Card card) {
		this.name = name;
		this.card = card;
	}
	
	/**
	 * Holt den Namen des Spielers zurück
	 * @return Der Name vom Spieler
	 */
	protected String getName() {
		return name;
	}
	
	/**
	 * Holt die Karte die abgelegt wurde
	 * @return Die Karte die abgelegt wurde
	 */
	protected Card getCard() {
		return card;
	}
}
