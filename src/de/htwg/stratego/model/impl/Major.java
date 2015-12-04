package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Major extends AbstractCharacter {

	public Major(Player player) {
		super(Rank.MAJOR, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Major(p));
		}
	}

}
