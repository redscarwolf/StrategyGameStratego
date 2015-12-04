package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class General extends AbstractCharacter {

	public General(Player player) {
		super(Rank.GENERAL, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new General(p));
		}
	}
}
