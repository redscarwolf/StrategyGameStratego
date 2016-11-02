package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class Miner extends AbstractCharacter {

	public Miner(IPlayer player) {
		super(Rank.MINER, true, player, "03miner.png");
	}
	
	public static void buildSeveral(int number, IPlayer p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Miner(p));
		}
	}
}
