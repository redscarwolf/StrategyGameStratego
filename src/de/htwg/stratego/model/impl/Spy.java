package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Spy extends AbstractCharacter {

	public Spy(Player player) {
		super(Rank.SPY, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Spy(p));
		}
	}
}
