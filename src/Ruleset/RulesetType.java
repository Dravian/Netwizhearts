package Ruleset;
/**
 * Ruleset. Den Regelwerk Typ den ein Spiel hat.
 */
public enum RulesetType {
	/**
	 * Der Typ zum Spiel Wizard.
	 */
	Wizard(3,6),
	/**
	 * Der Typ zum Spiel Hearts.
	 */
	Hearts(4,4),
	/**
	 * Der Typ zu keinem Spiel
	 */
	NONE(0,0);
	
	private final int MIN_PLAYER;
	private final int MAX_PLAYER;
	
	private RulesetType(int minPlayer, int maxPlayer) {
		MIN_PLAYER = minPlayer;
		MAX_PLAYER = maxPlayer;
	}
	
	/**
	 * Gibt die Mindestanzahl an Spieler zurück
	 * @return Die Mindestanzahl an Spieler
	 */
	public int getMinPlayer() {
		return MIN_PLAYER;
	}
	
	/**
	 * Gibt die maximale Anzahl an Spieler zurück
	 * @return Die maximale Anzahl an Spieler
	 */
	public int getMaxPlayer() {
		return MAX_PLAYER;
	}

}
