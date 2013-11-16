package Ruleset;

public class IllegalRulesetType extends RulesetException {
	private RulesetType ruleset;
	
	public IllegalRulesetType(RulesetType ruleset) {
		super();
		this.ruleset = ruleset;
	}
	
	public RulesetType ruleset() {
		return ruleset;
	}
}
