package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class Lieutenant extends AbstractCharacter {

	public Lieutenant(IPlayer player) {
		super(Rank.LIEUTENANT, true, player, "05lieutenant.png");
	}
	
	public static void buildSeveral(int number, IPlayer p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Lieutenant(p));
		}
	}
}
