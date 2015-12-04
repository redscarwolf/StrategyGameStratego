package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Scout extends AbstractCharacter {

	public Scout(Player player) {
		super(Rank.SCOUT, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Scout(p));
		}
	}

}
