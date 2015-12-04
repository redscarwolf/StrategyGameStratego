package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Marshal extends AbstractCharacter {

	public Marshal(Player player) {
		super(Rank.MARSHAL, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Marshal(p));
		}
	}
}
