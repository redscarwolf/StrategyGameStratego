package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;

public class Lieutenant extends AbstractCharacter {

	public Lieutenant(Player player) {
		super(Rank.LIEUTENANT, true, player, "05lieutenant.png");
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Lieutenant(p));
		}
	}
}
