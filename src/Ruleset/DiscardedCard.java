package Ruleset;

import java.io.Serializable;

/**
 * Eine Karte die von einem Spieler auf den Ablegestapel gelegt wird und als
 * Atrribute die Karte die gespielt wurde und den Spieler der sie gespielt hat 
 * enthält.
 */
public class DiscardedCard implements Serializable{
	private String name;
	private Card card;
	
	/**
	 * Erstellt eine abgelegte Karte
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
	public String getOwnerName() {
		return name;
	}
	
	/**
	 * Holt die Karte die abgelegt wurde
	 * @return Die Karte die abgelegt wurde
	 */
	public Card getCard() {
		return card;
	}
	
	/**
	 * Kopier eine DiscardedCard
	 * @return Eine Kopie dieser DiscardedCard
	 */
	protected DiscardedCard clone() {
		DiscardedCard copy = new DiscardedCard(name,card);
		return copy;
	}
}
