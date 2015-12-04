package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Flag extends AbstractCharacter {
	
	public Flag(Player player) {
		super(Rank.FLAG, false, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Flag(p));
		}
	}

}
