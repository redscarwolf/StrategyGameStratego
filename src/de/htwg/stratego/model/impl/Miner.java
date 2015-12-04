package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.Rank;

public class Miner extends AbstractCharacter {

	public Miner(Player player) {
		super(Rank.MINER, true, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Miner(p));
		}
	}
}
