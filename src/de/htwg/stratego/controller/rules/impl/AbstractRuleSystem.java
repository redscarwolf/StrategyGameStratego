package de.htwg.stratego.controller.rules.impl;

import de.htwg.stratego.controller.rules.IRule;
import de.htwg.stratego.controller.rules.IRuleSystem;
import de.htwg.stratego.model.IField;

public abstract class AbstractRuleSystem implements IRuleSystem {

	protected IField field;
	
	private String message = "NoMessage";
	
	public AbstractRuleSystem(IField field) {
		this.field = field;
	}
	
	public boolean verify(IRule rule) {
		boolean result = rule.verify();
		message = rule.message();
		return result;
	}
	
	@Override
	public String message() {
		return message;
	}
	
}
