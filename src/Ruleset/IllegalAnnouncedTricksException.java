package Ruleset;

public class IllegalAnnouncedTricksException extends RulesetException {
	private int tricks;
	
	public IllegalAnnouncedTricksException(int tricks) {
		super();
		this.tricks = tricks;
	}
	
	public int getAnnouncedTricks() {
		return tricks;
	}
}
