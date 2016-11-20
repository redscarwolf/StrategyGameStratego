package de.htwg.stratego.controller.rules.impl;

import de.htwg.stratego.controller.rules.IRule;

public abstract class AbstractRule implements IRule {

	protected String message = "NoMessage";
	
	@Override
	public String message() {
		return message;
	}
	
}
