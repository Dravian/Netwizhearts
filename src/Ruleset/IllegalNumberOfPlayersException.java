package Ruleset;

/**
 * Wird geworfen wenn zu viele oder zu wenig Spieler in einem Spiel sind
 */
public class IllegalNumberOfPlayersException extends RulesetException {
	private int number;
	
	public IllegalNumberOfPlayersException(int number) {
		super();
		this.number = number;
	}
	
	public int getNumberOfPlayers() {
		return number;
	}
}
